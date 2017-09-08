package com.yanjiubin.pulltorefreshlibrary;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.yanjiubin.pulltorefreshlibrary.JudgeUtil.empty;
import static com.yanjiubin.pulltorefreshlibrary.JudgeUtil.size;

/**
 * 分页代理类
 * Created by WQ on 2017/5/26.
 */

public class PageLimitDelegate<T> {
    public int page = 1;//当前页
    DataProvider provider;//数据提供者,主要实现loadData 方法即可
    BaseQuickAdapter<T,?> quickAdapter;//适配器
    RefreshLayout refreshLayout;//下拉刷新控件
    AtomicBoolean isLoadMore=new AtomicBoolean(false);//是否处于加载状态
    int maxPageSize=10;//每页最大值
    public PageLimitDelegate(DataProvider provider) {
        this.provider
                = provider;
    }
    boolean isAttach=false;
    /**
     * 附加列表信息,设置加载刷新信息
     * @param refreshLayout
     * @param recyclerView
     * @param quickAdapter
     */
    public void attach(final RefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T,?> quickAdapter) {
        this.quickAdapter=quickAdapter;
        this.refreshLayout=refreshLayout;
        refreshLayout.setEnabled(true);
        refreshLayout.setOnRefreshingListener(new RefreshLayout.OnRefreshingListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //获取网络数据，更新页面之后
                        refreshPage();
                    }
                }, 2000);

            }
        });
        quickAdapter.setEnableLoadMore(true);
        quickAdapter.disableLoadMoreIfNotFullPage(recyclerView);
        quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadMore.set(true);
                page++;
                provider.loadData(page);
            }
        }, recyclerView);
        provider.loadData(page);
        isAttach=true;
    }

    /**
     * 刷新
     */
    public void refreshPage() {
        page = 1;
        isLoadMore.set(false);
        provider.loadData(page);
    }

    /**
     * 设置数据,
     * @param data
     */
    public  void setData(List<T> data){
        maxPageSize=Math.max(maxPageSize,size(data));
        if(isLoadMore.get() ||page!=1) {
            if(empty(data)){
                page--;
                quickAdapter.setEnableLoadMore(false);
                quickAdapter.loadMoreEnd();
            }else {
                quickAdapter.addData(data);
            }
            loadComplete();
        }else {
            loadComplete();
            quickAdapter.setNewData(data);
            quickAdapter.setEnableLoadMore(size(data)>=maxPageSize);
        }
    }




    /**
     * 加载/刷新完成
     */
    public void loadComplete(){
        isLoadMore.set(false);
        refreshLayout.endRefreshing();
        quickAdapter.loadMoreComplete();

    }

    /**
     * 数据提供者接口,用于进行加载动作
     */
    public interface DataProvider {
        public void loadData(int page);
    }


    public static void addData2List(List src, List data) {
        if(data != null) {
            Iterator var3 = data.iterator();

            while(true) {
                while(var3.hasNext()) {
                    Object object = var3.next();
                    int index = src.indexOf(object);
                    if(index != -1) {
                        src.set(index, object);
                    } else {
                        src.add(object);
                    }
                }

                return;
            }
        }
    }

    public boolean isAttach() {
        return isAttach;
    }
}
