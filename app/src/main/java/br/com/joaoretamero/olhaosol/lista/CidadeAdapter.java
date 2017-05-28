package br.com.joaoretamero.olhaosol.lista;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.modelos.Cidade;
import butterknife.ButterKnife;

public class CidadeAdapter extends RecyclerView.Adapter<CidadeAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Cidade> cidades;

    public CidadeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
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
        return cidades == null ? 3 : cidades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
