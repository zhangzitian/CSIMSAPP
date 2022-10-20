package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.InventoryContrastManualAddItemList;
import com.zitian.csims.model.InventoryManualAddItemList;

public class InventoryContrastManualAddItemListAdapter extends ListBaseAdapter<InventoryContrastManualAddItemList.Bean>  implements View.OnClickListener{

    public InventoryContrastManualAddItemListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_contrast_manual_add_item_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        InventoryContrastManualAddItemList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getWh_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getWh_prodName());

        TextView tv_piecesNum = holder.getView(R.id.tv_piecesNum);
        tv_piecesNum.setText("件:" + item.getWh_nbox_num());

        TextView tv_packNum = holder.getView(R.id.tv_packNum);
        tv_packNum.setText("包:" + item.getWh_npack_num());

        TextView tv_bookNum = holder.getView(R.id.tv_bookNum);
        tv_bookNum.setText("册:" + item.getWh_nbook_num());

        //TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        //title3.setText("库位/库区:" + item.getWh_wareHouseNo());

        //title3.setOnClickListener(InventoryManualAddItemListAdapter.this);
        //title3.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
        Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);
        btnGain.setOnClickListener(InventoryContrastManualAddItemListAdapter.this);
        btnOperation.setOnClickListener(InventoryContrastManualAddItemListAdapter.this);
        btnGain.setTag(position);
        btnOperation.setTag(position);
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, InventoryContrastManualAddItemListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private InventoryContrastManualAddItemListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(InventoryContrastManualAddItemListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, InventoryContrastManualAddItemListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
