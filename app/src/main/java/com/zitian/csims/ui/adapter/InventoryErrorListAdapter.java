package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.InventoryErrorList;

public class InventoryErrorListAdapter extends ListBaseAdapter<InventoryErrorList.Bean>  implements View.OnClickListener{

    public InventoryErrorListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_error_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        InventoryErrorList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getT_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getT_prodName());

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getT_fromNo() );
        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView title4 = holder.getView(R.id.tv_toNoAndArea);
        title4.setText("搬运到：" + item.getT_toNo() );
        //title4.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView title5 = holder.getView(R.id.tv_Status);
        title5.setText("状态：" + item.getT_taskState());

        TextView piecesNum = holder.getView(R.id.tv_piecesNum);
        piecesNum.setText("件：" + item.getT_nbox_num());

        TextView packNum = holder.getView(R.id.tv_packNum);
        packNum.setText("包：" + item.getT_npack_num());

        TextView bookNum = holder.getView(R.id.tv_bookNum);
        bookNum.setText("本：" + item.getT_nbook_num());

        TextView total = holder.getView(R.id.tv_total);
        total.setText("数量：" + item.getT_count());

        title3.setOnClickListener(InventoryErrorListAdapter.this);
        title4.setOnClickListener(InventoryErrorListAdapter.this);
        title3.setTag(position);
        title4.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
        Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);
        btnGain.setOnClickListener(InventoryErrorListAdapter.this);
        btnOperation.setOnClickListener(InventoryErrorListAdapter.this);
        btnGain.setTag(position);
        btnOperation.setTag(position);

        if(item.getT_taskState().equals("未开始"))
        {
            btnGain.setVisibility(View.VISIBLE);
            btnOperation.setVisibility(View.GONE);
        }
        else if(item.getT_taskState().equals("已领取"))
        {
            btnOperation.setVisibility(View.VISIBLE);
            btnGain.setVisibility(View.GONE);
        }
        else
        {
            btnOperation.setVisibility(View.GONE);
            btnGain.setVisibility(View.GONE);
        }
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, InventoryErrorListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private InventoryErrorListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(InventoryErrorListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, InventoryErrorListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
