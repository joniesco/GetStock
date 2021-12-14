package com.example.getstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import yahoofinance.YahooFinance;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    //Will queue our requests.
    private EditText stockSymbol;
    private Button search;
    private ProgressBar progressBar;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        search = (Button) findViewById(R.id.SearchBtn);
        search.setOnClickListener(this);

        stockSymbol = (EditText) findViewById(R.id.txtSearch2);

        //welcome user
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        final TextView UserNameTextView = (TextView) findViewById(R.id.UserName);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile =snapshot.getValue((User.class));
                if(userProfile!= null){
                    String fullName =userProfile.fullName;
                    UserNameTextView.setText("Welcome " + fullName + "!!!");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(ProfileActivity.this,"something wrpng happend!",Toast.LENGTH_LONG).show();
            }
        });
        // end welcome user
    }

    @Override
    public void onClick(View view) {
        try {
            fetchStock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchStock() throws IOException {

        setContentView(R.layout.activity_search_result);
//        progressBar.setVisibility(View.VISIBLE);

        String symbol = stockSymbol.getText().toString().trim();
        final TextView answer = (TextView) findViewById(R.id.answer);
        answer.setText(YahooFinance.get(symbol).toString());
    }

    /**
     * Our Search function, gets a parameter and
     * sends an API request via StringRequest method,
     * in the onResponse function we continue the process.
     * @param nameSearch
     * @return
     */
    private StringRequest searchNameStringRequest(String nameSearch) {
        final String API = "&api_key=<<YOUR_API_KEY_HERE>>";
        final String NAME_SEARCH = "&q=";
        final String DATA_SOURCE = "&ds=Standard Reference";
        final String FOOD_GROUP = "&fg=";
        final String SORT = "&sort=r";
        final String MAX_ROWS = "&max=25";
        final String BEGINNING_ROW = "&offset=0";
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/search/?format=json";

        String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;
        String test_url = "https://eodhistoricaldata.com/api/search/"+nameSearch+"?api_token=61b664d1a29e60.51382518";
        // 1st param => type of method (GET/PUT/POST/PATCH/etc)
        // 2nd param => complete url of the API
        // 3rd param => Response.Listener -> Success procedure
        // 4th param => Response.ErrorListener -> Error procedure
        return new StringRequest(Request.Method.GET, test_url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        try {
//                            JSONObject result = new JSONObject(response);
                            JSONArray result1 = new JSONArray(response);
                            //Toast.makeText(ProfileActivity.this,response, Toast.LENGTH_LONG).show();

                            //Create an Intent to pass to next page to display the result.
                            Intent intent = new Intent(ProfileActivity.this, SearchResult.class);
                            Bundle b = new Bundle();
                            b.putString("ans", response);

                            intent.putExtras(b); //Put your id to your next Intent
                            startActivity(intent); //Start next activity once we have our response.
                            Toast.makeText(ProfileActivity.this, "test", Toast.LENGTH_LONG).show();
//                            finish();
//                            int maxItems = result.getInt("end");
//                            JSONArray resultList = result.getJSONArray("item");


                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Intent intent = new Intent(ProfileActivity.this, SearchResult.class);
                            Bundle b = new Bundle();
                            b.putString("ans", response);

                            intent.putExtras(b); //Put your id to your next Intent
                            startActivity(intent); //Start next activity once we have our response.
                            //Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } // public void onResponse(String response)
                },


                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(ProfileActivity.this, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
    }
}