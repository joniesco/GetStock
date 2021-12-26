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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    private RecyclerView recommendRecyclerView;
    private List<UserPost> postList;
    private FloatingActionButton floatingActionButton;
    private CardView cardView;
    Fragment homeFragment = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        fillExampleList();

        // Inflate the layout for this fragment
        recommendRecyclerView = view.findViewById(R.id.RecommendRecycler);
        recommendRecyclerView.setHasFixedSize(true);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recommendRecyclerView.setAdapter(new StockRecommendorAdapter(postList,view.getContext(),this));
//        cardView = (CardView) view.findViewById(R.id.click_post);
//
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.fragment_container, new showPostFragment());
//                transaction.commit();
//            }
//        });

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
        ((AppCompatActivity) getActivity()).setTitle("User Recommendation");
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