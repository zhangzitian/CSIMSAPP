package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.InventoryInputDisperseError;
import com.zitian.csims.model.InventoryManualList;

public class InventoryInputDisperseErrorAdapter extends ListBaseAdapter<InventoryInputDisperseError.Bean>  implements View.OnClickListener{

    public InventoryInputDisperseErrorAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_input_disperse_error_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        InventoryInputDisperseError.Bean item = mDataList.get(position);

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

        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        //TextView title5 = holder.getView(R.id.tv_Status);
        //title5.setText("状态：" + item.getWh_status() == "0" ? "空":"有货");

        //title3.setOnClickListener(InventoryInputDisperseErrorAdapter.this);
        //title3.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
        Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);
        btnGain.setOnClickListener(InventoryInputDisperseErrorAdapter.this);
        btnOperation.setOnClickListener(InventoryInputDisperseErrorAdapter.this);
        btnGain.setTag(position);
        btnOperation.setTag(position);

//        if(item.getT_taskState().equals("未开始"))
//        {
//            btnGain.setVisibility(View.VISIBLE);
//            btnOperation.setVisibility(View.GONE);
//        }
//        else if(item.getT_taskState().equals("已领取"))
//        {
//            btnOperation.setVisibility(View.VISIBLE);
//            btnGain.setVisibility(View.GONE);
//        }
//        else
//        {
//            btnOperation.setVisibility(View.GONE);
//            btnGain.setVisibility(View.GONE);
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
        void onItemClick(View v, InventoryInputDisperseErrorAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private InventoryInputDisperseErrorAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(InventoryInputDisperseErrorAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, InventoryInputDisperseErrorAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
