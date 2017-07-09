package com.zhou.widget.loading;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.geek.utils.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 加载进度，可用于FrameLayout、RelativeLayout、DrawerLayout、CoordinatorLayout、CardView等
 *
 * @author zhou
 *         Created by Lee64 on 2017/7/9.
 */

public class LoadingBar implements ILoading {

    private static final String TAG = "LoadingBar";

    private static final Map<View, LoadingBar> loadingBarMaps = new HashMap<>();
    private static int LOADING_LIMIT = 15; // 默认15,超过会检查资源释放

    private ViewGroup mParent;
    private View mView;
    private LoadingFactory mFactory;
    private OnLoadingBarListener mListener;

    public LoadingBar(ViewGroup parent, LoadingFactory factory) {
        this.mParent = parent;
        this.mFactory = factory;
        this.mView = factory.createView(parent);
    }

    @Override
    public void show() {
        if (mView == null) return;
        mView.setVisibility(View.VISIBLE);
        if (mView.getParent() != null) {
            mParent.removeView(mView);
        }
        mParent.addView(mView);
    }

    @Override
    public void cancel() {
        if (mView == null) return;
        mView.setVisibility(View.GONE);
        mParent.removeView(mView);
        mView = null;
        if (mListener != null) {
            mListener.onCancel(mParent);
        }
    }

    public LoadingBar setOnClickListener(View.OnClickListener listener) {
        if (mView != null) {
            mView.setOnClickListener(listener);
        }
        return this;
    }

    public LoadingBar setOnLoadingBarListener(OnLoadingBarListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public View getView() {
        return mView;
    }

    public LoadingFactory getLoadingFactory() {
        return mFactory;
    }

    public static LoadingBar make(View parent) {
        return make(parent, LoadingConfig.getLoadingFactory());
    }

    public static LoadingBar make(View parent, LoadingFactory factory) {
        // 如果已经有Loading在显示了
        if (loadingBarMaps.containsKey(parent)) {
            LoadingBar loadingBar = loadingBarMaps.get(parent);
            loadingBar.mParent.removeView(loadingBar.mView);
        }
        LoadingBar newLoadingBar = new LoadingBar(findSuitableParent(parent), factory);
        loadingBarMaps.put(parent, newLoadingBar);
        return newLoadingBar;
    }

    public static LoadingBar make(View parent, final int loadingResId) {
        return make(parent, new LoadingFactory() {
            @Override
            public View createView(ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(loadingResId, parent, false);
            }
        });
    }

    /**
     * 根据父节点取消单个loading
     */
    public static void cancel(View parent) {
        LoadingBar loadingBar = loadingBarMaps.get(parent);
        if (loadingBar != null) {
            loadingBar.cancel();
        }
        loadingBarMaps.remove(parent);
    }

    /**
     * 取消所有loading
     */
    public static void cancelAll() {
        Iterator<Map.Entry<View, LoadingBar>> it = loadingBarMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<View, LoadingBar> entry = it.next();
            cancel(entry.getKey());
        }
    }

    public static void release() {
        release(LOADING_LIMIT);
    }

    /**
     * 释放无用的资源
     *
     * @param limit loading池的限制,超过数量才检查资源释放
     */
    public static void release(int limit) {
        if (limit <= 0) {
            limit = LOADING_LIMIT;
        }
        if (loadingBarMaps.size() < limit) {
            return;
        }
        LogUtil.d(TAG, "release before loading size - " + loadingBarMaps.size());
        Iterator<Map.Entry<View, LoadingBar>> it = loadingBarMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<View, LoadingBar> entry = it.next();
            Context context = entry.getKey().getContext();
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (activity.isFinishing()) {
                    it.remove();
                }
            }
        }
        LogUtil.d(TAG, "release after loading size - " + loadingBarMaps.size());
    }

    /**
     * 找到合适的父布局
     */
    private static ViewGroup findSuitableParent(View parent) {
        if (parent == null) return null;
        View suitableParent = parent;
        do {
            if (suitableParent instanceof FrameLayout || suitableParent instanceof RelativeLayout ||
                    "android.support.v4.widget.DrawerLayout".equals(suitableParent.getClass().getName()) ||
                    "android.support.design.widget.CoordinatorLayout".equals(suitableParent.getClass().getName()) ||
                    "android.support.v7.widget.CardView".equals(suitableParent.getClass().getName())) {
                return (ViewGroup) suitableParent;
            } else {
                final ViewParent viewParent = suitableParent.getParent();
                suitableParent = viewParent instanceof View ? (View) viewParent : null;
                return (ViewGroup) suitableParent;
            }
        } while (suitableParent != null);
    }


}
