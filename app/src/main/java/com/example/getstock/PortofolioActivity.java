package com.example.getstock;

import static com.example.getstock.R.id.ClientRecycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PortofolioActivity} factory method to
 * create an instance of this fragment.
 */
public class PortofolioActivity extends AppCompatActivity {

    private RecyclerView stocksRecycleView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Get our DB instance.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //user logged in related data.
    int userType;
    User user;
    Broker broker;
    PortofolioAdapter portofolioAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        setContentView(R.layout.fragment_portofolio);
        stocksRecycleView =  findViewById(R.id.portofolioRecycler);
        stocksRecycleView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.HORIZONTAL);
        stocksRecycleView.setLayoutManager(llm);

        ClientPortofolio portofolio = (ClientPortofolio) bundle.getSerializable("portofolio");
        portofolioAdapter = new PortofolioAdapter( portofolio, this );
        stocksRecycleView.setAdapter(portofolioAdapter);

    }


}