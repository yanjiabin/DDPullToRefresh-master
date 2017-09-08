package com.yanjiubin.pulltorefreshlibrary;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sszz on 2016/12/17.
 */
//分为三个阶段
//1,缩放阶段:PULL_DOWN
//2,小人跳出来的阶段:RELEASE_REFRESH
//3,小人头部左右摇摆阶段:REFRESHING
public class MeiTuanSelfHeaderViewManager extends SelfHeaderViewManager {

	private ImageView ivPullDown;
	private ImageView ivReleaseRefreshing;
	private AnimationDrawable releaseAnimationDrawable;
	private AnimationDrawable refreshingAnimationDrawable;

	public MeiTuanSelfHeaderViewManager(Context context) {
		this.context = context;
	}

	@Override
	public View getSelfHeaderView() {
		if (selfHeaderView == null) {
			selfHeaderView = View.inflate(context, R.layout.view_refresh_header_meituan, null);
			ivPullDown = (ImageView) selfHeaderView.findViewById(R.id.iv_meituan_pull_down);
			selfHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			ivReleaseRefreshing = (ImageView) selfHeaderView.findViewById(R.id.iv_meituan_release_refreshing);
		}
		return selfHeaderView;
	}

	@Override
	public void changeToIdle() {
		//做收尾处理工作
		ivPullDown.setVisibility(View.VISIBLE);
		ivReleaseRefreshing.setVisibility(View.INVISIBLE);
		if(releaseAnimationDrawable!=null){
			releaseAnimationDrawable.stop();
		}
		if(refreshingAnimationDrawable!=null){
			refreshingAnimationDrawable.stop();
		}
	}

	@Override
	public void changeToPullDown() {
		ivPullDown.setVisibility(View.VISIBLE);
		ivReleaseRefreshing.setVisibility(View.INVISIBLE);
	}

	@Override
	public void changeToReleaseRefresh() {
		ivPullDown.setVisibility(View.INVISIBLE);
		ivReleaseRefreshing.setVisibility(View.VISIBLE);
		ivReleaseRefreshing.setImageResource(R.drawable.release_refresh);
		releaseAnimationDrawable = (AnimationDrawable) ivReleaseRefreshing.getDrawable();
		releaseAnimationDrawable.start();
	}

	@Override
	public void changeToRefreshing() {
		ivReleaseRefreshing.setImageResource(R.drawable.refresh_mt_refreshing);
		refreshingAnimationDrawable = (AnimationDrawable) ivReleaseRefreshing.getDrawable();
		refreshingAnimationDrawable.start();
	}

	@Override
	public void endRefreshing() {
		ivReleaseRefreshing.setVisibility(View.INVISIBLE);
		ivPullDown.setVisibility(View.VISIBLE);

	}

	public void handleScale(float scale){
		ivPullDown.setScaleX(scale);
		ivPullDown.setScaleY(scale);
	}
}
