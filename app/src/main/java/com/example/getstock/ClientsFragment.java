package com.example.getstock;

import static com.example.getstock.R.id.ClientRecycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import yahoofinance.Stock;

/**
 * This fragment displays all the clients files, the broker has.
 * (A yellowish widget on the home screen) , contains small amounts of data
 * each widget can be clicked to move to the full client details.
 */
public class ClientsFragment extends Fragment implements ClientWidgetAdapter.recycleClickListener {

    private RecyclerView recommendRecyclerView; //Will hold our widgets.
    private List<String> postList; //a random list that currently populates the page.
    private List<Broker> clientList; //The actual list that will populate the page.
    private FloatingActionButton floatingActionButton; //floating + button.
    private CardView cardView;
    Fragment homeFragment = this; //Used adapter constructor.
    private DatabaseReference brokerDatabase; //Will hold our database reference.
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Will store data to pass to next fragment.
    Bundle args;

    //Get our DB instance.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //user logged in related data.
    int userType;
    User user;
    Broker broker;
    ClientWidgetAdapter stockRecommendorAdapter;

    Context ct = getContext();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_client
                , container, false);


        // Fill an fake list of items to be presented.
//        fillExampleList();

        // Inflate the layout for this fragment
        recommendRecyclerView = view.findViewById(ClientRecycler);
        recommendRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(RecyclerView.HORIZONTAL);
        recommendRecyclerView.setLayoutManager(llm);




        args = new Bundle();


        //Set user settings
        userType = getArguments().getInt("userType");

        if(userType == 1) { //broker

            broker = (Broker) getArguments().getSerializable("broker");

            //put args for next fragment.
            args.putInt("userType", 1);
            args.putSerializable("broker", broker);

            //update client list

            postList = new ArrayList<>(broker.IdsToNames.keySet());
//            stockRecommendorAdapter.notifyDataSetChanged();
            stockRecommendorAdapter = new ClientWidgetAdapter(postList,view.getContext(),this, this);
            recommendRecyclerView.setAdapter(stockRecommendorAdapter);

//            updateBroker();
        }
        else { //user

            user = (User) getArguments().getSerializable("user");

            //put args for next fragment.
            args.putInt("userType", 2);
            args.putSerializable("user", user);

//            stockRecommendorAdapter = new ClientWidgetAdapter(postList,view.getContext(),this, this);
//            recommendRecyclerView.setAdapter(stockRecommendorAdapter);
        }



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }

    private void fillExampleList() {
        postList = new ArrayList<>();
//        postList.add(new Broker("David Blaine", "One", "Ten", 0.5));
//        postList.add(new Broker("Archie22", "Two", "Eleven", 0.5));
//        postList.add(new Broker("Ben jake", "Three", "Twelve", 0.5));
//        postList.add(new Broker("guitarHERO", "Four", "Thirteen", 0.5));
//        postList.add(new Broker("broker ben", "Five", "Fourteen", 0.5));
//        postList.add(new Broker("candytimes jhon", "Six", "Fifteen", 0.5));
//        postList.add(new Broker("test", "Seven", "Sixteen", 0.5));
//        postList.add(new Broker("test", "Eight", "Seventeen", 0.5));
//        postList.add(new Broker("test", "Nine", "Eighteen", 0.5));

    }

    public void updateBroker(){
        db.collection("Brokers")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                broker = (Broker)documentSnapshot.toObject(Broker.class);
                postList = new ArrayList<>(broker.IdsToNames.keySet());
                Log.d("", ""+postList.size() + "this");
                stockRecommendorAdapter.notifyDataSetChanged();

//                //StockRecommendorAdapter is list of Clients
//                stockRecommendorAdapter = new ClientWidgetAdapter(postList,
//                        getContext(),
//                        getTargetFragment(),
//                        );
                recommendRecyclerView.setAdapter(stockRecommendorAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void updateUser(){
        db.collection("Users")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    /**
     * Get a full list of brokers from database.
     */
    private void getClientListFromBroker(){
        clientList = new ArrayList<>();
//        brokerDatabase = FirebaseDatabase.getInstance().getReference().child("Brokers");
        //need to insert all brokers into list and pass the list in the adapter.
    }


    @Override
    public void myClickListener(int position) {

        Log.d("", ""+postList.size() + "this");

        String userId = postList.get(position);


        args.putSerializable("portofolio", broker.usersInvestmentFile.get(userId));

        Intent intent = new Intent(getContext(),PortofolioActivity.class) ;
        intent.putExtras(args);

        startActivity(intent);
    }
}
