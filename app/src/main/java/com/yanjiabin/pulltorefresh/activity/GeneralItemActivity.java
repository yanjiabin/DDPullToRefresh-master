package com.yanjiabin.pulltorefresh.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yanjiabin.pulltorefresh.R;
import com.yanjiubin.pulltorefreshlibrary.MeiTuanSelfHeaderViewManager;
import com.yanjiubin.pulltorefreshlibrary.PageLimitDelegate;
import com.yanjiubin.pulltorefreshlibrary.RefreshLayout;
import com.yanjiubin.pulltorefreshlibrary.adapter.TextAdapter;

import java.util.List;

import static com.yanjiabin.pulltorefresh.utils.TestData.createData;

public class GeneralItemActivity extends AppCompatActivity implements PageLimitDelegate.DataProvider {
    private RecyclerView mRecyclerView;
    private PageLimitDelegate<String> pageLimitDelegate = new PageLimitDelegate<>(this);
    private RefreshLayout refreshLayout;
    private TextAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_item);
        refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new TextAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //美团的效果就是new MeiTuanSelfHeaderViewManager(this)，基本的下拉效果new NormalSelfHeaderViewManager(this)
        refreshLayout.setSelfHeaderViewManager(new MeiTuanSelfHeaderViewManager(this));
        pageLimitDelegate.attach(refreshLayout, mRecyclerView, mAdapter);
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
