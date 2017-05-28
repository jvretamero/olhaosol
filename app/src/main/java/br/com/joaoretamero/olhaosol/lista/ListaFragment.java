package br.com.joaoretamero.olhaosol.lista;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.joaoretamero.olhaosol.R;

public class ListaFragment extends Fragment {

    private CidadeAdapter adapter;

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

}
