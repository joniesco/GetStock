package com.example.getstock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class widgetClick extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_click);
        Bundle b = getIntent().getExtras();
        String ans = b.getString("Code");

        textView = (TextView) findViewById(R.id.textData);
        textView.setText(ans);

    }
}