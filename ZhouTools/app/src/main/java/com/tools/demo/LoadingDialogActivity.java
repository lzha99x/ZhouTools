package com.tools.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.widget.loading.DialogFactory;
import com.zhou.widget.loading.LoadingDialog;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class LoadingDialogActivity extends Activity {

    private EditText etMessage;
    private CheckBox cbCancelable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_dialog);
        etMessage = (EditText) this.findViewById(R.id.et_message);
        cbCancelable = (CheckBox) this.findViewById(R.id.cb_cancelable);
    }


    public void clickDefault(View v) {
        final LoadingDialog dialog = LoadingDialog.make(this);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        }, 2000);
    }

    public void clickCustom(View v) {
        final Dialog dialog = LoadingDialog.make(this, new CustomDialogFactory())
                .setMessage(etMessage.getText())
                .setCancelable(cbCancelable.isChecked())
                .create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(LoadingDialogActivity.this, "Dialog取消了", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        }, 2000);
    }

    private class CustomDialogFactory implements DialogFactory {

        @Override
        public Dialog createDialog(Context context) {
            Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
            dialog.setContentView(R.layout.layout_custom);
            return dialog;
        }

        @Override
        public void setMessage(Dialog dialog, CharSequence message) {
            TextView tv = (TextView) dialog.findViewById(R.id.tv_message);
            if (tv != null) {
                tv.setText(message);
            }
        }

        @Override
        public int getAnimateStyleId() {
            return R.style.Dialog_Alpha_Animation;
        }

    }


}


