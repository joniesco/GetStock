package com.example.getstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This is a screen shown after a successful login process.
 */
public class afterLogin extends AppCompatActivity {
    String userId;
    Bundle args;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int userType;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        userId = intent.getExtras().getString("userId"); //This holds our user id.
//
//        args = new Bundle();
//        args.putString("userId", userId);
//
//        Toast.makeText(afterLogin.this, userId, Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_after_login);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        String TAG = "";


        //Search the broker collection for the email.
//        db.collection("Brokers")
//
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            flag = true;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Broker test = document.toObject(Broker.class);
//                                if(test.email.equals("sam@gmail.com")){
//                                    flag=true;
//                                    Log.d(TAG, "It's a match!");
//                                    // We have our Broker Instance rdy.
//                                    args.putString("UserType", test.userType);
//                                }
//
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        //SEARCH USERS
        //Search the broker collection for the email.
//        db.collection("Users")
//
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            flag = true;
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Broker test = document.toObject(Broker.class);
//                                if(test.email.equals("sam@gmail.com")){
//                                    flag=true;
//                                    Log.d(TAG, "It's a match!");
//                                    // We have our Broker Instance rdy.
//                                    args.putString("UserType", test.userType);
//                                }
//
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

    }

    /**
     * add a listener to the bottom navigation bar
     * This is where the magic happens!
     * A new fragment is created according to the users choice.
     * We then swap fragment with getSupportFragment commit action.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new brokerFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                    }
                    //Passing userId to its child.
//                    selectedFragment.setArguments(args);

                    //Set the display
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}