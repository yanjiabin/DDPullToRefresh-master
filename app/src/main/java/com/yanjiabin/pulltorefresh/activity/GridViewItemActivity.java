package com.yanjiabin.pulltorefresh.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yanjiabin.pulltorefresh.R;
import com.yanjiabin.pulltorefresh.adapter.GridviewAdapter;
import com.yanjiabin.pulltorefresh.utils.TestData;
import com.yanjiubin.pulltorefreshlibrary.MeiTuanSelfHeaderViewManager;
import com.yanjiubin.pulltorefreshlibrary.PageLimitDelegate;
import com.yanjiubin.pulltorefreshlibrary.RefreshLayout;

public class GridViewItemActivity extends AppCompatActivity implements PageLimitDelegate.DataProvider {

    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecyclewview;

    private PageLimitDelegate<String> pageLimitDelegate = new PageLimitDelegate<>(this);
    private GridviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_item);
        initView();
    }


    private void initView() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclewview = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclewview.setLayoutManager(new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false));
        mAdapter = new GridviewAdapter();
        mRecyclewview.setAdapter(mAdapter);
        //美团的效果就是new MeiTuanSelfHeaderViewManager(this)，基本的下拉效果new NormalSelfHeaderViewManager(this)
        mRefreshLayout.setSelfHeaderViewManager(new MeiTuanSelfHeaderViewManager(this));
        pageLimitDelegate.attach(mRefreshLayout, mRecyclewview, mAdapter);
    }

    @Override
    public void loadData(int page) {
        if (page < 5) {
            pageLimitDelegate.setData(TestData.createData());
        } else {
            pageLimitDelegate.setData(null);
        }
    }
}
