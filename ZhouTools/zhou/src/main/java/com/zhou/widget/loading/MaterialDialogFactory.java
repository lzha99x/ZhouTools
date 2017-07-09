package com.zhou.widget.loading;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;

import com.geek.utils.R;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class MaterialDialogFactory implements DialogFactory<ProgressDialog> {

    @Override
    public ProgressDialog createDialog(Context context) {
        ProgressDialog dialog = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog = new ProgressDialog(context, R.style.Dialog_Loading_Material);
        } else {
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar);
        }
        dialog.setMessage("正在提交");
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void setMessage(ProgressDialog dialog, CharSequence message) {
        dialog.setMessage(message);
    }

    @Override
    public int getAnimateStyleId() {
        return 0;
    }

}
