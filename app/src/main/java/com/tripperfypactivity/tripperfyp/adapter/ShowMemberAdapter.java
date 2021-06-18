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
import com.tripperfypactivity.tripperfyp.models.TripMemberList;
import com.tripperfypactivity.tripperfyp.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShowMemberAdapter extends RecyclerView.Adapter<ShowMemberAdapter.ViewHolder> {

    private ArrayList<TripMemberList> mData;
    private LayoutInflater mInflater;
    OnRecyclerItemClickListener recyclerItemClickListener;


    public ShowMemberAdapter(Context context, ArrayList<TripMemberList> data, OnRecyclerItemClickListener recyclerItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyler_memberlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.membaername.setText(mData.get(position).getFirstName());
       holder.expences.setText(mData.get(position).getLastName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TextView myTextView;
        @BindView(R.id.profile_image)
        CircleImageView profileImage;
        @BindView(R.id.membaername)
        TextView membaername;
        @BindView(R.id.expences)
        TextView expences;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //  if (recyclerItemClickListener != null)
            // recyclerItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}
