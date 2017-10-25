package com.yanjiabin.pulltorefresh.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by cnsunrun on 2017-10-25.
 */

public class MultipleItem implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;
    private String txtContent;
    private String imgContent;

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public String getImgContent() {
        return imgContent;
    }

    public void setImgContent(String imgContent) {
        this.imgContent = imgContent;
    }

    public MultipleItem(int itemType,String txtContent) {
        this.itemType = itemType;
        this.txtContent = txtContent;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
