package com.example.getstock;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link brokerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class brokerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView name;
    ImageView profilePic;
    Bundle args;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public brokerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment brokerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static brokerFragment newInstance(String param1, String param2) {
        brokerFragment fragment = new brokerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        args = this.getArguments();
        String email = args.getString("userId");
        String userType = args.getString("UserType");

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_broker, container, false);
        profilePic = view.findViewById(R.id.profile_pic);
        name = view.findViewById(R.id.desc);

        if(userType.equals("Broker")){
            profilePic.setImageResource(R.drawable.business_man2);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fragment childFragment = new ClientsFragment();
        Fragment stocksFragment = new StocksFragment();

        Bundle userData = new Bundle();
        userData.putString("user", "data");
        String TAG = "";

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
        childFragment.setArguments(userData);
        stocksFragment.setArguments(userData);

        //Create the small clients list.
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.clients_fragment_container, childFragment).commit();

        //Create the small favorite stocks.
        FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
        transaction1.replace(R.id.stocks_fragment_container, stocksFragment).commit();
    }
}