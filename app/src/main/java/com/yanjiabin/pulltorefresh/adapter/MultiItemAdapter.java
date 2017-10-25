package com.yanjiabin.pulltorefresh.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yanjiabin.pulltorefresh.R;

import java.util.List;

/**
 * Created by cnsunrun on 2017-10-25.
 */

public class MultiItemAdapter extends BaseMultiItemQuickAdapter<MultipleItem,BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiItemAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.text_layout_item);
        addItemType(MultipleItem.IMG,R.layout.image_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.text_tv,item.getTxtContent());
                break;
            case MultipleItem.IMG:

                break;

        }
    }
}
