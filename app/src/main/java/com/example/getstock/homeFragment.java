package com.example.getstock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Show a broker list to choose from.
 * user can click on a broker to go into his profile.
 *
 *
 */
public class homeFragment extends Fragment {

    private RecyclerView recommendRecyclerView; //Will hold our widgets.
    private List<UserPost> postList; //a random list that currently populates the page.
    private List<Broker> brokerList; //The actual list that will populate the page.
    private FloatingActionButton floatingActionButton; //floating + button.
    private CardView cardView;
    Fragment homeFragment = this; //Used adapter constructor.
    private DatabaseReference brokerDatabase; //Will hold our database reference.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        // Fill an fake list of items to be presented.
        fillExampleList();

        // Inflate the layout for this fragment
        recommendRecyclerView = view.findViewById(R.id.RecommendRecycler);
        recommendRecyclerView.setHasFixedSize(true);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recommendRecyclerView.setAdapter(new StockRecommendorAdapter(postList,view.getContext(),this));

        //Set up the floating button.
        floatingActionButton = view.findViewById(R.id.addPost);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postList.add(new UserPost("David Blaine", "One", "Ten"));
                recommendRecyclerView.setAdapter(new StockRecommendorAdapter(postList,view.getContext(), homeFragment));
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setTitle("Our Brokers");
    }

    private void fillExampleList() {
        postList = new ArrayList<>();
        postList.add(new UserPost("David Blaine", "One", "Ten"));
        postList.add(new UserPost("Archie22", "Two", "Eleven"));
        postList.add(new UserPost("Ben jake", "Three", "Twelve"));
        postList.add(new UserPost("guitarHERO", "Four", "Thirteen"));
        postList.add(new UserPost("broker ben", "Five", "Fourteen"));
        postList.add(new UserPost("candytimes jhon", "Six", "Fifteen"));
        postList.add(new UserPost("test", "Seven", "Sixteen"));
        postList.add(new UserPost("test", "Eight", "Seventeen"));
        postList.add(new UserPost("test", "Nine", "Eighteen"));
    }

    /**
     * Get a full list of brokers from database.
     */
    private void getBrokerListFromFB(){
        brokerList = new ArrayList<>();
//        brokerDatabase = FirebaseDatabase.getInstance().getReference().child("Brokers");
        //need to insert all brokers into list and pass the list in the adapter.
    }
}