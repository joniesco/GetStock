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
 * This Fragment shows a users or brokers favorite picks.
 * both user and broker can click a widget, and see full details
 * of the stock.
 *
 */
public class favoFragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private List<MyStock> myStockList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favo, container, false);
        fillExampleList();

        // Inflate the layout for this fragment
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecycler);
        favoritesRecyclerView.setHasFixedSize(true);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        favoritesRecyclerView.setAdapter(new StockSearchAdapter(myStockList,view.getContext() ,this));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setTitle("Favorites");

    }

    /**
     * a fake list of stocks,
     * need to be populated from DB of the user/broker
     *
     */
        private void fillExampleList() {
            myStockList = new ArrayList<>();
            myStockList.add(new MyStock("AAA", "One", "Ten"));
            myStockList.add(new MyStock("APL", "Two", "Eleven"));
            myStockList.add(new MyStock("BBB", "Three", "Twelve"));
            myStockList.add(new MyStock("APA", "Four", "Thirteen"));
            myStockList.add(new MyStock("APA", "Five", "Fourteen"));
            myStockList.add(new MyStock("APA", "Six", "Fifteen"));
            myStockList.add(new MyStock("APA", "Seven", "Sixteen"));
            myStockList.add(new MyStock("APA", "Eight", "Seventeen"));
            myStockList.add(new MyStock("APA", "Nine", "Eighteen"));
        }

    /**
     * Create a list of preferences from the DB.
     */
    private void createFavoritesList(){

    }
}