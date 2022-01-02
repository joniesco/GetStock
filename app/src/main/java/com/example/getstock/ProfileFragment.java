package com.example.getstock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 *
 *
 */
public class ProfileFragment extends Fragment {

    //Our views
    Switch notificationSwitch;
    TextView profileName;
    TextView numOfClients;
    TextView notifications;
    TextView balance;
    TextView clientsOrBrokers;

    //user logged in settings
    User user;
    Broker broker;
    int userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Bind views
        notificationSwitch = view.findViewById(R.id.switch_notification);
        profileName = view.findViewById(R.id.profile_name);
        numOfClients = view.findViewById(R.id.num_of_clients);
        notifications = view.findViewById(R.id.num_of_notifications);
        clientsOrBrokers = view.findViewById(R.id.clients);
        balance = view.findViewById(R.id.balance);

        //Setup user / broker instance
        userType = getArguments().getInt("userType");

        if(userType == 1){ //broker
            broker = (Broker) getArguments().getSerializable("broker");
            profileName.setText(broker.getFullName());
            numOfClients.setText("0");
            if(broker.getUsersInvesting().keySet()!=null && broker.getUsersInvesting().keySet().size()!=0) {
                numOfClients.setText(broker.getUsersInvesting().keySet().size());
            }
            balance.setText(broker.getInitialMoney().toString());
            notifications.setText("0");
        }
        else { //user
            user = (User)  getArguments().getSerializable("user");
            profileName.setText(user.getFullName());
            clientsOrBrokers.setText("Brokers");
            numOfClients.setText("0");
            if(user.getBrokerMap().keySet() != null && user.getBrokerMap().keySet().size() != 0) {
                numOfClients.setText(user.getBrokerMap().keySet().size());
            }
            balance.setText(user.getInitialMoney());
            notifications.setText("0");

        }

        //Define what happens when switch is on.
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //if b = true, switch is on do something.
                // i.e. we want to allow notification, so first lets understand how to do notification.

                //if b = false, switch is off do something.

            }
        });

        return view;
    }
}