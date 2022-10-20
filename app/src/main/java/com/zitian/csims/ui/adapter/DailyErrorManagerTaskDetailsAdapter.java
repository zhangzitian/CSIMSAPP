package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.DailyErrorManagerTaskDetails;

public class DailyErrorManagerTaskDetailsAdapter  extends ListBaseAdapter<DailyErrorManagerTaskDetails.Bean>  implements View.OnClickListener{

    public DailyErrorManagerTaskDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_error_manager_task_details_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        DailyErrorManagerTaskDetails.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getWh_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getWh_prodName());

        TextView title4 = holder.getView(R.id.tv_prodNumber);
        title4.setText("数量：" + item.getWh_prodNumber() );

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getWh_wareArea()+"("+item.getWh_wareHouseNo()+")" );

//        title3.setOnClickListener(DailyErrorManagerTaskDetailsAdapter.this);
//        title4.setOnClickListener(DailyErrorManagerTaskDetailsAdapter.this);
//        title3.setTag(position);
//        title4.setTag(position);
//
//        //未开始，已领取，已完成，已审核，已关闭
//        Button btnGain = holder.getView(R.id.btnGain);
//        Button btnOperation = holder.getView(R.id.btnOperation);
//        btnGain.setOnClickListener(DailyErrorManagerTaskDetailsAdapter.this);
//        btnOperation.setOnClickListener(DailyErrorManagerTaskDetailsAdapter.this);
//        btnGain.setTag(position);
//        btnOperation.setTag(position);
//
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
        void onItemClick(View v, DailyErrorManagerTaskDetailsAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private DailyErrorManagerTaskDetailsAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(DailyErrorManagerTaskDetailsAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, DailyErrorManagerTaskDetailsAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
