package com.tripperfypactivity.tripperfyp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.listeners.OnRecyclerItemClickListener;
import com.tripperfypactivity.tripperfyp.models.ChatMessage;
import com.tripperfypactivity.tripperfyp.models.Trip;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context activity;
    private LayoutInflater mInflater;
    ArrayList<ChatMessage> chatMessageArrayList;
    String name;

    public MessageAdapter(Activity activity, ArrayList<ChatMessage> modelClass) {
        // super(activity, modelClass, modelLayout, ref);
        this.activity = activity;
        this.chatMessageArrayList = modelClass;
        this.mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getItemViewType(int position) {
         if (chatMessageArrayList.get(position).getMessageUserId().equals(Shared.getUser().getUid())) {
                 return 0;
             } else {
                 return 1;
             }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;// mInflater.inflate(R.layout.recyler_triplist, parent, false);
        if (viewType == 0) {
            view = mInflater.inflate(R.layout.item_out_message, parent, false);
            return new OuterViewHolder(view);
        } else {
            view = mInflater.inflate(R.layout.item_in_message, parent, false);
            return new InnerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        switch (holder1.getItemViewType()) {
            case 0:
                OuterViewHolder holder = (OuterViewHolder) holder1;
                holder.messageText.setText(chatMessageArrayList.get(position).getMessageText());
                holder.messageTime.setText(timeConvert(chatMessageArrayList.get(position).getMessageTime()+""));
                holder.messageUser.setText(chatMessageArrayList.get(position).getMessageUser());
                break;

            case 2:
                InnerViewHolder holder2 = (InnerViewHolder) holder1;
                holder2.messageText.setText(chatMessageArrayList.get(position).getMessageText());
                holder2.messageTime.setText(timeConvert(chatMessageArrayList.get(position).getMessageTime()+""));
                holder2.messageUser.setText(chatMessageArrayList.get(position).getMessageUser());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }
    
    public String timeConvert(String longv){
        long millisecond = Long.parseLong(longv);
        String dateString = DateFormat.format("MMM dd  hh:mm a", new Date(millisecond)).toString();
        return dateString;
    }



    public class OuterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_user)
        TextView messageUser;
        @BindView(R.id.message_text)
        TextView messageText;
        @BindView(R.id.message_time)
        TextView messageTime;

        OuterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_user)
        TextView messageUser;
        @BindView(R.id.message_text)
        TextView messageText;
        @BindView(R.id.message_time)
        TextView messageTime;

        InnerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
