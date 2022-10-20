package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.OutofSpaceManagerList;

public class OutofSpaceManagerListAdapter extends ListBaseAdapter<OutofSpaceManagerList.Bean> implements View.OnClickListener {

    public OutofSpaceManagerListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_outof_space_manager_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        OutofSpaceManagerList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getT_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getT_prodName());

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getT_fromNo()  );

        TextView title4 = holder.getView(R.id.tv_toNoAndArea);
        title4.setText("搬运到：" + item.getT_toNo() );

        TextView title5 = holder.getView(R.id.tv_Status);
        title5.setText("状态：" + item.getT_taskState());

        TextView title6 = holder.getView(R.id.tv_receiver);
        title6.setText("领取人：" + item.getReceiverName() );

    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, OutofSpaceForkliftListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }

    private OutofSpaceForkliftListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OutofSpaceForkliftListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, OutofSpaceForkliftListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }


}
