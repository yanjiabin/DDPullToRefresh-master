package com.yanjiabin.pulltorefresh.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanjiabin.pulltorefresh.R;

/**
 * Created by cnsunrun on 2017-09-08.
 */

public class GridviewAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public GridviewAdapter() {
        super(R.layout.gridview_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);
    }
}
