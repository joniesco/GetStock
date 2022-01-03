package com.example.getstock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This Class represents a fragment that contains a list of all brokers,
 * to be picked by a client and added to his broker list.
 * will be placed in the search fragment.
 */
public class BrokerListFragment extends Fragment {
    List<String> brokerIdList;
    List<Broker> brokerList; //list to hold all items.
    RecyclerView brokerListRecyclerView; //the layout.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //user logged in settings
    int userType; //Set user type to display the correct view.
    Bundle args;
    User user;
    String userId;
    Broker broker;
    View view;
    BrokerListAdapter brokerListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString("userId");
        args = new Bundle();
        user = (User) getArguments().getSerializable("user");

        args.putString("userId", userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //sets the view of this fragment to be fragment_broker_list.xml
        view = inflater.inflate(R.layout.fragment_broker_list, container, false);

        //populate our list.
        fillExampleList();
        //Make sure we pass a list that is already filtered, i.e. client won't see any brokers that he already has in his list.


        // create a recycler view and populate it, with our designed adapter.
        brokerListRecyclerView = view.findViewById(R.id.broker_recycler); //bind the id.
        brokerListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext()); //create a new linear layout programmatically.
        llm.setOrientation(RecyclerView.VERTICAL); //set it to vertical mode.
        brokerListRecyclerView.setLayoutManager(llm); //set our recycler view to contain the new linear layout.
        brokerListAdapter = new BrokerListAdapter(brokerList,view.getContext(),this, user,brokerIdList, userId);
        brokerListRecyclerView.setAdapter(brokerListAdapter);
        return view;
    }

    private void fillExampleList() {
        brokerList = new ArrayList<>();
        brokerIdList = new ArrayList<>();
        db.collection("Brokers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Log.d("", documentSnapshot.getId());
                            broker = documentSnapshot.toObject(Broker.class);
                            if(!broker.userRequests.containsKey(userId) && !broker.usersInvestmentFile.containsKey(userId) ) {
                                brokerList.add(broker);
                                brokerIdList.add(documentSnapshot.getId());
                            }
                        }

                        brokerListAdapter.notifyDataSetChanged(); //Notify our Recyclerview that we have an update.
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}