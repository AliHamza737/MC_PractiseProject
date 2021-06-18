package com.tripperfypactivity.tripperfyp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;
import com.tripperfypactivity.tripperfyp.models.Expences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowTripExpencesAdapter extends RecyclerView.Adapter<ShowTripExpencesAdapter.ViewHolder> {



    private List<Expences> mData;
    private LayoutInflater mInflater;
    OnRecyclerItemClickListener recyclerItemClickListener;

    public ShowTripExpencesAdapter(Context context, List<Expences> data, OnRecyclerItemClickListener recyclerItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recyler_expences_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.expencename.setText(mData.get(position).getMemberName());
        holder.expencedate.setText(mData.get(position).getType());
        holder.expamount.setText(mData.get(position).getDiscription());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TextView myTextView;
        @BindView(R.id.expencename)
        TextView expencename;
        @BindView(R.id.expencedate)
        TextView expencedate;
        @BindView(R.id.expamount)
        TextView expamount;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
