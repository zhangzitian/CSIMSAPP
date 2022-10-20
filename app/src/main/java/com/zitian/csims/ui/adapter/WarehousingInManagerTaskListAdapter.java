package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.WarehousingInManagerTaskList;

public class WarehousingInManagerTaskListAdapter  extends ListBaseAdapter<WarehousingInManagerTaskList.Bean>  implements View.OnClickListener{

    public WarehousingInManagerTaskListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_warehousing_in_manager_task_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        WarehousingInManagerTaskList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getT_prodNo());

        TextView title2 = holder.getView(R.id.mWareHouseNo);
        title2.setText("产品名称：" + item.getT_prodName());

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getT_toNo() );
        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView mOperatorName = holder.getView(R.id.mOperatorName);
        mOperatorName.setText("发起人：" + item.getOperatorName());

        TextView title5 = holder.getView(R.id.tv_Status);
        title5.setText("产品状态：" + item.getT_trayType());

        title3.setOnClickListener(WarehousingInManagerTaskListAdapter.this);
        title3.setTag(position);

        Button btnOperation = holder.getView(R.id.btnOperation);
        btnOperation.setOnClickListener(WarehousingInManagerTaskListAdapter.this);
        btnOperation.setTag(position);
        if(item.getT_taskState().equals("已暂停") )
        {
            btnOperation.setVisibility(View.VISIBLE);
        }else
        {
            btnOperation.setVisibility(View.GONE);
        }

        Button btnQuxiao = holder.getView(R.id.btnQuxiao);
        btnQuxiao.setOnClickListener(WarehousingInManagerTaskListAdapter.this);
        btnQuxiao.setTag(position);
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, WarehousingInManagerTaskListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private WarehousingInManagerTaskListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(WarehousingInManagerTaskListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, WarehousingInManagerTaskListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }


}
