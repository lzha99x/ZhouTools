package com.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SpringBackScrollViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview_springback);
	}

	public void showToast(View view) {
		Toast.makeText(getApplicationContext(), "hello world",
				Toast.LENGTH_SHORT).show();
	}

}
