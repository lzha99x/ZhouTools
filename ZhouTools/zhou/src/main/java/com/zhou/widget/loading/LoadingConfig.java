package com.zhou.widget.loading;

/**
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class LoadingConfig {

    private static DialogFactory globalDialogFactory = new MaterialDialogFactory();
    private static LoadingFactory globalLoadingFactory = new MaterialLoadingFactory();

    public static void setFactory(DialogFactory dialogFactory, LoadingFactory loadingFactory) {
        if (dialogFactory != null) {
            globalDialogFactory = dialogFactory;
        }
        if (loadingFactory != null) {
            globalLoadingFactory = loadingFactory;
        }
    }

    public static void defaultFactory() {
        setFactory(new MaterialDialogFactory(), new MaterialLoadingFactory());
    }

    public static DialogFactory getDialogFactory() {
        return globalDialogFactory;
    }

    public static LoadingFactory getLoadingFactory() {
        return globalLoadingFactory;
    }

}
