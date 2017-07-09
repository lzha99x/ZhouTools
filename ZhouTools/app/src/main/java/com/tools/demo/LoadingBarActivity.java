package com.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhou.widget.loading.LoadingBar;
import com.zhou.widget.loading.LoadingFactory;
import com.zhou.widget.loading.OnLoadingBarListener;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class LoadingBarActivity extends Activity {

    private View parent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_bar);
        parent = findViewById(R.id.view_loading_container);
    }

    public void clickLoading(View v) {
        LoadingBar.make(parent).show();
    }

    public void clickCustomLoading(View v) {
        LoadingBar.make(parent, new CustomLoadingFactory())
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(LoadingBarActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                    }

                })
                .setOnLoadingBarListener(new OnLoadingBarListener() {

                    @Override
                    public void onCancel(View parent) {
                        Toast.makeText(LoadingBarActivity.this, "Loading取消了", Toast.LENGTH_SHORT).show();
                    }

                }).show();
    }


    public void clickCancelLoading(View v) {
        LoadingBar.cancel(parent);
    }

    private class CustomLoadingFactory implements LoadingFactory {

        @Override
        public View createView(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom, parent, false);
            return view;
        }

    }


}
