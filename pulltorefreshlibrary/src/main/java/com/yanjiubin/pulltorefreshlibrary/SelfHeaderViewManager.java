package com.yanjiubin.pulltorefreshlibrary;

import android.content.Context;
import android.view.View;

/**
 * Created by sszz on 2016/12/16.
 */

public abstract class SelfHeaderViewManager {
	protected Context context;
	protected View selfHeaderView;


	public abstract View getSelfHeaderView();

	public int getSelfHeaderViewHeight() {
		//此时处于视图的加载阶段,并未对视图进行测量,想要获取测量高度,需要提前测量
		selfHeaderView.measure(0, 0);
		int selfHeaderViewHeight = selfHeaderView.getMeasuredHeight();
		return selfHeaderViewHeight;
	}

	public abstract void changeToIdle();

	public abstract void changeToPullDown();

	public abstract void changeToReleaseRefresh();

	public abstract void changeToRefreshing();

	public abstract void endRefreshing();


	public abstract void handleScale(float scale);
}
