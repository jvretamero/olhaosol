package br.com.joaoretamero.olhaosol.mapa;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.ProvedorHttp;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;
import br.com.joaoretamero.olhaosol.util.temperatura.TemperaturaUtil;

public class PrevisoesMapaAdapter implements GoogleMap.InfoWindowAdapter {

    private final Map<Marker, Bitmap> imagens = new HashMap<>();
    private final Map<Marker, Target<Bitmap>> targets = new HashMap<>();

    private Context context;
    private LayoutInflater layoutInflater;
    private String formatoTemperatura;
    private ConversorTemperatura conversorTemperatura;

    public PrevisoesMapaAdapter(Context context, String formatoTemperatura) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.formatoTemperatura = formatoTemperatura;
    }

    public void setConversorTemperatura(ConversorTemperatura conversorTemperatura) {
        this.conversorTemperatura = conversorTemperatura;
    }

    public void limpar() {
        RequestManager requestManager = Glide.with(context);

        for (Marker marcador : targets.keySet())
            requestManager.clear(targets.get(marcador));

        imagens.clear();
        targets.clear();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marcador) {
        PrevisaoClimatica previsaoClimatica = (PrevisaoClimatica) marcador.getTag();
        float temperaturaConvertida = getTemperaturaConvertida(previsaoClimatica);

        View view = layoutInflater.inflate(R.layout.marcador_previsao, null);

        ImageView imagem = (ImageView) view.findViewById(R.id.marcador_previsao_imagem);

        Bitmap bitmap = imagens.get(marcador);
        if (bitmap == null)
            Glide.with(context)
                    .asBitmap()
                    .load(ProvedorHttp.montarUrlIcone(previsaoClimatica.icone))
                    .into(getTarget(marcador));
        else
            imagem.setImageBitmap(bitmap);

        TextView cidade = (TextView) view.findViewById(R.id.marcador_previsao_cidade);
        cidade.setText(previsaoClimatica.nomeCidade);

        TextView temperatura = (TextView) view.findViewById(R.id.marcador_previsao_temperatura);
        temperatura.setText(TemperaturaUtil.formatar(temperaturaConvertida, formatoTemperatura));

        return view;
    }

    private Target<Bitmap> getTarget(Marker marcador) {
        Target<Bitmap> target = targets.get(marcador);

        if (target == null)
            return new PrevisaoTarget(marcador);
        else
            return target;
    }

    private float getTemperaturaConvertida(PrevisaoClimatica previsaoClimatica) {
        if (conversorTemperatura != null)
            return conversorTemperatura.converter(previsaoClimatica.temperaturaAtual);

        return previsaoClimatica.temperaturaAtual;
    }

    private class PrevisaoTarget extends SimpleTarget<Bitmap> {

        private Marker marcador;

        public PrevisaoTarget(Marker marker) {
            this.marcador = marker;
        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {
            imagens.remove(marcador);
        }

        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
            imagens.put(marcador, resource);
            marcador.showInfoWindow();
        }
    }
}
