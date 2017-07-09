package com.zhou.widget.loading;

import android.app.Dialog;

/**
 * @author Lee64 on 2017/7/9.
 */

public interface ILoadingDialog extends ILoading {

    Dialog create();

    ILoadingDialog setCancelable(boolean cancel);

    ILoadingDialog setCanceledOnTouchOutside(boolean cancel);

    ILoadingDialog setMessage(CharSequence message);

}
