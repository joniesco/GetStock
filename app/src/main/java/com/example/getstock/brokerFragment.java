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
 * Home Broker page
 */
public class brokerFragment extends Fragment {
    //Init page elements.
    TextView name;
    TextView numOfNotifications;
    ImageView profilePic;

    //Will store data to pass to next fragment.
    Bundle args;

    //Get our DB instance.
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //user logged in related data.
    int userType;
    User user;
    Broker broker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).setTitle("Portfolio");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_broker, container, false);
        profilePic = view.findViewById(R.id.profile_pic);
        name = view.findViewById(R.id.desc);
        numOfNotifications = view.findViewById(R.id.num_of_notifications);


        args = new Bundle();
        //Set user settings
        userType = getArguments().getInt("userType");
        if(userType == 1) { //broker

            broker = (Broker) getArguments().getSerializable("broker");

            //put args for next fragment.
            args.putInt("userType", 1);
            args.putSerializable("broker", broker);

            //Change text in page.
            name.setText(broker.getFullName());
            numOfNotifications.setText("You've got " + broker.notifications.size() + " notifications!");
        }
        else { //user

            user = (User) getArguments().getSerializable("user");

            //put args for next fragment.
            args.putInt("userType", 2);
            args.putSerializable("user", user);

            //Change text in page.
            name.setText(user.getFullName());
            numOfNotifications.setText("You've got " + user.notifications.size() + " notifications!");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create our 2 fragments.
        Fragment clientsFragment = new ClientsFragment();
        Fragment stocksFragment = new StocksFragment();

        //Transfer user data to clients fragment and stocks fragment.
        clientsFragment.setArguments(args);
        stocksFragment.setArguments(args);

        //Create the small clients list.
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.clients_fragment_container, clientsFragment).commit();

        //Create the small favorite stocks.
        FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
        transaction1.replace(R.id.stocks_fragment_container, stocksFragment).commit();
    }
}