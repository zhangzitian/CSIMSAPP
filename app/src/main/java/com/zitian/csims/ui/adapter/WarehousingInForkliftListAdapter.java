package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.WarehousingInForkliftList;
import com.zitian.csims.model.WarehousingInManagerTaskList;

public class WarehousingInForkliftListAdapter extends ListBaseAdapter<WarehousingInForkliftList.Bean>  implements View.OnClickListener{

    public WarehousingInForkliftListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_warehousing_in_forklift_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        WarehousingInForkliftList.Bean item = mDataList.get(position);

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getT_toNo() );
        title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        title3.setOnClickListener(WarehousingInForkliftListAdapter.this);
        title3.setTag(position);

        Button btnQuxiao = holder.getView(R.id.btnQuxiao);
        Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);

        btnQuxiao.setOnClickListener(WarehousingInForkliftListAdapter.this);
        btnGain.setOnClickListener(WarehousingInForkliftListAdapter.this);
        btnOperation.setOnClickListener(WarehousingInForkliftListAdapter.this);

        btnQuxiao.setTag(position);
        btnGain.setTag(position);
        btnOperation.setTag(position);

        if(item.getT_taskState().equals("未领取"))
        {
            btnGain.setVisibility(View.VISIBLE);
            btnQuxiao.setVisibility(View.GONE);
            btnOperation.setVisibility(View.GONE);
        }
        else if(item.getT_taskState().equals("已领取"))
        {
            btnGain.setVisibility(View.GONE);
            btnQuxiao.setVisibility(View.VISIBLE);
            btnOperation.setVisibility(View.VISIBLE);
        }
        else
        {
            btnGain.setVisibility(View.GONE);
            btnQuxiao.setVisibility(View.GONE);
            btnOperation.setVisibility(View.GONE);
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
        void onItemClick(View v, WarehousingInForkliftListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private WarehousingInForkliftListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(WarehousingInForkliftListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, WarehousingInForkliftListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
