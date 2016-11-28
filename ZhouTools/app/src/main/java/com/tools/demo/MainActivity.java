package com.tools.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author leeshenzhou on 2016/11/25.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPasswordInputBox(View view) {
        Intent intent = new Intent(this, PasswordInputBoxActivity.class);
        startActivity(intent);
    }

    public void openRoundPicture(View view) {
        Intent intent = new Intent(this, RoundImageViewActivity.class);
        startActivity(intent);
    }

}
