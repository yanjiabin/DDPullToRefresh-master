package com.yanjiabin.pulltorefresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yanjiabin.pulltorefresh.activity.GeneralItemActivity;
import com.yanjiabin.pulltorefresh.activity.GridViewItemActivity;
import com.yanjiabin.pulltorefresh.activity.ListViewWithBannerActivity;
import com.yanjiabin.pulltorefresh.activity.ListViewWithFootViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private String[] activityName = new String[] {"普通listview条目","gridview条目","带有头部listview条目","带有脚的listview条目","多类型条目"};

    private Class[] classList = new Class[]{GeneralItemActivity.class,
            GridViewItemActivity.class,
            ListViewWithBannerActivity.class,
            ListViewWithFootViewActivity.class,
            ListViewWithFootViewActivity.class
    };
    private ListView listView;
    private List<String> stringList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        for (String s : activityName) {
            stringList.add(s);
        }
        ///第二种方法
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);
        //最后一个参数是List或String[]均可
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,classList[position]));

            }
        });
    }
}
