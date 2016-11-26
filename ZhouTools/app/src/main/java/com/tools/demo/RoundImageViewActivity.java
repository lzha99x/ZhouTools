package com.tools.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.geek.widget.RoundImageView;

/**
 * @author leeshenzhou on 2016/11/25.
 */
public class RoundImageViewActivity extends Activity {

    private RoundImageView typeImage;

    private RoundImageView changeImage;

    private RoundImageView changeRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image);
        typeImage = (RoundImageView) findViewById(R.id.image_type);
        changeImage = (RoundImageView) findViewById(R.id.image_change);
        changeRadius = (RoundImageView) findViewById(R.id.image_radius);
    }

    public void resetType(View view) {
        typeImage.setRoundType(1);
    }

    public void changePicture(View view) {
        changeImage.setImageResource(R.drawable.change);
    }

    public void changeRadius(View view) {
        changeRadius.setRadius(20);
    }

}
