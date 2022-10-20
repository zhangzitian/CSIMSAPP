package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.ManualForkliftList;

public class ManualForkliftListAdapter extends ListBaseAdapter<ManualForkliftList.Bean>  implements View.OnClickListener{

    public ManualForkliftListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_manual_forklift_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ManualForkliftList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getT_prodNo());

        TextView tv_prodName = holder.getView(R.id.mWareHouseNo);
        tv_prodName.setText("产品名称：" + item.getT_prodName());

        TextView tv_Status = holder.getView(R.id.tv_Status);
        tv_Status.setText("类型：" + item.getT_taskType());

        TextView tv_piecesNum = holder.getView(R.id.tv_piecesNum);
        tv_piecesNum.setText("件：" + item.getT_nbox_num());

        TextView tv_packNum = holder.getView(R.id.tv_packNum);
        tv_packNum.setText("包：" + item.getT_npack_num());

        TextView tv_bookNum = holder.getView(R.id.tv_bookNum);
        tv_bookNum.setText("册：" + item.getT_nbook_num());

        TextView tv_total = holder.getView(R.id.tv_total);
        tv_total.setText("总数：" + item.getT_count());

        TextView tv_from = holder.getView(R.id.tv_from);
        tv_from.setText("库位号/库区：" + item.getT_fromNo() );
//        if(item.getT_fromNo().equals(""))
//        {
//            tv_from.setText("库位号/库区：" + item.getT_fromArea()  );
//        }else
//        {
//            tv_from.setText("库位号/库区：" + item.getT_fromArea() +"("+ item.getT_fromNo()+")" );
//        }

        TextView tv_to = holder.getView(R.id.tv_to);
        tv_to.setText("库位号/库区：" + item.getT_toNo() );
//        if(item.getT_toNo().equals(""))
//        {
//            tv_to.setText("库位号/库区：" + item.getT_toArea()  );
//        }else
//        {
//            tv_to.setText("库位号/库区：" + item.getT_toArea() +"("+ item.getT_toNo()+")" );
//        }

        Button btnGain = holder.getView(R.id.btnGain);
        btnGain.setOnClickListener(ManualForkliftListAdapter.this);
        btnGain.setTag(position);

        Button btnOperation = holder.getView(R.id.btnOperation);
        btnOperation.setOnClickListener(ManualForkliftListAdapter.this);
        btnOperation.setTag(position);

        if(item.getT_taskState().equals("未领取"))
        {
            btnGain.setVisibility(View.VISIBLE);
            btnOperation.setVisibility(View.GONE);
        }
        else if(item.getT_taskState().equals("已领取"))
        {
            btnOperation.setVisibility(View.VISIBLE);
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
        void onItemClick(View v, ManualForkliftListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private ManualForkliftListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(ManualForkliftListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, ManualForkliftListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }



}
