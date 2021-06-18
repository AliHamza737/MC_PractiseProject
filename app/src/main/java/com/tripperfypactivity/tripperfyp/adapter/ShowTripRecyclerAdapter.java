package com.tripperfypactivity.tripperfyp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;

import java.util.List;

public class ShowTripRecyclerAdapter extends RecyclerView.Adapter<ShowTripRecyclerAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;
    OnRecyclerItemClickListener recyclerItemClickListener;

    // data is passed into the constructor
    public ShowTripRecyclerAdapter(Context context, List<String> data, OnRecyclerItemClickListener recyclerItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_iltem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // holder.myTextView.setText(mData.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return 20;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       // TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
           // myTextView = itemView.findViewById(R.id.recyleritem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (recyclerItemClickListener != null)
                recyclerItemClickListener.onItemClick(getAdapterPosition());
        }
    }

//    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData.get(id);
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
}