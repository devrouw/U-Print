package com.lollipop.uprint.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.lollipop.uprint.Config;
import com.lollipop.uprint.R;

public class MainActivity extends AppCompatActivity {

    Config session;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        session = new Config(getApplicationContext());

        session.checkLogin();
    }
}
