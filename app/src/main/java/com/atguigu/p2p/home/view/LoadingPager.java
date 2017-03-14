package com.atguigu.p2p.home.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.atguigu.p2p.R;
import com.atguigu.p2p.utils.UiUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/13 0013.
 * 功能:
 */
public abstract class LoadingPager extends FrameLayout {
    private final Context mContext;


    private int STATE_LOADING = 1; //加载中
    private int STATE_ERROR = 2; //加载失败
    private int STATE_SUCCESS = 3; //加载成功
    private int STATE_EMPTY = 4; //空

    /**
     * 当前状态,一开始是加载中
     */
    private int current_state = STATE_LOADING;
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View sucessView;
    private LayoutParams params;

    private ResultState mResultState;

    public LoadingPager(Context context) {
        super(context);
        this.mContext = context;
        init();
    }


    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }


    /**
     * 添加三个布局
     */
    private void init() {
        //设置全屏属性
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //加载布局
        if (loadingView == null) {
            loadingView = View.inflate(mContext, R.layout.page_loading, null);
            this.addView(loadingView, params);
        }
        //加载布局
        if (errorView == null) {
            errorView = View.inflate(mContext, R.layout.page_error, null);
            this.addView(errorView, params);
        }
        //加载布局
        if (emptyView == null) {
            emptyView = View.inflate(mContext, R.layout.page_empty, null);
            this.addView(emptyView, params);
        }

        //显示布局
        showSafeView();

    }

    private void showSafeView() {
        //展示view保证要在主线程
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showView();
            }
        });
    }

    /**
     * 根据状态来展示不同的页面
     */
    private void showView() {
        //错误页面逻辑
        errorView.setVisibility(current_state == STATE_ERROR ?
                View.VISIBLE : View.GONE);
        //加载页面逻辑
        loadingView.setVisibility(current_state == STATE_LOADING ?
                View.VISIBLE : View.GONE);
        //空页面逻辑
        emptyView.setVisibility(current_state == STATE_EMPTY ?
                View.VISIBLE : View.GONE);

        if (sucessView == null) {
            sucessView = View.inflate(mContext, getViewId(), null);
            this.addView(sucessView, params);
        }

        //是否展示成功页面
        //成功页面逻辑
        sucessView.setVisibility(current_state == STATE_SUCCESS ?
                View.VISIBLE : View.GONE);
    }

    /**
     * 根据不同的网络状态加载相应的页面
     */
    public void loadData() {
        //加载网络
        AsyncHttpClient client = new AsyncHttpClient();
        String url = getUrl();
        if (TextUtils.isEmpty(url)) {
            //如果是空的默认为不加载网络
            mResultState = ResultState.SUCCESS;//改变状态
            loadImage();
            return;
        }
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (!TextUtils.isEmpty(content)) {
                    mResultState = mResultState.SUCCESS;
                    mResultState.setJson(content);
                } else {
                    mResultState = mResultState.EMPTY;
                    mResultState.setJson("");
                }
                loadImage();
            }

            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                mResultState = mResultState.ERROR;
                mResultState.setJson(content);
                loadImage();
            }
        });
    }

    /**
     * 根据枚举不同的值, 来设置不同的状态
     */
    protected void loadImage() {
        switch (mResultState) {
            case ERROR:
                current_state = STATE_ERROR; //根据枚举值来赋值相应的状态
                break;
            case EMPTY:
                current_state = STATE_EMPTY; //根据枚举值来赋值相应的状态
                break;
            case SUCCESS:
                current_state = STATE_SUCCESS; //根据枚举值来赋值相应的状态
                break;
        }
        showSafeView();
        if (current_state == STATE_SUCCESS) {
            //把数据传过去
            onSuccess(mResultState, sucessView);
        }
    }

    protected abstract void onSuccess(ResultState resultState, View sucessView);

    public enum ResultState {

        ERROR(2), SUCCESS(3), EMPTY(4);
        private int state;

        ResultState(int state) {
            this.state = state;
        }

        private String json;

        public void setJson(String json) {
            this.json = json;
        }

        public String getJson() {
            return json;
        }
    }

    protected abstract String getUrl();

    public abstract int getViewId();


}
