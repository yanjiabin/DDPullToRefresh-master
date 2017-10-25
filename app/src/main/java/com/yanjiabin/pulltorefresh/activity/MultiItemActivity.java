package com.yanjiabin.pulltorefresh.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yanjiabin.pulltorefresh.R;
import com.yanjiabin.pulltorefresh.adapter.MultiItemAdapter;
import com.yanjiabin.pulltorefresh.utils.TestData;
import com.yanjiubin.pulltorefreshlibrary.MeiTuanSelfHeaderViewManager;
import com.yanjiubin.pulltorefreshlibrary.PageLimitDelegate;
import com.yanjiubin.pulltorefreshlibrary.RefreshLayout;

public class MultiItemActivity extends AppCompatActivity implements PageLimitDelegate.DataProvider {

    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private PageLimitDelegate<String> pageLimitDelegate = new PageLimitDelegate<>(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_item);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        MultiItemAdapter multiItemAdapter = new MultiItemAdapter(TestData.createMultiData());
        mRecyclerView.setAdapter(multiItemAdapter);
        //美团的效果就是new MeiTuanSelfHeaderViewManager(this)，基本的下拉效果new NormalSelfHeaderViewManager(this)
        mRefreshLayout.setSelfHeaderViewManager(new MeiTuanSelfHeaderViewManager(this));
//        pageLimitDelegate.attach(mRefreshLayout, mRecyclerView, multiItemAdapter);
    }

    @Override
    public void loadData(int page) {

    }
}
