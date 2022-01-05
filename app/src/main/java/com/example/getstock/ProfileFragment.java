package com.example.getstock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    //for profile details
    EditText fullname;
    EditText brokerCommission, buyingCommission, sellingCommission ;
    Button updateDetails;
    ProgressBar progressBar;

    //user logged in settings
    User user;
    Broker broker;
    int userType;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


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
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarProfile);

        //Setup user / broker instance
        userType = getArguments().getInt("userType");

        if(userType == 1){ //broker


            // set status params
            broker = (Broker) getArguments().getSerializable("broker");
            profileName.setText(broker.getFullName());
            numOfClients.setText("0");
            if(broker.getUsersInvesting().keySet()!=null && broker.getUsersInvesting().keySet().size()!=0) {
                numOfClients.setText(broker.getUsersInvesting().keySet().size());
            }
            balance.setText(broker.getInitialMoney().toString());
            notifications.setText("0");

            // profile details
            fullname = (EditText) view.findViewById(R.id.full_name_box);
            buyingCommission = (EditText) view.findViewById(R.id.buyComBox);
            sellingCommission = (EditText) view.findViewById(R.id.sellComBox);
            brokerCommission = (EditText) view.findViewById(R.id.commission);
            updateDetails = (Button)view.findViewById(R.id.button);

            fullname.setText(broker.getFullName());
            buyingCommission.setText(broker.getBuyingCommission().toString());
            sellingCommission.setText(broker.getSellingCommission().toString());
            brokerCommission.setText(broker.getBrokerCommission().toString());


            updateDetails.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);


                    String FullName = fullname.getText().toString();
                    Double BuyCom = Double.valueOf(buyingCommission.getText().toString().trim());
                    Double SellCom = Double.valueOf(sellingCommission.getText().toString().trim());
                    Double BrokerCom = Double.valueOf(brokerCommission.getText().toString().trim());

                    broker.setFullName(FullName);
                    broker.setBrokerCommission(BrokerCom);
                    broker.setBuyingCommission(BuyCom);
                    broker.setSellingCommission(SellCom);

                    String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    DocumentReference brokerRef = db.collection("Brokers")
                            .document(UID);

                    brokerRef.update("fullName", FullName,
                            "brokerCommission", BrokerCom,
                            "buyingCommission", BuyCom,
                            "sellingCommission", SellCom)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(),"Details has been changed successfully",Toast.LENGTH_LONG)
                                            .show();
                                    Bundle args = new Bundle();
                                    args.putInt("userType", 1);
                                    args.putSerializable("broker", broker);
                                    args.putString("userId", UID);

                                    Fragment selectedFragment = new ProfileFragment();
                                    selectedFragment.setArguments(args);

                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            selectedFragment).commit();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(),"Failed to change details",Toast.LENGTH_LONG)
                                            .show();
                                }
                            });



                }
            });


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