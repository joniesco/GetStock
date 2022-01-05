package com.example.getstock;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


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

    CardView notificationButton;

    ImageView profileImage;
    ImageButton changePicture;
    StorageReference storageReference;
    FirebaseAuth fAuth;

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

        notificationButton = view.findViewById(R.id.notification_button);

        //image
        profileImage =view.findViewById(R.id.businessIcon);
        changePicture = view.findViewById(R.id.add_photo);
        storageReference= FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid()+ "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


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
            //Notifications
            notifications.setText(Integer.toString(broker.getUserRequests().size()));
            notificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment userRequestFragment = new UserRequestsFragment();
                    Bundle args = new Bundle();
                    args.putInt("userType", 1);
                    args.putSerializable("broker", broker);
                    args.putString("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    userRequestFragment.setArguments(args);

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            userRequestFragment).commit();
                }
            });




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
                numOfClients.setText(Integer.toString(user.getBrokerMap().keySet().size()));
            }
            balance.setText(user.getInitialMoney());
            notifications.setText(Integer.toString(user.notifications.size()));

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

        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGaleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGaleryIntent,1000);

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri =data.getData();
//                profileImage.setImageURI(imageUri);
                uploadImageToFireBase(imageUri);

            }
        }
    }

    private void uploadImageToFireBase(Uri imageUri) {
        StorageReference fileRef= storageReference.child("users/" + fAuth.getCurrentUser().getUid()+ "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profileImage.getContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}