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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter used in our home screen
 * contains a list of brokers (Widgets).
 */
public class StockRecommendorAdapter extends RecyclerView.Adapter<StockRecommendorAdapter.StockRecommendorViewHolder> {

    private List<String> stockList;
    private List<String> stockListFull;
    Context ct;
    Fragment ft;

    //Get our DB instance.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    StockRecommendorAdapter(List<String> stockList, Context ct, Fragment ft) {
        this.stockList = stockList;
        stockListFull = new ArrayList<>(stockList);
        this.ct = ct;
        this.ft = ft;
    }


    class StockRecommendorViewHolder extends RecyclerView.ViewHolder {
        TextView postDescription;
        TextView PostTitle;
        ImageView profilePicture;
        CardView cardView;
        PieChart pieChart;

        StockRecommendorViewHolder(View itemView) {
            super(itemView);
            postDescription = itemView.findViewById(R.id.postDescription);
            PostTitle = itemView.findViewById(R.id.title_of_post);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            cardView = itemView.findViewById(R.id.click_post);
            pieChart = itemView.findViewById(R.id.piechart);

        }
    }

    @NonNull
    @Override
    public StockRecommendorAdapter.StockRecommendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_stock_recommend,
                parent, false);
        return new StockRecommendorAdapter.StockRecommendorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StockRecommendorAdapter.StockRecommendorViewHolder holder, int position) {
        String userId = stockList.get(position);
        Log.d("", userId + " user");
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

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}


