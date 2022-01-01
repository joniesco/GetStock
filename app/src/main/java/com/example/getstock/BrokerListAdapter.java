package com.example.getstock;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class BrokerListAdapter extends RecyclerView.Adapter<BrokerListAdapter.BrokerListViewHolder> implements Filterable {

    private List<UserPost> stockList;
    private List<UserPost> stockListFull;
    Context ct;
    Fragment ft;

    BrokerListAdapter(List<UserPost> stockList, Context ct, Fragment ft) {
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

    class BrokerListViewHolder extends RecyclerView.ViewHolder {
//        TextView postDescription;
//        TextView PostTitle;
//        ImageView profilePicture;
//        CardView cardView;
//        PieChart pieChart;

        BrokerListViewHolder(View itemView) {
            super(itemView);
//            postDescription = itemView.findViewById(R.id.postDescription);
//            PostTitle = itemView.findViewById(R.id.title_of_post);
//            profilePicture = itemView.findViewById(R.id.profile_picture);
//            cardView = itemView.findViewById(R.id.click_post);
//            pieChart = itemView.findViewById(R.id.piechart);

        }
    }

    @NonNull
    @Override
    public BrokerListAdapter.BrokerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_item_list_view,
                parent, false);
        return new BrokerListAdapter.BrokerListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BrokerListAdapter.BrokerListViewHolder holder, int position) {
        UserPost userpost = stockList.get(position);

//        holder.PostTitle.setText(userpost.getPostTitle());
//        holder.postDescription.setText(userpost.getDesc());
//        holder.profilePicture.setImageResource(R.drawable.ic_accessibility);
//        holder.profilePicture.setColorFilter(Color.parseColor("#519259"));

        //Create our fragment to show broker
        Fragment showPost = new showPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", "From Activity"); //Attach the new fragment an instance of broker to show.
        showPost.setArguments(bundle);

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            /**
//             * Define an what happens when you click a widget.
//             *
//             * @param v
//             */
//            @Override
//            public void onClick(View v) {
//
//                FragmentManager fragmentManager = ((FragmentActivity) ct).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, showPost);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
////                Intent intent = new Intent(ct, widgetClick.class);
////                intent.putExtra("Code", "asd");
////                ct.startActivity(intent);
//            }
//
//        });

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
