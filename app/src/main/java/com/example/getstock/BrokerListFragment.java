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

/**
 * This Class represents a fragment that contains a list of all brokers,
 * to be picked by a client and added to his broker list.
 * will be placed in the search fragment.
 */
public class BrokerListFragment extends Fragment {

    List<UserPost> postList; //list to hold all items.
    RecyclerView brokerListRecyclerView; //the layout.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //sets the view of this fragment to be fragment_broker_list.xml
        View view = inflater.inflate(R.layout.fragment_broker_list, container, false);

        //populate our list.
        fillExampleList();

        // create a recycler view and populate it, with our designed adapter.
        brokerListRecyclerView = view.findViewById(R.id.broker_recycler); //bind the id.
        brokerListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext()); //create a new linear layout programmatically.
        llm.setOrientation(RecyclerView.VERTICAL); //set it to vertical mode.
        brokerListRecyclerView.setLayoutManager(llm); //set our recycler view to contain the new linear layout.
        brokerListRecyclerView.setAdapter(new BrokerListAdapter(postList,view.getContext(),this)); //inserting adapter.

        return view;
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
}