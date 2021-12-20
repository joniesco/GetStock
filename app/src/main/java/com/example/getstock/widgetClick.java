package com.example.getstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class defines what information each widget contains.
 * We have implemented callback function inside it to receive the data from the API
 * call , and display it.
 */
public class widgetClick extends AppCompatActivity implements VolleyCallback {
    TextView textView;
    String answer="null";
    StringRequest stringRequest;
    private RequestQueue queue;


    //Create all our fields
    TextView exchange;
    TextView stockprice;
    TextView pricechange;
    TextView percent;
    TextView openprice;
    TextView prevprice;
    TextView high;
    TextView low;
    TextView market;
    TextView volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_click);
        queue = Volley.newRequestQueue(this);
        Bundle b = getIntent().getExtras();
        String Symbol = b.getString("Code");

//        JsonObjectRequest str =  updateAPIdata(Symbol);

        //Create all textViews.
        exchange = (TextView)findViewById(R.id.exchange);
        stockprice = (TextView)findViewById(R.id.stock_price);
        pricechange= (TextView)findViewById(R.id.Price_Change);
        percent = (TextView)findViewById(R.id.percentage);
        openprice = (TextView)findViewById(R.id.opening_price);
        prevprice = (TextView)findViewById(R.id.previous_close);
        high = (TextView)findViewById(R.id.high);
        low = (TextView)findViewById(R.id.low);
        market = (TextView)findViewById(R.id.market_cap);
        volume = (TextView)findViewById(R.id.volume);
        //

        try {
            JSONObject jsonObject =new JSONObject(Symbol);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        textView = (TextView) findViewById(R.id.textData);
        textView.setText(Symbol);
        JsonObjectRequest jor = updateAPIdata(Symbol,this);
        queue.add(jor);

    }

    @Override
    public void onSuccess(String result) {
        try {
            JSONObject jsonObject =new JSONObject(result);
            exchange.setText( jsonObject.getString("code"));
            stockprice.setText((String)jsonObject.getString("close"));
            pricechange.setText((String)jsonObject.getString("change"));
            percent.setText((String)jsonObject.getString("change_p"));
            openprice.setText((String)jsonObject.getString("open"));
            prevprice.setText((String)jsonObject.getString("previousClose"));
            high.setText((String)jsonObject.getString("high"));
            low.setText((String)jsonObject.getString("low"));
            market.setText("Nasdaq");
            volume.setText((String)jsonObject.getString("volume"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String result) {
        Toast.makeText(widgetClick.this, "failure", Toast.LENGTH_LONG).show();
    }

    /**
     * Will send an API request to receive all data regarding the stock symbol.
     * if the API call is successful the OnSuccess method will be called.
     * @param symbol
     * @param callback
     * @return
     */
    private JsonObjectRequest updateAPIdata(String symbol, final VolleyCallback callback) {
        String my_url = "https://eodhistoricaldata.com/api/real-time/AAPL.US?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";

        return new JsonObjectRequest(Request.Method.GET, my_url, null,
                new Response.Listener<org.json.JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    } // public void onResponse(String response)
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen

                    }
                });
    }

    

        
}