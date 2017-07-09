package com.zhou.widget.loading;

import android.app.Dialog;
import android.content.Context;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public interface DialogFactory<T extends Dialog> {

    T createDialog(Context context);

    void setMessage(T dialog, CharSequence message);

    int getAnimateStyleId();

}
