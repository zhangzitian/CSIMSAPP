package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.BatchOffManagerAdd;

public class BatchOffManagerAddAdapter extends ListBaseAdapter<BatchOffManagerAdd.Bean>  implements View.OnClickListener{

    public BatchOffManagerAddAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_batch_off_manager_add_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        BatchOffManagerAdd.Bean item = mDataList.get(position);

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位号/库区：" + item.getWh_wareHouseNo() );
        //title3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        title3.setOnClickListener(BatchOffManagerAddAdapter.this);
        title3.setTag(position);

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
        void onItemClick(View v, BatchOffManagerAddAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private BatchOffManagerAddAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(BatchOffManagerAddAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, BatchOffManagerAddAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
