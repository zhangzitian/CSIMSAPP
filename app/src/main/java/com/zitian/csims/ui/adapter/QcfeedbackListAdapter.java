package com.zitian.csims.ui.adapter;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.QcfeedbackList;

public class QcfeedbackListAdapter extends ListBaseAdapter<QcfeedbackList.Bean> implements View.OnClickListener{
    public QcfeedbackListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qc_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        QcfeedbackList.Bean item = mDataList.get(position);

        TextView tv_factoryid = holder.getView(R.id.tv_factoryid);
        tv_factoryid.setText("加工厂名称：" + item.getfactory_id());

        TextView title2 = holder.getView(R.id.tv_prodNo);
        title2.setText("产品编码：" + item.getproduct_no());

        TextView title1 = holder.getView(R.id.tv_review);
        title1.setText("生产主管处理意见：" + item.getqc_review());

        TextView title3 = holder.getView(R.id.tv_programme);
        title3.setText("入库方案：" + item.getqc_programme());

        Button btnDetail = holder.getView(R.id.ItemDetail);
        btnDetail.setOnClickListener(QcfeedbackListAdapter.this);
        btnDetail.setTag(position);
    }
    //=======================以下为item中的button控件点击事件处理===================================


    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, QcfeedbackListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private QcfeedbackListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(QcfeedbackListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, QcfeedbackListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }
}
