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

    List<Broker> brokerList; //list to hold all items.
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
        //Make sure we pass a list that is already filtered, i.e. client won't see any brokers that he already has.


        // create a recycler view and populate it, with our designed adapter.
        brokerListRecyclerView = view.findViewById(R.id.broker_recycler); //bind the id.
        brokerListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext()); //create a new linear layout programmatically.
        llm.setOrientation(RecyclerView.VERTICAL); //set it to vertical mode.
        brokerListRecyclerView.setLayoutManager(llm); //set our recycler view to contain the new linear layout.
        brokerListRecyclerView.setAdapter(new BrokerListAdapter(brokerList,view.getContext(),this)); //inserting adapter.

        return view;
    }

    private void fillExampleList() {
        brokerList = new ArrayList<>();
        brokerList.add(new Broker("David Blaine", "One", "Ten", 0.5));
        brokerList.add(new Broker("Archie22", "Two", "Eleven",0.3));
        brokerList.add(new Broker("Ben jake", "Three", "Twelve", 0.3));
        brokerList.add(new Broker("guitarHERO", "Four", "Thirteen", 0.3));
        brokerList.add(new Broker("broker ben", "Five", "Fourteen",0.5));
        brokerList.add(new Broker("candytimes jhon", "Six", "Fifteen",0.1));
        brokerList.add(new Broker("test", "Seven", "Sixteen",0.2));
        brokerList.add(new Broker("test", "Eight", "Seventeen", 0.4));
        brokerList.add(new Broker("test", "Nine", "Eighteen", 0.2));
    }
}