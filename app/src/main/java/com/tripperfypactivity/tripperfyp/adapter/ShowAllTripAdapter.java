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
import com.tripperfypactivity.tripperfyp.models.Trip;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAllTripAdapter extends RecyclerView.Adapter<ShowAllTripAdapter.ViewHolder> {



    private List<Trip> mData;
    private LayoutInflater mInflater;
    OnRecyclerItemClickListener recyclerItemClickListener;


    public ShowAllTripAdapter(Context context, List<Trip> data, OnRecyclerItemClickListener recyclerItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyler_triplist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyleitemname.setText(mData.get(position).getTripName());
        holder.recylertripexpence.setText(mData.get(position).getStartDate());
        holder.recyletripdate.setText(mData.get(position).getStartDate());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TextView myTextView;
        @BindView(R.id.recyleitemname)
        TextView recyleitemname;
        @BindView(R.id.recyletripdate)
        TextView recyletripdate;
        @BindView(R.id.recylertripexpence)
        TextView recylertripexpence;
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
