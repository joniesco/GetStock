package com.example.getstock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class UserRequestsFragment extends Fragment {

    RecyclerView userRecyclerView;
    List<User> userList;
    UserRequestAdapter userRequestsAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    //User settings
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
        broker = (Broker) getArguments().getSerializable("broker");

        args.putString("userId", userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_requests, container, false);



        //populate our list.
        fillExampleList();
        //Make sure we pass a list that is already filtered, i.e. client won't see any brokers that he already has in his list.

        //Get List of ids.
        List<String> userIds = new ArrayList<>();
        for(String uid: broker.userRequests.keySet()){
            userIds.add(uid);
        }

        // create a recycler view and populate it, with our designed adapter.
        userRecyclerView = view.findViewById(R.id.user_request_recycler); //bind the id.
        userRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext()); //create a new linear layout programmatically.
        llm.setOrientation(RecyclerView.VERTICAL); //set it to vertical mode.
        userRecyclerView.setLayoutManager(llm); //set our recycler view to contain the new linear layout.
        userRequestsAdapter = new UserRequestAdapter(userList,view.getContext(),this, broker, userIds, userId);
        userRecyclerView.setAdapter(userRequestsAdapter); //inserting adapter.


        return view;
    }
    private void fillExampleList() {
        userList = new ArrayList<>();

        for(String uid: broker.userRequests.keySet()){
            db.collection("Users").document(uid)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            user = documentSnapshot.toObject(User.class);
                            userList.add(user);
                            userRequestsAdapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
//
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
//        userList.add(new User());
    }
}