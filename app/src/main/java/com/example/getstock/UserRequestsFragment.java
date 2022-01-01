package com.example.getstock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class UserRequestsFragment extends Fragment {

    RecyclerView userRecyclerView;
    List<User> userList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_requests, container, false);

        //populate our list.
        fillExampleList();
        //Make sure we pass a list that is already filtered, i.e. client won't see any brokers that he already has in his list.


        // create a recycler view and populate it, with our designed adapter.
        userRecyclerView = view.findViewById(R.id.user_request_recycler); //bind the id.
        userRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext()); //create a new linear layout programmatically.
        llm.setOrientation(RecyclerView.VERTICAL); //set it to vertical mode.
        userRecyclerView.setLayoutManager(llm); //set our recycler view to contain the new linear layout.
        userRecyclerView.setAdapter(new UserRequestAdapter(userList,view.getContext(),this)); //inserting adapter.


        return view;
    }
    private void fillExampleList() {
        userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
    }
}