package com.br.syncrename.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.br.syncrename.Models.Tags;
import com.br.syncrename.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheAdapter extends RecyclerView.Adapter<DetalheAdapter.ViewHolder>{

    private List<Tags> mList;
    private Activity context;

    public DetalheAdapter(Activity context, List<Tags> mList){
        this.mList=mList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_destalhes, parent, false);
        return new DetalheAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tags tag = mList.get(position);
        holder.textTag.setText(tag.getCode());
        holder.textTimestamp.setText(tag.getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Tags> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.text_tag)
       TextView textTag;
       @BindView(R.id.text_timestamp)
       TextView textTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
