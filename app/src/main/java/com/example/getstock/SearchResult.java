package com.example.getstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private TextView answer;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = (RecyclerView)findViewById(R.id.results);

        Bundle b = getIntent().getExtras();
        String ans = b.getString("ans");

        String [] search_res = new String[0];

        try {
            search_res = getSearchResults(ans);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new CustomAdapter(this,search_res);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //answer = (TextView) findViewById(R.id.answer);
        //answer.setText(ans);
    }
    private String[] getSearchResults(String result) throws JSONException {
        JSONArray jsonArray = new JSONArray(result);
        String [] answer = new String[jsonArray.length()];

        for(int i=0; i<jsonArray.length();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            String temp = (String) object.get("Code");
            answer[i] = temp;
        }
        return answer;
    }
}