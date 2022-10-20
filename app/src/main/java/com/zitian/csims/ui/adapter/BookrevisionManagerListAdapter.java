package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.BookrevisionManagerList;

public class BookrevisionManagerListAdapter extends ListBaseAdapter<BookrevisionManagerList.Bean>  implements View.OnClickListener{

    public BookrevisionManagerListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bookrevision_manager_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BookrevisionManagerList.Bean item = mDataList.get(position);

        TextView tv_Receiver = holder.getView(R.id.tv_Receiver);
        tv_Receiver.setText("领取人：" + item.getReceiverName() );

        //TextView title2 = holder.getView(R.id.tv_prodName);
        //title2.setText("产品名称：" + item.getT_prodName());

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getT_fromNo() );
        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView title4 = holder.getView(R.id.tv_toNoAndArea);
        title4.setText("搬运到：" + item.getT_toNo() );
        //title4.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView title5 = holder.getView(R.id.tv_Status);
        title5.setText("状态：" + item.getT_taskState());

        title3.setOnClickListener(BookrevisionManagerListAdapter.this);
        title4.setOnClickListener(BookrevisionManagerListAdapter.this);
        title3.setTag(position);
        title4.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
        Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);
        btnGain.setOnClickListener(BookrevisionManagerListAdapter.this);
        btnOperation.setOnClickListener(BookrevisionManagerListAdapter.this);
        btnGain.setTag(position);
        btnOperation.setTag(position);

        if(item.getT_taskState().equals("已领取"))
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
        void onItemClick(View v, BookrevisionManagerListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private BookrevisionManagerListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(BookrevisionManagerListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, BookrevisionManagerListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
