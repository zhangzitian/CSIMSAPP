package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.InventoryContrastList;

public class InventoryContrastListAdapter extends ListBaseAdapter<InventoryContrastList.Bean>  implements View.OnClickListener{

    public InventoryContrastListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_contrast_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        InventoryContrastList.Bean item = mDataList.get(position);

        TextView tv_Status = holder.getView(R.id.tv_Status);
        if(item.getR_userid().equals(""))
        {
            tv_Status.setText("未盘");
        }else
        {
            tv_Status.setText("已盘");
        }

        TextView tv_fromNoAndArea = holder.getView(R.id.tv_fromNoAndArea);
        tv_fromNoAndArea.setText("库位号：" + item.getWh_wareHouseNo());

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getWh_prodNo());

        //Button btnGain  = holder.getView(R.id.btnGain);
        //Button btnProduct = holder.getView(R.id.btnProduct);
        Button btnOperation = holder.getView(R.id.btnOperation);

        //btnGain.setOnClickListener(InventoryContrastListAdapter.this);
        //btnGain.setTag(position);

        //btnProduct.setOnClickListener(InventoryContrastListAdapter.this);
        //btnProduct.setTag(position);

        btnOperation.setOnClickListener(InventoryContrastListAdapter.this);
        btnOperation.setTag(position);



//        if(item.getR_error5().contains("Table_InventoryManualWhole"))
//        {
//            btnGain.setVisibility(View.GONE);
//            btnProduct.setVisibility(View.GONE);
//            btnOperation.setVisibility(View.VISIBLE);
//        }else if(item.getR_error5().contains("Table_InventoryManualScattered"))
//        {
//            btnGain.setVisibility(View.GONE);
//            btnProduct.setVisibility(View.GONE);
//            btnOperation.setVisibility(View.VISIBLE);
//        }else if(item.getR_error5().contains("Table_InventoryEmpty"))
//        {
//            btnGain.setVisibility(View.VISIBLE);
//            if(item.getR_status()==2 && item.getWh_prodNo().trim().equals(""))
//            {
//                btnProduct.setVisibility(View.VISIBLE);
//            }else
//                btnProduct.setVisibility(View.GONE);
//
//            btnOperation.setVisibility(View.GONE);
//        }
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, InventoryContrastListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private InventoryContrastListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(InventoryContrastListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, InventoryContrastListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
