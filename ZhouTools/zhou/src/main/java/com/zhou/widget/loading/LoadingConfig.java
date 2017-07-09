package com.zhou.widget.loading;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class LoadingConfig {

    private static DialogFactory globalDialogFactory = new MaterialDialogFactory();

    public static void setFactory(DialogFactory dialogFactory) {
        if (dialogFactory != null) {
            globalDialogFactory = dialogFactory;
        }
    }

    public static void defaultFactory() {
        setFactory(new MaterialDialogFactory());
    }

    public static DialogFactory getDialogFactory() {
        return globalDialogFactory;
    }

}
