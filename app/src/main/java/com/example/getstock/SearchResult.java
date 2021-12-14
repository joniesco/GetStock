package com.example.getstock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SearchResult extends AppCompatActivity {
    private TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Bundle b = getIntent().getExtras();
        String ans = b.getString("ans");
        answer = (TextView) findViewById(R.id.answer);
        answer.setText(ans);
    }
    private void getSearchResults(String result){
        
    }
}