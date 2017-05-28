package br.com.joaoretamero.olhaosol.lista;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import butterknife.ButterKnife;

public class CidadeAdapter extends RecyclerView.Adapter<CidadeAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<PrevisaoClimatica> previsoesClimaticas;

    public CidadeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setPrevisoesClimaticas(List<PrevisaoClimatica> previsoesClimaticas) {
        this.previsoesClimaticas = previsoesClimaticas;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_cidade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return previsoesClimaticas == null ? 3 : previsoesClimaticas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
