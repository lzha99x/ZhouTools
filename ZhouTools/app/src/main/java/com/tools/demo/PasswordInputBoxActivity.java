package com.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.geek.widget.PasswordInputBox;

/**
 * @author  Lee64 on 2016/11/28.
 */
public class PasswordInputBoxActivity extends Activity {

    PasswordInputBox box;

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_input_box);
        box = (PasswordInputBox) findViewById(R.id.password_box);
        text = (TextView) findViewById(R.id.show_text);
    }

    public void input(View v) {
        box.setText("龍");
    }

    public void showPwd(View v) {
        box.setShowPwd(true);
    }

    public void hidePwd(View v) {
        box.setShowPwd(false);
    }

    public void delete(View v) {
        box.clear();
    }

    public void deleteAll(View v) {
        box.clearAll();
    }

    public void getText(View v) {
        String str = box.getInputText();
        text.setText(str);
    }

}
