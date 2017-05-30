package br.com.joaoretamero.olhaosol.mapa;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.main.ExibicaoListener;
import br.com.joaoretamero.olhaosol.main.PrevisoesView;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;

public class MapaFragment extends Fragment implements PrevisoesView, OnMapReadyCallback {

    private ConversorTemperatura conversorTemperatura;
    private PrevisoesMapaAdapter adapter;
    private List<PrevisaoClimatica> previsoes;
    private GoogleMap googleMap;
    private MapView mapView;
    private ExibicaoListener exibicaoListener;
    private String formatoTemperatura;

    public MapaFragment() {
        // Required empty public constructor
    }

    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formatoTemperatura = getResources().getString(R.string.formato_temperatura);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        mapView = (MapView) view.findViewById(R.id.mapa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if (googleMap != null && exibicaoListener != null)
            exibicaoListener.onExibicaoIniciada();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

        if (exibicaoListener != null)
            exibicaoListener.onExibicaoPausada();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExibicaoListener) {
            exibicaoListener = (ExibicaoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar ExibicaoListener");
        }
    }

    @Override
    public void exibePrevisoes(List<PrevisaoClimatica> previsoes, ConversorTemperatura conversorTemperatura) {
        this.previsoes = previsoes;
        setConversorTemperatura(conversorTemperatura);
    }

    @Override
    public void setConversorTemperatura(ConversorTemperatura conversorTemperatura) {
        adapter.setConversorTemperatura(conversorTemperatura);
        inserePrevisoesNoMapa();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.adapter = new PrevisoesMapaAdapter(getContext(), formatoTemperatura);

        this.googleMap = googleMap;
        this.googleMap.setInfoWindowAdapter(adapter);

        if (exibicaoListener != null)
            exibicaoListener.onExibicaoIniciada();
    }

    private void inserePrevisoesNoMapa() {
        if (previsoes != null && googleMap != null) {
            adapter.limpar();
            googleMap.clear();

            LatLngBounds.Builder limites = new LatLngBounds.Builder();

            for (PrevisaoClimatica previsao : previsoes) {
                LatLng coordenada = new LatLng(previsao.latitude, previsao.longitude);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(coordenada);
                markerOptions.title(previsao.nomeCidade);
                markerOptions.snippet(previsao.descricaoClima);

                Marker marcador = googleMap.addMarker(markerOptions);
                marcador.setTag(previsao);

                limites.include(coordenada);
            }

            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(limites.build(), 128));
        }
    }
}
