package br.com.joaoretamero.olhaosol.lista;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.main.ExibicaoListener;
import br.com.joaoretamero.olhaosol.main.PrevisoesView;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;

public class ListaFragment extends Fragment implements PrevisoesView {

    private CidadeAdapter adapter;
    private ExibicaoListener exibicaoListener;

    public ListaFragment() {
        // Required empty public constructor
    }

    public static ListaFragment newInstance() {
        ListaFragment fragment = new ListaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new CidadeAdapter(getContext());

        RecyclerView lista = (RecyclerView) inflater.inflate(R.layout.fragment_lista, container, false);
        lista.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        lista.setAdapter(adapter);

        return lista;
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
    public void onResume() {
        super.onResume();

        if (exibicaoListener != null)
            exibicaoListener.onExibicaoIniciada();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (exibicaoListener != null)
            exibicaoListener.onExibicaoPausada();
    }

    @Override
    public void exibePrevisoes(List<PrevisaoClimatica> previsoes, ConversorTemperatura conversorTemperatura) {
        adapter.setPrevisoesClimaticas(previsoes);
        setConversorTemperatura(conversorTemperatura);
    }

    @Override
    public void setConversorTemperatura(ConversorTemperatura conversorTemperatura) {
        adapter.setConversorTemperatura(conversorTemperatura);
    }
}
