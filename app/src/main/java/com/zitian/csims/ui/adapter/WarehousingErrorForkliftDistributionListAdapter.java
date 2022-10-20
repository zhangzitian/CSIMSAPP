package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.model.WarehousingInManagerTaskList;

public class WarehousingErrorForkliftDistributionListAdapter extends ListBaseAdapter<WarehousingErrorForkliftDistributionList.Bean>  implements View.OnClickListener{

    public WarehousingErrorForkliftDistributionListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_warehousing_error_forklift_distribution_list_iem;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        WarehousingErrorForkliftDistributionList.Bean item = mDataList.get(position);
        //tv_Operation  tv_Status  tv_fromNoAndArea  btnOperation

        TextView tv_oeration = holder.getView(R.id.tv_Operation);
        tv_oeration.setText("操作人：" + item.getOperatorName());

        TextView tv_Status = holder.getView(R.id.tv_Status);
        tv_Status.setText("状态：" + item.getE_state());

        TextView tv_fromNoAndArea = holder.getView(R.id.tv_fromNoAndArea);
        if(item.getE_wareArea().equals(""))
        {
            tv_fromNoAndArea.setText("库位号/库区：" + item.getE_wareHouseNo() );
        }else
        {
            tv_fromNoAndArea.setText("库位号/库区：" + item.getE_wareHouseNo() + "(" + item.getE_wareArea()+ ")");
        }
        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        TextView btnOperation = holder.getView(R.id.btnOperation);
        btnOperation.setOnClickListener(WarehousingErrorForkliftDistributionListAdapter.this);
        btnOperation.setTag(position);

        //if(item.getT_taskState().equals("已暂停") )
        //{
        //    btnOperation.setVisibility(View.VISIBLE);
        //}else
        //{
        //    btnOperation.setVisibility(View.GONE);
        //}

        //Button btnQuxiao = holder.getView(R.id.btnQuxiao);
        //btnQuxiao.setOnClickListener(WarehousingErrorForkliftDistributionListAdapter.this);
        //btnQuxiao.setTag(position);
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, WarehousingErrorForkliftDistributionListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private WarehousingErrorForkliftDistributionListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(WarehousingErrorForkliftDistributionListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, WarehousingErrorForkliftDistributionListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }


}
