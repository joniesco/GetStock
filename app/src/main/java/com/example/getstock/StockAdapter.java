package com.example.getstock;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockAdapterViewHolder> implements Filterable {

    private List<String> stockList;
    private List<String> stockListFull;
    Context ct;
    Fragment ft;
    Stock myStock;

    StockAdapter(List<String> stockList, Context ct, Fragment ft) {
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
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stockListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String item : stockListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
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

    class StockAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView postDescription;
        TextView PostTitle;
        ImageView profilePicture;
        CardView cardView;
        PieChart pieChart;

        // variable for our bar chart
        BarChart barChart;

        // variable for our bar data.
        BarData barData;

        // variable for our bar data set.
        BarDataSet barDataSet;

        // array list for storing entries.
        ArrayList barEntriesArrayList;

        StockAdapterViewHolder(View itemView) {
            super(itemView);
            postDescription = itemView.findViewById(R.id.postDescription);
            PostTitle = itemView.findViewById(R.id.title_of_post);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            cardView = itemView.findViewById(R.id.click_post);
            pieChart = itemView.findViewById(R.id.piechart);


            // initializing variable for bar chart.
            barChart = itemView.findViewById(R.id.idBarChart);
            barChart.setDrawValueAboveBar(true);
            barChart.getAxisRight().setDrawGridLines(false);
            barChart.getAxisLeft().setDrawGridLines(false);
            barChart.getXAxis().setDrawGridLines(false);

            barChart.setDrawBorders(false);

            //outline
            barChart.getAxisRight().setDrawAxisLine(false);

            barChart.getAxisLeft().setDrawAxisLine(false);

            barChart.getXAxis().setDrawAxisLine(false);


            // calling method to get bar entries.
            getBarEntries();

            // creating a new bar data set.
            barDataSet = new BarDataSet(barEntriesArrayList, "Geeks for Geeks");
            barDataSet.setDrawValues(false);
            // creating a new bar data and
            // passing our bar data set.
            barData = new BarData(barDataSet);

            // below line is to set data
            // to our bar chart.
            barChart.setData(barData);

            // adding color to our bar data set.
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            // setting text color.
            barDataSet.setValueTextColor(Color.BLACK);

            // setting text size
            barDataSet.setValueTextSize(16f);
            barChart.getDescription().setEnabled(false);

        }
        private void getBarEntries() {
            // creating a new array list
            barEntriesArrayList = new ArrayList<>();

            // adding new entry to our array list with bar
            // entry and passing x and y axis value to it.
            barEntriesArrayList.add(new BarEntry(1f, 4));
            barEntriesArrayList.add(new BarEntry(2f, 6));
            barEntriesArrayList.add(new BarEntry(3f, 8));
            barEntriesArrayList.add(new BarEntry(4f, 2));
            barEntriesArrayList.add(new BarEntry(5f, 4));
            barEntriesArrayList.add(new BarEntry(6f, 1));
        }
    }

    @NonNull
    @Override
    public StockAdapter.StockAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item,
                parent, false);
        return new StockAdapter.StockAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.StockAdapterViewHolder holder, int position) {
        String userpost = stockList.get(position);
        holder.PostTitle.setText(userpost);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void unused) {
                holder.postDescription.setText(myStock.getName());
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    myStock = YahooFinance.get(userpost);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();

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
