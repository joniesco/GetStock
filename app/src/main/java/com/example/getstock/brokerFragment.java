package com.example.getstock;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 *
 */
public class brokerFragment extends Fragment {
    //Init page elements
    TextView name;
    ImageView profilePic;
    Bundle args;

    //Get our DB instance.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).setTitle("Portfolio");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get args from previous page.
//        args = this.getArguments();
//        String email = args.getString("userId");
//        String userType = args.getString("UserType");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_broker, container, false);
        profilePic = view.findViewById(R.id.profile_pic);
        name = view.findViewById(R.id.desc);

        //Change profile pic according to user logged in.
//        if(userType.equals("Broker")){
//            profilePic.setImageResource(R.drawable.business_man2);
//        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create our 2 fragments.
        Fragment clientsFragment = new ClientsFragment();
        Fragment stocksFragment = new StocksFragment();

        //Create the bundle to pass to next page.
        Bundle userData = new Bundle();
        userData.putString("user", "data");
        String TAG = "";

        //users email
        String email = "stam@stam";



        //CREATE
        db.collection("Brokers")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Broker test = document.toObject(Broker.class);
                                if(test.email.equals("sam@gmail.com")){
                                    Log.d(TAG, "It's a match!");
                                    // We have our Broker Instance rdy.
                                    name.setText(test.fullName);
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Transfer user data to clients fragment and stocks fragment.
        clientsFragment.setArguments(userData);
        stocksFragment.setArguments(userData);

        //Create the small clients list.
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.clients_fragment_container, clientsFragment).commit();

        //Create the small favorite stocks.
        FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
        transaction1.replace(R.id.stocks_fragment_container, stocksFragment).commit();
    }
}