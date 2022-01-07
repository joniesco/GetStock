package com.example.getstock;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class PortofolioAdapter extends RecyclerView.Adapter<PortofolioAdapter.MyViewHolder> {

    public ClientPortofolio portofolio;
    public List<String> symbolList;

    Context ct;
    Fragment ft;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userId , companyName , stockPrice;
    Broker broker;


    public PortofolioAdapter(ClientPortofolio portofolio, Context ct) {
        this.portofolio = portofolio;
        this.ct = ct;
        this.ft = ft;

        this.symbolList = new ArrayList<>(portofolio.symbolToAmount.keySet());

    }


    @NonNull
    @Override
    public PortofolioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.portofolio_item,
                parent, false);
        return new PortofolioAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PortofolioAdapter.MyViewHolder holder, int position) {

        String symbol = symbolList.get(position);

        holder.PostTitle.setText(symbol);

        holder.amount.setText(Double.toString(portofolio.symbolToAmount.get(symbol)));
        holder.buyValue.setText(Double.toString(portofolio.symbolToBuyValue.get(symbol)));

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void unused) {
                holder.PostDescription.setText(companyName);
                holder.currentValue.setText(stockPrice);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Stock myStock = YahooFinance.get(symbol);
                    companyName = myStock.getName(); //Company name
                    stockPrice = String.valueOf(myStock.getQuote().getPrice()); //stock price

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();

    }

    @Override
    public int getItemCount() {
        return symbolList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView companyPicture;
        TextView PostTitle;
        TextView PostDescription;

        TextView amount;
        TextView buyValue;
        TextView currentValue;


        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyPicture = itemView.findViewById(R.id.company_picture);
            PostDescription = itemView.findViewById(R.id.postDescription);
            PostTitle = itemView.findViewById(R.id.title_of_post);

            amount = itemView.findViewById(R.id.amount);
            buyValue = itemView.findViewById(R.id.buyValue);
            currentValue = itemView.findViewById(R.id.presentValue);

            cardView = itemView.findViewById(R.id.click_post);

        }
    }
}
