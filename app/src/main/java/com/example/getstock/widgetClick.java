package com.example.getstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class widgetClick extends AppCompatActivity {
    TextView textView;
    String answer;
    StringRequest stringRequest;

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
        Bundle b = getIntent().getExtras();
        String ans = b.getString("Code");

        updateAPIdata(ans);

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
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject = new JSONObject(ans);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            exchange.setText((String)jsonObject.getString("code"));
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
        textView = (TextView) findViewById(R.id.textData);
        textView.setText(ans);

    }
    private StringRequest updateAPIdata(String stockSymbol) {
        //Create url
        String my_url = "https://eodhistoricaldata.com/api/real-time/AAPL.US?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";

        return new StringRequest(Request.Method.GET, my_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        answer = response;
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