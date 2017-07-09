package com.zhou.widget.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;

import com.geek.utils.LogUtil;

/**
 * @author Lee64 on 2017/7/9.
 */

public class LoadingDialog implements ILoadingDialog {

    private static final String TAG = "LoadingDialog";

    private static LoadingDialog instance;

    private Dialog dialog;
    private DialogFactory factory;

    public LoadingDialog(Context context, DialogFactory factory) {
        this.factory = factory;
        this.dialog = factory.createDialog(context);
        int animateStyleId = factory.getAnimateStyleId();
        if (animateStyleId > 0) {
            dialog.getWindow().setWindowAnimations(animateStyleId);
        }
    }

    @Override
    public void show() {
        if (dialog.isShowing()) {
            LogUtil.d(TAG, "dialog is showing");
            return;
        }
        if (isAttach()) {
            dialog.show();
        }
    }

    @Override
    public void cancel() {
        if (isAttach() && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    @Override
    public Dialog create() {
        return dialog;
    }

    @Override
    public ILoadingDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    @Override
    public ILoadingDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    @Override
    public ILoadingDialog setMessage(CharSequence message) {
        factory.setMessage(dialog, message);
        return this;
    }

    public static LoadingDialog make(Context context) {
        return make(context, LoadingConfig.getDialogFactory());
    }

    public static LoadingDialog make(Context context, DialogFactory factory) {
        if (instance != null) {
            instance.cancel();
            instance = null;
        }
        instance = new LoadingDialog(context, factory);
        return instance;
    }

    private boolean isAttach() {
        if (dialog == null) {
            return false;
        }
        Context context = dialog.getContext();
        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            context = wrapper.getBaseContext();
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
