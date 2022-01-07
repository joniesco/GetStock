package com.example.getstock;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.verdelhan.ta4j.Rule;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class SearchFragment extends Fragment {

    //Our views
    public ImageButton pressSearch;
    public EditText inputBox;
    private TextView title;

    Context ct;

    //Create our model
    SearchModel searchModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //Create our model.
        searchModel = new SearchModel(getArguments());

        //get Context.
        ct = getContext();

        //Connect our views
        title = view.findViewById(R.id.head_line);
        pressSearch = view.findViewById(R.id.press_search);
        inputBox = view.findViewById(R.id.input_search);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create our stocks page.
        Fragment stockFullPage = new StockFullPageFragment();
        stockFullPage.setArguments(searchModel.getBundle());

        //


        //Add our click listener.
        pressSearch.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            try {
                searchModel.CheckStock(inputBox.getText().toString());
                if(searchModel.isQueryGood == true){

                    searchModel.addToBundle("symbol" ,inputBox.getText().toString());
                    stockFullPage.setArguments(searchModel.getBundle());

                    FragmentManager fragmentManager = ((FragmentActivity) ct).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, stockFullPage);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
                else {
                    Log.d("", "not very good success");
                }


            } catch (IOException e) {
                Log.d("", "No such Stock exists.");
            }

            }

        });

        //Set our view according to user.
        if(searchModel.userType == 2) {

            title.setText("Our Brokers");

            //Create fragment.
            Fragment brokerListFragment = new BrokerListFragment();
            brokerListFragment.setArguments(searchModel.getBundle());

            //Create the small clients list.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.broker_list_fragment_container, brokerListFragment).commit();
        }
        else {

            title.setText("Client Requests");

            //Create fragment.
            Fragment userRequestFragment = new UserRequestsFragment();
            userRequestFragment.setArguments(searchModel.getBundle());

            //Create the small clients list.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.broker_list_fragment_container, userRequestFragment).commit();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setTitle("Search stock symbol");
    }

    }


