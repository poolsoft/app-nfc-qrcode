package com.br.syncrename.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private List<Arquivo> mList;
    private Activity context;

    public ListaAdapter(Activity context, List<Arquivo> mList){
        this.mList=mList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_arquivos, parent, false);
        return new ListaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Arquivo arquivo = mList.get(position);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        DateTime data = arquivo.getData();
        formato.applyPattern("dd-MM-yyyy");
        holder.textDate.setText(formato.format(data).replace("-","/"));

        holder.textNome.setText(arquivo.getNome());
        holder.textTime.setText(data.getHourOfDay()+":"+data.getMillisOfDay());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Arquivo> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.father_relative)
        RelativeLayout fatherRelative;
        @BindView(R.id.text_nome)
        TextView textNome;
        @BindView(R.id.text_date)
        TextView textDate;
        @BindView(R.id.text_time)
        TextView textTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
