package com.itheima.pulltorefresh.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itheima.pulltorefresh.R;

/**
 * Created by cnsunrun on 2017-09-07.
 */

public class TextAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public TextAdapter() {
        super(R.layout.text_layout_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text_tv,item);
    }
}
