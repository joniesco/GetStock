package com.example.getstock;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.getstock.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.Query;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText editTextEmail,getEditTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userType;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        signIn = (Button) findViewById(R.id.loginbtn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.Email);
        getEditTextPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.loginbtn:
                userLogin();

        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = getEditTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Please insert a email");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            getEditTextPassword.setError("Please insert a password");
            getEditTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            getEditTextPassword.setError("Min password length is 6 characters!");
            getEditTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    DocumentReference brokersRef = db.collection("Brokers").document(UID);

                    DocumentReference usersRef = db.collection("Users").document(UID);

                    //check if it is a broker
                    brokersRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    progressBar.setVisibility(View.GONE);

                                    Broker broker = document.toObject(Broker.class);
                                    Intent intent = new Intent(MainActivity.this,afterLogin.class) ;
                                    intent.putExtra("broker", broker);
                                    intent.putExtra("userType", 1);
                                    Toast.makeText(MainActivity.this,"welcome broker "+ broker.getFullName(),Toast.LENGTH_LONG)
                                            .show();
                                    startActivity(intent);


                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                    usersRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    progressBar.setVisibility(View.GONE);

                                    User user = document.toObject(User.class);
                                    Intent intent = new Intent(MainActivity.this,afterLogin.class) ;
                                    intent.putExtra("user", user);
                                    intent.putExtra("userType", 2);
                                    Toast.makeText(MainActivity.this,"welcome user "+ user.getFullName(),Toast.LENGTH_LONG)
                                            .show();
                                    startActivity(intent);


                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });



                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}