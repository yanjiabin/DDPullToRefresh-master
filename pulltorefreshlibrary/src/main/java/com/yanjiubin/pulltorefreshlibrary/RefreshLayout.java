package com.yanjiubin.pulltorefreshlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by sszz on 2016/12/16.
 */

public class RefreshLayout extends LinearLayout {

	private LinearLayout wholeHeaderView;
	/**
	 * 头部视图的最小上边距
	 */
	private int minWholeHeaderViewPaddingTop;
	/**
	 * 头部视图的最大上边距=头部视图的高度*头部视图的超出最大范围的系数
	 */
	private int maxWholeHeaderViewPaddingTop;
	/**
	 * 头部视图的超出最大范围的系数
	 */
	private float maxWholeHeaderViewPaddingTopRadio = 0.3f;
	private ScrollView scrollView;

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(LinearLayout.VERTICAL);
		init();
	}

	private void init() {
		//1,初始化头部的视图
		initWholeHeaderView();

		//2,初始化内容视图
	}

	private SelfHeaderViewManager manager;

	/**
	 * 设置自定义头部视图的管理器,需要使用者手动调用
	 *
	 * @param manager
	 */
	public void setSelfHeaderViewManager(SelfHeaderViewManager manager) {
		this.manager = manager;
		initSelfHeaderView();
	}

	/**
	 * 初始化自定义头部视图,这个视图会加入到wholeHeaderView
	 */
	private void initSelfHeaderView() {
		View selfHeaderView = manager.getSelfHeaderView();
		int selfHeaderViewHeight = manager.getSelfHeaderViewHeight();
		minWholeHeaderViewPaddingTop = -selfHeaderViewHeight;
		maxWholeHeaderViewPaddingTop = (int) (selfHeaderViewHeight * maxWholeHeaderViewPaddingTopRadio);
		wholeHeaderView.setPadding(0, minWholeHeaderViewPaddingTop, 0, 0);
		wholeHeaderView.addView(selfHeaderView);
	}


	private void initWholeHeaderView() {
		wholeHeaderView = new LinearLayout(getContext());
		//相当于在xml文件中定义一个线性布局,布局宽高为MATCH_PARENT,WRAP_CONTENT
		wholeHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		wholeHeaderView.setBackgroundColor(Color.parseColor("#550000ff"));
		addView(wholeHeaderView);
	}

	private int downY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downY = (int) event.getY();
				return true;
			case MotionEvent.ACTION_MOVE:

				if (handleActionMove(event)) {
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (handleActionUp(event)) {
					return true;
				}
				break;
		}
		return super.onTouchEvent(event);
	}


	private boolean handleActionUp(MotionEvent event) {
		downY = 0;
		if (currentStatus == RefreshStatus.PULL_DOWN) {
			hiddenRefreshHeaderView();
			currentStatus = RefreshStatus.IDLE;
			//如果换为美团下拉刷新等,当头部回头初始状态时候,需要做一些还原操作
			handleRefreshStatusChanged();
		} else if (currentStatus == RefreshStatus.RELEASE_REFRESH) {
			beginRefreshing();
		}
		//只要将头部拉出一点点,up事件就由当前控件处理
		return wholeHeaderView.getPaddingTop() > minWholeHeaderViewPaddingTop;
	}

	private void beginRefreshing() {
		currentStatus = RefreshStatus.REFRESHING;
		changeHeaderViewPaddingTopToZero();
		handleRefreshStatusChanged();
		if (listener != null) {
			listener.onRefresh();
		}
	}

	private void changeHeaderViewPaddingTopToZero() {
		ValueAnimator va = ValueAnimator.ofInt(wholeHeaderView.getPaddingTop(), 0);
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int currentPaddingTop = (int) animation.getAnimatedValue();
				wholeHeaderView.setPadding(0, currentPaddingTop, 0, 0);
			}
		});
		va.setDuration(300);
		va.start();
	}

	/**
	 * 隐藏头部视图
	 */
	private void hiddenRefreshHeaderView() {
		ValueAnimator va = ValueAnimator.ofInt(wholeHeaderView.getPaddingTop(), minWholeHeaderViewPaddingTop);
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				//获取值动画在动画变化过程中的值
				int currentPaddingTop = (int) animation.getAnimatedValue();
				wholeHeaderView.setPadding(0, currentPaddingTop, 0, 0);
			}
		});
		va.setDuration(300);
		va.start();
	}

	private OnRefreshingListener listener;

	public void endRefreshing() {
		hiddenRefreshHeaderView();
		currentStatus = RefreshStatus.IDLE;
		handleRefreshStatusChanged();
		//做SelfHeaderView的还原处理
		manager.endRefreshing();
	}

	public interface OnRefreshingListener {
		void onRefresh();
	}

	public void setOnRefreshingListener(OnRefreshingListener listener) {
		this.listener = listener;
	}

	private float dragRadio = 1.8f;

	private boolean handleActionMove(MotionEvent event) {
		if (currentStatus == RefreshStatus.REFRESHING) {
			return false;
		}
		//由于在down的时候当前控件并没有拦截,down事件被RecyclerView消费掉了
		//当前控件直接进入move状态,由于当前控件的onTouchEvent的down事件未执行,造成downY为0
		//我们要在move的时候给downY重新赋值
		if (downY == 0) {
			downY = (int) event.getY();
		}

		int moveY = (int) event.getY();
		int dY = moveY - downY;
//		Log.i("test", "dY:" + dY + ", moveY:" + moveY + ", downY:" + downY);
		//只有向下移动才能拉出头部
		if (dY > 0) {
			//阻尼效果:就是类似弹簧的效果,随距离越来越长,拉动越来越难
			int paddingTop = (int) (dY / dragRadio + minWholeHeaderViewPaddingTop);
			if (paddingTop < 0) {
				if (currentStatus != RefreshStatus.PULL_DOWN) {
					currentStatus = RefreshStatus.PULL_DOWN;
					handleRefreshStatusChanged();
				}
//				Log.i("test", "paddingTop:" + paddingTop);
				float scale = 1-paddingTop*1.0f/minWholeHeaderViewPaddingTop;
				Log.i("test", "scale:" + scale);
				manager.handleScale(scale);
			} else if (paddingTop >= 0 && currentStatus != RefreshStatus.RELEASE_REFRESH) {
				currentStatus = RefreshStatus.RELEASE_REFRESH;
				handleRefreshStatusChanged();
			}
			//判断如果paddingtop>maxWholeHeaderViewPaddingTop,就不能再滑动了
			paddingTop = Math.min(paddingTop, maxWholeHeaderViewPaddingTop);
			//不断的设置paddingTop,来达到将头部拉出的目的
			wholeHeaderView.setPadding(0, paddingTop, 0, 0);
			return true;
		}

		return false;
	}


	/**
	 * 处理刷新状态改变
	 */
	private void handleRefreshStatusChanged() {
		//让SelfHeaderView做响应的处理
		switch (currentStatus) {
			case IDLE:
				manager.changeToIdle();
				break;
			case PULL_DOWN:
				manager.changeToPullDown();
				break;
			case RELEASE_REFRESH:
				manager.changeToReleaseRefresh();
				break;
			case REFRESHING:
				manager.changeToRefreshing();
				break;
		}
	}

	private RefreshStatus currentStatus = RefreshStatus.IDLE;

	//静止,下拉,释放刷新,刷新
	//枚举:
	//枚举不占用任何实际的值
	//枚举中的变量都是常量
	//枚举一旦定义完,只能使用枚举中的常量
	public enum RefreshStatus {
		IDLE, PULL_DOWN, RELEASE_REFRESH, REFRESHING
	}

	private float interceptDownX;
	private float interceptDownY;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				interceptDownX = ev.getX();
				interceptDownY = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float dY = ev.getY() - interceptDownY;
				//1,y方向的变化:y方向的变化量>x方向的变化量
				if (Math.abs(ev.getX() - interceptDownX) < Math.abs(dY)) {
					//2,y方向向下滑动
					if (dY > 0 && handleRefresh()) {
						return true;
					}
				}
				break;
			case MotionEvent.ACTION_UP:

				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	private RecyclerView recyclerView;

	//当前控件及其子控件全部加载完成的时候的回调
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		View contentView = getChildAt(1);
		if (contentView instanceof RecyclerView) {
			this.recyclerView = (RecyclerView) contentView;
		} else if (contentView instanceof ScrollView) {
			this.scrollView = (ScrollView) contentView;
		}
	}

	/**
	 * 判断是否可以处理刷新操作,其实就是判断滚动视图是否到达顶部
	 *
	 * @return
	 */
	private boolean handleRefresh() {
		if (RefreshScrollingUtil.isScrollViewOrWebViewToTop(scrollView)) {
			return true;
		}
		if (RefreshScrollingUtil.isRecyclerViewToTop(recyclerView)) {
			return true;
		}
		return false;
	}
}
