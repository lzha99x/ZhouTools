package com.zhou.widget.loading;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geek.utils.R;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class MaterialLoadingFactory implements LoadingFactory {

    @Override
    public View createView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_bar_material, parent, false);
        return view;
    }

}
