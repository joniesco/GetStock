package com.example.getstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private RadioButton broker, user ;


    private TextView banner , registerUser;
    private EditText editTextFullName , editTextEmail, editTextPassword,editTextAge;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String usersType = "None";
    private static final String TAG = "Register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        registerUser= (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.Age);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText) findViewById(R.id.password);

        radioGroup = findViewById(R.id.user_type);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        broker = findViewById(R.id.broker);
        broker.setOnClickListener(this);

        user = findViewById(R.id.user);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
            case R.id.broker:
                usersType = "broker";
                break;
            case R.id.user:
                usersType = "user";
                break;
        }

    }



    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("email is invalid!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Min 6 characters!!");
            editTextPassword.requestFocus();
            return;
        }
        if(usersType.equals("None")){
            Toast.makeText(Register.this,"Please select User Type",Toast.LENGTH_LONG)
                    .show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful() && usersType.equals("user")){

//                Map<String, Double> map = new HashMap<String, Double>();

                User user = new User(fullName,age,email, "user", "1", new HashMap<String, Double>());
                String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                db.collection("Users").document(UID)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(Register.this,"User has been registered sucssesfully",Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this,"User has failed to register",Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                progressBar.setVisibility(View.GONE);

            }
            else if(task.isSuccessful() && usersType.equals("broker")){

                Broker broker =  new Broker(fullName,age,email,900.0);
                String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                db.collection("Brokers").document(UID)
                        .set(broker)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(Register.this,"Broker has been registered sucssesfully",Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this,"Broker has failed to register",Toast.LENGTH_LONG)
                                        .show();
                            }
                        });



                progressBar.setVisibility(View.GONE);

//                FirebaseDatabase.getInstance().getReference("Brokers")
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .setValue(broker).addOnCompleteListener(task1 -> {
//                           if(task1.isSuccessful()){
//                               Toast.makeText(Register.this,"Broker has been registered sucssesfully",Toast.LENGTH_LONG)
//                                       .show();
//                           }else{Toast.makeText(Register.this,"Broker has failed to register",Toast.LENGTH_LONG)
//                                   .show();
//
//                           }
//                    progressBar.setVisibility(View.GONE);
//                });
            }
            else{Toast.makeText(Register.this,"failed to register",Toast.LENGTH_LONG)
                    .show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }
}


//            else if(task.isSuccessful() && usersType.equals("broker")){
//                Broker broker =  new Broker(100);
//                FirebaseDatabase.getInstance().getReference("Brokers")
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .setValue(broker).addOnCompleteListener(task1 -> {
//                           if(task1.isSuccessful()){
//                               Toast.makeText(Register.this,"Broker has been registered sucssesfully",Toast.LENGTH_LONG)
//                                       .show();
//                           }else{Toast.makeText(Register.this,"Broker has failed to register",Toast.LENGTH_LONG)
//                                   .show();
//
//                           }
//                    progressBar.setVisibility(View.GONE);
//                });
//            }