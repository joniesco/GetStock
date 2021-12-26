package com.example.getstock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



/**
 * This Fragment shows a users favorite picks.
 */
public class favoFragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private List<Stock> stockList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favo, container, false);
        fillExampleList();

        // Inflate the layout for this fragment
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecycler);
        favoritesRecyclerView.setHasFixedSize(true);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favoritesRecyclerView.setAdapter(new StockSearchAdapter(stockList,view.getContext() ,this));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setTitle("Favorites");

    }
        private void fillExampleList() {
            stockList = new ArrayList<>();
            stockList.add(new Stock("AAA", "One", "Ten"));
            stockList.add(new Stock("APL", "Two", "Eleven"));
            stockList.add(new Stock("BBB", "Three", "Twelve"));
            stockList.add(new Stock("APA", "Four", "Thirteen"));
            stockList.add(new Stock("APA", "Five", "Fourteen"));
            stockList.add(new Stock("APA", "Six", "Fifteen"));
            stockList.add(new Stock("APA", "Seven", "Sixteen"));
            stockList.add(new Stock("APA", "Eight", "Seventeen"));
            stockList.add(new Stock("APA", "Nine", "Eighteen"));
        }
}