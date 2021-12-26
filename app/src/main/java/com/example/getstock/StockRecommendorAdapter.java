package com.example.getstock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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



import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter used in our recommendation screen
 * contains a list of User posts (Widgets).
 */
public class StockRecommendorAdapter extends RecyclerView.Adapter<StockRecommendorAdapter.StockRecommendorViewHolder> implements Filterable {

    private List<UserPost> stockList;
    private List<UserPost> stockListFull;
    Context ct;
    Fragment ft;

    StockRecommendorAdapter(List<UserPost> stockList, Context ct, Fragment ft) {
        this.stockList = stockList;
        stockListFull = new ArrayList<>(stockList);
        this.ct = ct;
        this.ft = ft;
    }

    @Override
    public Filter getFilter() {
        return stockFilter;
    }

    private Filter stockFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserPost> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stockListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (UserPost item : stockListFull) {
                    if (item.getDesc().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stockList.clear();
            stockList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class StockRecommendorViewHolder extends RecyclerView.ViewHolder {
        TextView postDescription;
        TextView PostTitle;
        ImageView profilePicture;
        CardView cardView;

        StockRecommendorViewHolder(View itemView) {
            super(itemView);
            postDescription = itemView.findViewById(R.id.postDescription);
            PostTitle = itemView.findViewById(R.id.title_of_post);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            cardView = itemView.findViewById(R.id.click_post);


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
        UserPost userpost = stockList.get(position);

        holder.PostTitle.setText(userpost.getPostTitle());
        holder.postDescription.setText(userpost.getDesc());
        holder.profilePicture.setImageResource(R.drawable.ic_accessibility);
        holder.profilePicture.setColorFilter(Color.parseColor("#519259"));

        Fragment showPost = new showPostFragment();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            /**
             * Define an what happens when you click a widget.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((FragmentActivity) ct).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, showPost);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Intent intent = new Intent(ct, widgetClick.class);
//                intent.putExtra("Code", "asd");
//                ct.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}


