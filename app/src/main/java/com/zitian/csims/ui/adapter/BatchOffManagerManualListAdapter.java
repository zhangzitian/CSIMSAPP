package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.BatchOffManagerManualList;

public class BatchOffManagerManualListAdapter extends ListBaseAdapter<BatchOffManagerManualList.Bean>  implements View.OnClickListener{

    public BatchOffManagerManualListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_batch_off_manager_manual_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BatchOffManagerManualList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getB_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getB_prodName());

        TextView title3 = holder.getView(R.id.tv_createTime);
        title3.setText("创建时间：" + item.getB_createTime() );

        TextView title4 = holder.getView(R.id.tv_receiver);
        title4.setText("领取人：" + item.getReceiverName() );
        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        //TextView title4 = holder.getView(R.id.tv_toNoAndArea);
        //title4.setText("搬运到：" + item.getT_toNo() );
        //title4.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView title5 = holder.getView(R.id.tv_Status);
        title5.setText("状态：" + item.getB_state());

        TextView title6 = holder.getView(R.id.tv_piecesNum);
        title6.setText("件：" + item.getB_piecesNum());

        TextView title7 = holder.getView(R.id.tv_packNum);
        title7.setText("包：" + item.getB_packNum());

        TextView title8 = holder.getView(R.id.tv_bookNum);
        title8.setText("册：" + item.getB_bookNum());

        TextView title9 = holder.getView(R.id.tv_total);
        title9.setText("总数：" + item.getB_total());

        //title3.setOnClickListener(BatchOffManagerManualListAdapter.this);
        //title4.setOnClickListener(BatchOffManagerManualListAdapter.this);
        //title3.setTag(position);
        //title4.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
        Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);
        btnGain.setOnClickListener(BatchOffManagerManualListAdapter.this);
        btnOperation.setOnClickListener(BatchOffManagerManualListAdapter.this);
        btnGain.setTag(position);
        btnOperation.setTag(position);

        if(item.getB_state().equals("已完成") || item.getB_state().equals("已取消"))
        {
            btnOperation.setVisibility(View.GONE);
        //    btnGain.setVisibility(View.GONE);
        }
        else
        {
            btnOperation.setVisibility(View.VISIBLE);
        //    btnGain.setVisibility(View.GONE);
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
        void onItemClick(View v, BatchOffManagerManualListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private BatchOffManagerManualListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(BatchOffManagerManualListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, BatchOffManagerManualListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
