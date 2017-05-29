package br.com.joaoretamero.olhaosol.lista;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.ServicoHttp;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.NumeroUtil;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;
import br.com.joaoretamero.olhaosol.util.temperatura.KelvinParaCelcius;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CidadeAdapter extends RecyclerView.Adapter<CidadeAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<PrevisaoClimatica> previsoesClimaticas;
    private String formatoTemperatura;
    private ConversorTemperatura conversorTemperatura;

    public CidadeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.formatoTemperatura = context.getResources().getString(R.string.formato_temperatura);
        this.conversorTemperatura = new KelvinParaCelcius();
    }

    public void setPrevisoesClimaticas(List<PrevisaoClimatica> previsoesClimaticas) {
        this.previsoesClimaticas = previsoesClimaticas;
    }

    public void setConversorTemperatura(ConversorTemperatura conversorTemperatura) {
        this.conversorTemperatura = conversorTemperatura;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_cidade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PrevisaoClimatica previsao = previsoesClimaticas.get(position);

        holder.cidade.setText(previsao.nomeCidade);
        holder.temperaturaAtual.setText(formatarTemperatura(previsao.temperaturaAtual));
        holder.temperaturaMinima.setText(formatarTemperatura(previsao.temperaturaMinima));
        holder.temperaturaMaxima.setText(formatarTemperatura(previsao.temperaturaMaxima));
        holder.descricaoClima.setText(previsao.descricaoClima);
        holder.distancia.setText(NumeroUtil.formatar(previsao.distanciaEmKm, 1));

        Glide.with(context)
                .load(ServicoHttp.montarUrlIcone(previsao.icone))
                .into(holder.imagem);
    }

    private String formatarTemperatura(float temperatura) {
        float temperaturaConvertida = conversorTemperatura.converter(temperatura);
        return String.format(formatoTemperatura, NumeroUtil.formatar(temperaturaConvertida, 1));
    }

    @Override
    public int getItemCount() {
        return previsoesClimaticas == null ? 0 : previsoesClimaticas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_cidade_nome)
        TextView cidade;

        @BindView(R.id.item_cidade_temperatura_atual)
        TextView temperaturaAtual;

        @BindView(R.id.item_cidade_temperatura_minima)
        TextView temperaturaMinima;

        @BindView(R.id.item_cidade_temperatura_maxima)
        TextView temperaturaMaxima;

        @BindView(R.id.item_cidade_distancia)
        TextView distancia;

        @BindView(R.id.item_cidade_descricao_clima)
        TextView descricaoClima;

        @BindView(R.id.item_cidade_imagem)
        ImageView imagem;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
