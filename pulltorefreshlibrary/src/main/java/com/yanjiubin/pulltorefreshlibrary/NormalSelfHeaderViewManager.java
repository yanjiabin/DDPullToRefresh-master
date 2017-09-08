package com.yanjiubin.pulltorefreshlibrary;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sszz on 2016/12/16.
 */

public class NormalSelfHeaderViewManager extends SelfHeaderViewManager{
	private TextView tvStatus;
	private RotateAnimation upAnimation;
	private RotateAnimation downAnimation;
	private ImageView ivArrow;
	private ImageView ivAnimation;
	private AnimationDrawable ivAnimationDrawable;

	public NormalSelfHeaderViewManager(Context context) {
		this.context = context;
		initAnimation();
	}

	private void initAnimation() {
		upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(100);
		//动画执行完成后,不会回到原点
		upAnimation.setFillAfter(true);

		downAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(100);
		//动画执行完成后,不会回到原点
		downAnimation.setFillAfter(true);
	}

	public View getSelfHeaderView() {
		if (selfHeaderView == null) {
			selfHeaderView = View.inflate(context, R.layout.view_refresh_header_normal, null);
			tvStatus = (TextView) selfHeaderView.findViewById(R.id.tv_normal_refresh_header_status);
			ivArrow = (ImageView) selfHeaderView.findViewById(R.id.iv_normal_refresh_header_arrow);
			ivAnimation = (ImageView) selfHeaderView.findViewById(R.id.iv_normal_refresh_header_chrysanthemum);
			ivAnimationDrawable = (AnimationDrawable) ivAnimation.getDrawable();
			selfHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		}
		return selfHeaderView;
	}

	public int getSelfHeaderViewHeight() {
		//此时处于视图的加载阶段,并未对视图进行测量,想要获取测量高度,需要提前测量
		selfHeaderView.measure(0, 0);
		int selfHeaderViewHeight = selfHeaderView.getMeasuredHeight();
		return selfHeaderViewHeight;
	}

	public void changeToIdle() {
	}

	public void changeToPullDown() {
		tvStatus.setText("下拉刷新");
		ivArrow.startAnimation(downAnimation);
	}

	public void changeToReleaseRefresh() {
		tvStatus.setText("释放刷新");
		ivArrow.startAnimation(upAnimation);
	}

	public void changeToRefreshing() {
		tvStatus.setText("加载中...");
		//控件身上执行过动画,需要先清除动画,后设置隐藏才会生效
		ivArrow.clearAnimation();
		ivArrow.setVisibility(View.INVISIBLE);
		ivAnimation.setVisibility(View.VISIBLE);
		ivAnimationDrawable.start();
	}

	public void endRefreshing() {
		tvStatus.setText("下拉刷新");
		ivAnimation.setVisibility(View.INVISIBLE);
		ivArrow.setVisibility(View.VISIBLE);
		//让箭头立即旋转至向下方向
		downAnimation.setDuration(0);
		ivArrow.startAnimation(downAnimation);
	}

	@Override
	public void handleScale(float scale) {

	}
}
