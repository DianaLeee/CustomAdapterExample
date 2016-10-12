package com.example.leedayeon.final_report_02_20141095;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by LeeDaYeon on 2016-06-25.
 */
public class MyselfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                finish();
                break;
        }
    }
}
