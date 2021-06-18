package com.tripperfypactivity.tripperfyp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.activiteis.Login_Activity;
import com.tripperfypactivity.tripperfyp.activiteis.Navigation_Activity;
import com.tripperfypactivity.tripperfyp.activiteis.Registraion_Activity;
import com.tripperfypactivity.tripperfyp.adapter.MessageAdapter;
import com.tripperfypactivity.tripperfyp.models.ChatMessage;
import com.tripperfypactivity.tripperfyp.models.User;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.GlobalUtils;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tripperfypactivity.tripperfyp.utilites.Constans.CHAT_GROUP;
import static java.util.Collections.addAll;


public class ChatFragment extends Fragment {

    Context mContext;
    private static final int SIGN_IN_REQUEST_CODE = 111;

    Unbinder unbinder;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MessageAdapter adapter;
    private ArrayList<ChatMessage> chatlist;
    LinearLayoutManager linearLayoutManager;

    final int item_load_count = 21;
    int total_item = 0, last_visible_item;
    boolean isLoading = false, isMaxdata = false;
    String last_node, last_key;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        showAllOldMessages();
        setupConnection();

        return view;
    }

    private void addMessage() {

        if (input.getText().toString().trim().equals("")) {
            GlobalUtils.showToast(mContext, "Please Enter Some Text!");
        } else {
            if(Shared.tripid!=null) {
                Shared.getDataBaseRef(CHAT_GROUP).child(Shared.tripid)
                        .push()
                        .setValue(new ChatMessage
                                (input.getText().toString(),
                                        Shared.getUserLogin().firstName,
                                        Shared.getUser().getUid())
                        );
                input.setText("");
            }else{
                popup();
            }
        }

    }

    public void popup() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setCancelable(false);
        dialog.setTitle("Alert");
        dialog.setMessage("Please Join a Trip First!");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
              //  startActivity(new Intent(mContext, Login_Activity.class));
            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }
    private void setupConnection() {
        if(Shared.tripid!=null) {
            DatabaseReference data = Shared.getDataBaseRef(CHAT_GROUP).child(Shared.tripid);

            data.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    handleReturn(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void handleReturn(DataSnapshot dataSnapshot) {
        chatlist.clear();

        for (DataSnapshot item : dataSnapshot.getChildren()) {
            ChatMessage data = item.getValue(ChatMessage.class);
            chatlist.add(0, data);
        }

        adapter.notifyDataSetChanged();
    }


    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;


    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public ArrayList<String> arrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        return arrayList;
        // return new ArrayList<>();
    }

    private void showAllOldMessages() {
        chatlist = new ArrayList<>();
        getLastKeyfromFirebase();
        linearLayoutManager = new LinearLayoutManager(mContext);
        //  linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //  linearLayoutManager.setStackFromEnd(true);
        // linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setReverseLayout(true);
        list.setLayoutManager(linearLayoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), linearLayoutManager.getOrientation());
//        list.addItemDecoration(dividerItemDecoration);

        adapter = new MessageAdapter((Activity) mContext, chatlist);
        list.setAdapter(adapter);
        getMessages();

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                total_item = linearLayoutManager.getItemCount();
                last_visible_item = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && total_item <= (last_visible_item + item_load_count)) {
                    getMessages();
                    isLoading = true;
                }
            }
        });

    }

    private void getMessages() {
        if(Shared.tripid!=null) {
            Query query;

            if (!isMaxdata)
                query = Shared.getDataBaseRef(CHAT_GROUP).child(Shared.tripid)
                        .orderByKey()
                        .limitToFirst(item_load_count);
            else
                query = Shared.getDataBaseRef(CHAT_GROUP).child(Shared.tripid)
                        .orderByKey()
                        .startAt(last_node)
                        .limitToFirst(item_load_count);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            chatlist.add(userSnapshot.getValue(ChatMessage.class));
                        }
                        last_node = chatlist.get(chatlist.size() - 1).getMessageUserId();
                        if (!last_node.equals(last_key)) {
                            chatlist.remove(chatlist.size() - 1);
                        } else {
                            last_node = "end";
                        }

                        adapter = new MessageAdapter((Activity) mContext, chatlist);
                        list.setAdapter(adapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }

    private void getLastKeyfromFirebase() {
        if(Shared.tripid!=null) {
            Query
                    query = Shared.getDataBaseRef(CHAT_GROUP).child(Shared.tripid)
                    .orderByKey()
                    .limitToLast(1);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot lastkey : dataSnapshot.getChildren())
                        last_key = lastkey.getKey();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }

    public void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
        hideKeyboard();
        addMessage();
    }


}
