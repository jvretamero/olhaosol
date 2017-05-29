package br.com.joaoretamero.olhaosol.mapa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.main.PrevisoesView;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;
import br.com.joaoretamero.olhaosol.util.temperatura.KelvinParaCelcius;

public class MapaFragment extends Fragment implements PrevisoesView, OnMapReadyCallback {

    private ConversorTemperatura conversorTemperatura;
    private List<PrevisaoClimatica> previsoes;
    private GoogleMap googleMap;
    private MapView mapView;

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
        conversorTemperatura = new KelvinParaCelcius();
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
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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
    public void exibePrevisoes(List<PrevisaoClimatica> previsoes, ConversorTemperatura conversorTemperatura) {
        this.previsoes = previsoes;
        inserePrevisoesNoMapa();
    }

    @Override
    public void setConversorTemperatura(ConversorTemperatura conversorTemperatura) {
        this.conversorTemperatura = conversorTemperatura;
        inserePrevisoesNoMapa();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        inserePrevisoesNoMapa();
    }

    private void inserePrevisoesNoMapa() {
        if (previsoes != null && googleMap != null) {
            googleMap.clear();
            for (PrevisaoClimatica previsao : previsoes) {
                MarkerOptions markerOptions = new MarkerOptions();

                googleMap.addMarker(new MarkerOptions());
            }
        }
    }
}
