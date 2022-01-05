package com.example.getstock;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StocksFragment extends Fragment {

    private RecyclerView recommendRecyclerView; //Will hold our widgets.
    private List<String> postList; //a random list that currently populates the page.
    private List<Broker> brokerList; //The actual list that will populate the page.
    private FloatingActionButton floatingActionButton; //floating + button.
    private CardView cardView;
    Fragment homeFragment = this; //Used adapter constructor.
    private DatabaseReference brokerDatabase; //Will hold our database reference.

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int userType;
    Broker broker;
    User user;
    Bundle args;
    String userId;

    StockAdapter stockAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_client
                , container, false);


        //Get user details
        userType = getArguments().getInt("userType");
        args = new Bundle();

        Log.d("",mAuth.getUid());

        if(userType == 1) { //broker

            broker = (Broker) getArguments().getSerializable("broker");

            //put args for next fragment.
            args.putInt("userType", 1);
            args.putSerializable("broker", broker);
            postList = broker.favorites;

        }
        else { //user

            user = (User) getArguments().getSerializable("user");

            //put args for next fragment.
            args.putInt("userType", 2);
            args.putSerializable("user", user);
            postList = user.favorites;
        }

//        fillExampleList();

        // Inflate the layout for this fragment
        recommendRecyclerView = view.findViewById(R.id.ClientRecycler);
        recommendRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        recommendRecyclerView.setLayoutManager(llm);
        stockAdapter = new StockAdapter(postList,view.getContext(),this);
        recommendRecyclerView.setAdapter(stockAdapter);



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
//        postList.add(new UserPost("David Blaine", "One", "Ten"));
//        postList.add(new UserPost("Archie22", "Two", "Eleven"));
//        postList.add(new UserPost("Ben jake", "Three", "Twelve"));
//        postList.add(new UserPost("guitarHERO", "Four", "Thirteen"));
//        postList.add(new UserPost("broker ben", "Five", "Fourteen"));
//        postList.add(new UserPost("candytimes jhon", "Six", "Fifteen"));
//        postList.add(new UserPost("test", "Seven", "Sixteen"));
//        postList.add(new UserPost("test", "Eight", "Seventeen"));
//        postList.add(new UserPost("test", "Nine", "Eighteen"));
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
