package com.example.getstock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter to search for stocks.
 */
public class StockSearchAdapter extends RecyclerView.Adapter<StockSearchAdapter.StockViewHolder> implements Filterable {

    private List<Stock> stockList;
    private List<Stock> stockListFull;
    Context ct;
    Fragment ft;

    StockSearchAdapter(List<Stock> stockList, Context ct, Fragment ft) {
        this.stockList= stockList;
        stockListFull= new ArrayList<>(stockList);
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
            List<Stock> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(stockListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Stock item : stockListFull) {
                    if (item.getSymbol().toLowerCase().contains(filterPattern)) {
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

    class StockViewHolder extends RecyclerView.ViewHolder {
        TextView Symbol;
        TextView desc;
        TextView market;
        TextView percentage;
        ImageView imageArrow;
        CardView cardView;

        StockViewHolder(View itemView) {
            super(itemView);
            Symbol = itemView.findViewById(R.id.stock);
            desc = itemView.findViewById(R.id.desc);
            market = itemView.findViewById(R.id.symbol);
            percentage = itemView.findViewById(R.id.percentageChange);
            imageArrow = itemView.findViewById(R.id.percentageImage);
            cardView = itemView.findViewById(R.id.search_card);
        }
    }
    @NonNull
    @Override
    public StockSearchAdapter.StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,
                parent, false);
        return new StockViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StockSearchAdapter.StockViewHolder holder, int position) {
        Stock stock = stockList.get(position);

        holder.Symbol.setText(stock.getSymbol());
        holder.desc.setText(stock.getDesc());
        holder.market.setText(stock.getMarket());
        holder.percentage.setText(stock.percentChange);

        if(stock.percentIsPositive) {
            holder.imageArrow.setImageResource(R.drawable.ic_arrow_upward);
            holder.imageArrow.setColorFilter(Color.parseColor("#519259"));
            holder.percentage.setTextColor(Color.parseColor("#519259"));
        }
        else {
            holder.imageArrow.setImageResource(R.drawable.ic_downward);
            holder.imageArrow.setColorFilter(Color.parseColor("#F05454"));
            holder.percentage.setTextColor(Color.parseColor("#F05454"));

        }

        Fragment showPost = new StockFullPageFragment();
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
