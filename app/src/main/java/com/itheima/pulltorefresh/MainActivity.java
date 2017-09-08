package com.itheima.pulltorefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.itheima.pulltorefresh.adapter.TextAdapter;
import com.yanjiubin.pulltorefreshlibrary.NormalSelfHeaderViewManager;
import com.yanjiubin.pulltorefreshlibrary.PageLimitDelegate;
import com.yanjiubin.pulltorefreshlibrary.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PageLimitDelegate.DataProvider {

    private RecyclerView mRecyclerView;
    private TextAdapter mAdapter;

    private PageLimitDelegate<String> pageLimitDelegate = new PageLimitDelegate<>(this);
    private RefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new TextAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //美团的效果就是new MeiTuanSelfHeaderViewManager(this)，基本的下拉效果new NormalSelfHeaderViewManager(this)
        refreshLayout.setSelfHeaderViewManager(new NormalSelfHeaderViewManager(this));

        pageLimitDelegate.attach(refreshLayout, mRecyclerView, mAdapter);

        //这里只是为了做测试就设置监听时间是两秒,如果是网络请求的话就不需要写这个 因为已经封装了下拉刷新
        refreshLayout.setOnRefreshingListener(new RefreshLayout.OnRefreshingListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //获取网络数据，更新页面之后
                        refreshLayout.endRefreshing();
                    }
                }, 2000);
            }
        });
    }

    private List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("测试" + i);
        }
        return data;
    }
    @Override
    public void loadData(int page) {
        Log.e("page", "-----------" + page);
        List<String> data = createData();
        if (page < 5) {
            pageLimitDelegate.setData(data);
        } else {
            pageLimitDelegate.setData(null);
        }
    }
}
