# screenshot
<a href="pic/maituan.gif" width="40%"/></a> <a href="pic/normal.gif"><img src="art/02.jpg" width="40%"/></a>
## 
Step 1. Add the JitPack repository to your build file</br>
Add it in your root build.gradle at the end of repositories:</br>
```Java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```Java
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.yanjiabin:DDPullToRefresh:1.0.0'
	}
```
基本使用
```Java
final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refresh_layout);
//美团的效果就是new MeiTuanSelfHeaderViewManager(this)，基本的下拉效果new NormalSelfHeaderViewManager(this)
refreshLayout.setSelfHeaderViewManager(new MeiTuanSelfHeaderViewManager(this));
		refreshLayout.setOnRefreshingListener(new RefreshLayout.OnRefreshingListener() {
			@Override
			public void onRefresh() {
				refreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						//获取网络数据，更新页面之后
						refreshLayout.endRefreshing();
					}
				},2000);
			}
		});
```
