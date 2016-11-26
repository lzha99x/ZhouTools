package com.geek.tools;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author leeshenzhou on 2016/11/26.
 */
public class DpiUtils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

}
