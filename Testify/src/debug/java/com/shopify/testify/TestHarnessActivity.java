package com.shopify.testify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TestHarnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_test);
    }
}
