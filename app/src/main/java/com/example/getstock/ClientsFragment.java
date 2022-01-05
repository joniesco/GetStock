package com.example.getstock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment displays all the clients files, the broker has.
 * (A yellowish widget on the home screen) , contains small amounts of data
 * each widget can be clicked to move to the full client details.
 */
public class ClientsFragment extends Fragment {

    private RecyclerView recommendRecyclerView; //Will hold our widgets.
    private List<Broker> postList; //a random list that currently populates the page.
    private List<Broker> clientList; //The actual list that will populate the page.
    private FloatingActionButton floatingActionButton; //floating + button.
    private CardView cardView;
    Fragment homeFragment = this; //Used adapter constructor.
    private DatabaseReference brokerDatabase; //Will hold our database reference.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_client
                , container, false);

        // Fill an fake list of items to be presented.
        fillExampleList();

        // Inflate the layout for this fragment
        recommendRecyclerView = view.findViewById(R.id.ClientRecycler);
        recommendRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        recommendRecyclerView.setLayoutManager(llm);
        recommendRecyclerView.setAdapter(new StockRecommendorAdapter(postList,view.getContext(),this));

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }

    private void fillExampleList() {
        postList = new ArrayList<>();
        postList.add(new Broker("David Blaine", "One", "Ten", 0.5));
        postList.add(new Broker("Archie22", "Two", "Eleven", 0.5));
        postList.add(new Broker("Ben jake", "Three", "Twelve", 0.5));
        postList.add(new Broker("guitarHERO", "Four", "Thirteen", 0.5));
        postList.add(new Broker("broker ben", "Five", "Fourteen", 0.5));
        postList.add(new Broker("candytimes jhon", "Six", "Fifteen", 0.5));
        postList.add(new Broker("test", "Seven", "Sixteen", 0.5));
        postList.add(new Broker("test", "Eight", "Seventeen", 0.5));
        postList.add(new Broker("test", "Nine", "Eighteen", 0.5));
    }

    /**
     * Get a full list of brokers from database.
     */
    private void getClientListFromBroker(){
        clientList = new ArrayList<>();
//        brokerDatabase = FirebaseDatabase.getInstance().getReference().child("Brokers");
        //need to insert all brokers into list and pass the list in the adapter.
    }
}
