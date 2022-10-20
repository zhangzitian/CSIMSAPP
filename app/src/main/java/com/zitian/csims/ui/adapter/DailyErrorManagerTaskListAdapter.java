package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.DailyErrorManagerTaskList;

public class DailyErrorManagerTaskListAdapter extends ListBaseAdapter<DailyErrorManagerTaskList.Bean>  implements View.OnClickListener{

    public DailyErrorManagerTaskListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_error_manager_task_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        DailyErrorManagerTaskList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getD_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getD_prodName());

        TextView title3 = holder.getView(R.id.tv_type);
        title3.setText("盘点类型：" + item.getD_Type());

        TextView title4 = holder.getView(R.id.tv_trayType);
        title4.setText("托盘性质：" + item.getD_trayType() == null ? "" :  item.getD_trayType());

        TextView title5 = holder.getView(R.id.tv_count);
        title5.setText("数量：" + item.getD_count());

//        TextView title6 = holder.getView(R.id.tv_toNoAndArea);
//        title6.setText("搬运到：" + item.getD_count());
//        title6.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//
//        title6.setOnClickListener(DailyErrorManagerTaskListAdapter.this);
//        title6.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
//        Button btnGain = holder.getView(R.id.btnGain);
         Button btnOperation = holder.getView(R.id.btnOperation);
//        btnGain.setOnClickListener(DailyErrorManagerTaskListAdapter.this);
        btnOperation.setOnClickListener(DailyErrorManagerTaskListAdapter.this);
//        btnGain.setTag(position);
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
        void onItemClick(View v, DailyErrorManagerTaskListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private DailyErrorManagerTaskListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(DailyErrorManagerTaskListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, DailyErrorManagerTaskListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
