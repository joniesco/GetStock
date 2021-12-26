package com.example.getstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * This class contains our search screen
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    //Will queue our requests.
    private RequestQueue queue;
    private EditText SearchBox;
    private TextView SearchBtn;
    private FirebaseUser user;
    private DatabaseReference reference;
    private  String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // initializing the queue object
        queue = Volley.newRequestQueue(this);

        //Set search button
        SearchBtn = (Button) findViewById(R.id.SearchBtn);
        SearchBtn.setOnClickListener(this);

        //Set search box
        SearchBox = (EditText) findViewById(R.id.txtSearch2);


        user=FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID= user.getUid();
        final TextView greeting = (TextView) findViewById(R.id.WelcomeMessage);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile =snapshot.getValue(User.class);
                if(userProfile!=null){
                    String fullName = userProfile.fullName;
                    greeting.setText("Welcome "+ fullName + "!");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"something wrong happend", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        //Will hold our string request.
        StringRequest stringRequest = searchNameStringRequest(SearchBox.getText().toString());
//        stringRequest.setTag(TAG_SEARCH_NAME);

        // executing the request (adding to queue)
        queue.add(stringRequest);
    }

    /**
     * Our Search function, gets a parameter and
     * sends an API request via StringRequest method,
     * in the onResponse function we continue the process.
     * @param nameSearch
     * @return
     */
    private StringRequest searchNameStringRequest(String nameSearch) {
        String url = "https://eodhistoricaldata.com/api/search/"+nameSearch+"?api_token=61c1113f825093.01855527";

        return new StringRequest(Request.Method.GET, url,
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
                            ///////////////FAKE RESPONSE///////////////////////
                            String fakeResponse = "[{\"Code\":\"APL\",\"Exchange\":\"KAR\",\"Name\":\"Attock Petroleum Limited\",\"Type\":\"Common Stock\",\"Country\":\"Pakistan\",\"Currency\":\"PKR\",\"ISIN\":\"PK0082901015\",\"previousClose\":303,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"AU\",\"Name\":\"Antipodes Global Investment Company Limited\",\"Type\":\"Common Stock\",\"Country\":\"Australia\",\"Currency\":\"AUD\",\"ISIN\":\"AU000000APL8\",\"previousClose\":1.17,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"F\",\"Name\":\"Alpha Pro Tech Ltd\",\"Type\":\"Common Stock\",\"Country\":\"Germany\",\"Currency\":\"EUR\",\"ISIN\":null,\"previousClose\":5.33,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"STU\",\"Name\":\"ALPHA PRO TECH (APL.SG)\",\"Type\":\"Common Stock\",\"Country\":\"Germany\",\"Currency\":\"EUR\",\"ISIN\":null,\"previousClose\":5.31,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"V\",\"Name\":\"Appulse Corporation\",\"Type\":\"Common Stock\",\"Country\":\"Canada\",\"Currency\":\"CAD\",\"ISIN\":\"CA03833A1049\",\"previousClose\":0.31,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"WAR\",\"Name\":\"Ampli S.A.\\r\\n\",\"Type\":\"Common Stock\",\"Country\":\"Poland\",\"Currency\":\"PLN\",\"ISIN\":\"PLAMPLI00019\",\"previousClose\":0.525,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"BE\",\"Name\":\"ALPHA PRO TECH\",\"Type\":\"Common Stock\",\"Country\":\"Germany\",\"Currency\":\"EUR\",\"ISIN\":null,\"previousClose\":5.31,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APL\",\"Exchange\":\"MU\",\"Name\":\"ALPHA PRO TECH\",\"Type\":\"Common Stock\",\"Country\":\"Germany\",\"Currency\":\"EUR\",\"ISIN\":null,\"previousClose\":5.45,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLP\",\"Exchange\":\"TA\",\"Name\":\"Apollo Power Ltd\",\"Type\":\"Common Stock\",\"Country\":\"Israel\",\"Currency\":\"ILA\",\"ISIN\":\"IL0010821143\",\"previousClose\":2786,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLY\",\"Exchange\":\"TA\",\"Name\":\"Aerodrome Group Ltd\",\"Type\":\"Common Stock\",\"Country\":\"Israel\",\"Currency\":\"ILA\",\"ISIN\":\"IL0003630105\",\"previousClose\":120,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLS\",\"Exchange\":\"US\",\"Name\":\"Apellis Pharmaceuticals Inc\",\"Type\":\"Common Stock\",\"Country\":\"USA\",\"Currency\":\"USD\",\"ISIN\":\"US03753U1060\",\"previousClose\":46.31,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLE\",\"Exchange\":\"US\",\"Name\":\"Apple Hospitality REIT Inc\",\"Type\":\"Common Stock\",\"Country\":\"USA\",\"Currency\":\"USD\",\"ISIN\":\"US03784Y2000\",\"previousClose\":14.98,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLLTD\",\"Exchange\":\"NSE\",\"Name\":\"Alembic Pharmaceuticals Limited\",\"Type\":\"Common Stock\",\"Country\":\"India\",\"Currency\":\"INR\",\"ISIN\":\"INE901L01018\",\"previousClose\":764.25,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLAPOLLO\",\"Exchange\":\"NSE\",\"Name\":\"APL Apollo Tubes Limited\",\"Type\":\"Common Stock\",\"Country\":\"India\",\"Currency\":\"INR\",\"ISIN\":\"INE702C01019\",\"previousClose\":1017.15,\"previousCloseDate\":\"2021-12-20\"},{\"Code\":\"APLT\",\"Exchange\":\"US\",\"Name\":\"Applied Therapeutics Inc\",\"Type\":\"Common Stock\",\"Country\":\"USA\",\"Currency\":\"USD\",\"ISIN\":\"US03828A1016\",\"previousClose\":9.44,\"previousCloseDate\":\"2021-12-20\"}]";
                            ///////////////////////////////////////////////////
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
                        Toast.makeText(ProfileActivity.this, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
    }

}