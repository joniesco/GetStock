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

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private TextView textView;
    private TextView title;
    final String[] s = {""};
    Context ct;

    //user logged in settings
    int userType; //Set user type to display the correct view.
    Bundle args;
    User user;
    Broker broker;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        textView = view.findViewById(R.id.yahoo_stock);
        ct = getContext();
        title = view.findViewById(R.id.head_line);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        userType = getArguments().getInt("userType");
        userId = getArguments().getString("userId");
        args = new Bundle();

        if(userType == 1) { //broker

            broker = (Broker) getArguments().getSerializable("broker");

            //put args for next fragment.
            args.putInt("userType", 1);
            args.putSerializable("broker", broker);

        }
        else { //user

            user = (User) getArguments().getSerializable("user");

            //put args for next fragment.
            args.putInt("userType", 2);
            args.putSerializable("user", user);

        }

        args.putString("userId", userId);
        Fragment stockFullPage = new StockFullPageFragment();
        stockFullPage.setArguments(args);

        textView.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {

                FragmentManager fragmentManager = ((FragmentActivity) ct).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, stockFullPage);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Intent intent = new Intent(ct, widgetClick.class);
//                intent.putExtra("Code", "asd");
//                ct.startActivity(intent);
            }

        });
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void unused) {
                textView.setText(s[0]);

            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    s[0] = YahooFinance.get("INTC").getSymbol();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();

        if(userType == 2) {

            title.setText("Our Brokers");

            //Create fragment.
            Fragment brokerListFragment = new BrokerListFragment();
            brokerListFragment.setArguments(args);

            //Create the small clients list.
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.broker_list_fragment_container, brokerListFragment).commit();
        }
        else if(userType == 1){

            title.setText("Client Requests");
            //Create fragment.
            Fragment userRequestFragment = new UserRequestsFragment();
            userRequestFragment.setArguments(args);

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

        userType = getArguments().getInt("userType");

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    }


