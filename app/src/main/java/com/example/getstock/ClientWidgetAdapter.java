package com.example.getstock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter used in our home screen
 * contains a list of clients (Widgets).
 */
public class ClientWidgetAdapter extends RecyclerView.Adapter<ClientWidgetAdapter.ClientWidgetViewHolder> {

    private List<String> clientList;
    private List<String> clientListFull;
    Context ct;
    Fragment ft;
    Broker broker;
    int userType;

    public  recycleClickListener recycleClickListener;
    //Get our DB instance.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    ClientWidgetAdapter(List<String> clientList, Context ct, Fragment ft, recycleClickListener recycleClickListener, int userType) {
        this.clientList = clientList;
        clientListFull = new ArrayList<>(clientList);
        this.ct = ct;
        this.ft = ft;
        this.recycleClickListener=recycleClickListener;
        this.userType = userType;
    }


    class ClientWidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView postDescription;
        TextView PostTitle;
        ImageView profilePicture;
        CardView cardView;
        PieChart pieChart;

        recycleClickListener recycleClickListener;

        ClientWidgetViewHolder(View itemView, recycleClickListener recycleClickListener) {
            super(itemView);
            postDescription = itemView.findViewById(R.id.postDescription);
            PostTitle = itemView.findViewById(R.id.title_of_post);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            cardView = itemView.findViewById(R.id.click_post);
            pieChart = itemView.findViewById(R.id.piechart);
            this.recycleClickListener= recycleClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recycleClickListener.myClickListener(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public ClientWidgetAdapter.ClientWidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_stock_recommend,
                parent, false);
        ClientWidgetAdapter.ClientWidgetViewHolder myHolder =
                new ClientWidgetAdapter.ClientWidgetViewHolder(v,recycleClickListener);

//        v.setOnClickListener(myHolder);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientWidgetAdapter.ClientWidgetViewHolder holder, int position) {

        String userId = clientList.get(position);
        Log.d("", userId + " user");
        if(userType == 1) {
            db.collection("Users").document(userId)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = (User) documentSnapshot.toObject(User.class);
                    Log.d("", "user" + user.toString());
                    holder.PostTitle.setText(user.getFullName());
                    holder.postDescription.setText(user.getEmail());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("", "failed");
                }
            });
        }
        else {
            db.collection("Brokers").document(userId)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Broker broker = (Broker) documentSnapshot.toObject(Broker.class);

//                    Log.d("", "user" + user.toString());

                    holder.PostTitle.setText(broker.getFullName());
                    holder.postDescription.setText(broker.getEmail());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("", "failed");
                }
            });
        }
        //Create pie chart
        holder.pieChart.addPieSlice(
                new PieModel(
                        "AAPL" +
                                "",
                        Integer.parseInt(Integer.toString(40)),
                        Color.parseColor("#FFA726")));
        holder.pieChart.addPieSlice(
                new PieModel(
                        "STK",
                        Integer.parseInt(Integer.toString(30)),
                        Color.parseColor("#66BB6A")));
        holder.pieChart.addPieSlice(
                new PieModel(
                        "RPQ",
                        Integer.parseInt(Integer.toString(5)),
                        Color.parseColor("#EF5350")));
        holder.pieChart.addPieSlice(
                new PieModel(
                        "TND",
                        Integer.parseInt(Integer.toString(25)),
                        Color.parseColor("#29B6F6")));
        holder.pieChart.setBackgroundColor(ct.getResources().getColor(R.color.nice_yellow));


//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                getBroker();
//                Bundle args= new Bundle();
//                args.putSerializable("broker", broker);
//                args.putString("clientId", userId);
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void getBroker(){


        db.collection("Brokers")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 broker = (Broker)documentSnapshot.toObject(Broker.class);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }



    public  interface recycleClickListener{
        void myClickListener(int position);
    }
}

