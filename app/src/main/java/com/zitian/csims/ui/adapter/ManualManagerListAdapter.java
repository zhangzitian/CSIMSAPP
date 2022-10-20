package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.ManualManagerList;

public class ManualManagerListAdapter  extends ListBaseAdapter<ManualManagerList.Bean>  implements View.OnClickListener{

    public ManualManagerListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_manual_manager_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ManualManagerList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getA_prodNo());

        TextView tv_prodName = holder.getView(R.id.mWareHouseNo);
        tv_prodName.setText("产品名称：" + item.getA_prodName());

        TextView m_type = holder.getView(R.id.mType);
        m_type.setText("类型：" + item.getA_type());

        TextView tv_piecesNum = holder.getView(R.id.tv_piecesNum);
        tv_piecesNum.setText("件：" + item.getA_nbox_num());

        TextView tv_packNum = holder.getView(R.id.tv_packNum);
        tv_packNum.setText("包：" + item.getA_npack_num());

        TextView tv_bookNum = holder.getView(R.id.tv_bookNum);
        tv_bookNum.setText("册：" + item.getA_nbook_num());

        TextView tv_total = holder.getView(R.id.tv_total);
        tv_total.setText("总数：" + item.getA_total());

        TextView tv_createTime = holder.getView(R.id.tv_createTime);
        tv_createTime.setText("创建时间：" + item.getA_createTime());

        TextView tv_wareHouseNo = holder.getView(R.id.tv_wareHouseNo);
        tv_wareHouseNo.setText("库位号/库区：" + item.getA_wareArea() +"("+ item.getA_wareHouseNo()+")" );

        if(item.getA_wareArea().equals(""))
        {
            tv_wareHouseNo.setText("库位号/库区：" + item.getA_wareHouseNo()  );
        }else
        {
            tv_wareHouseNo.setText("库位号/库区：" + item.getA_wareArea() +"("+ item.getA_wareHouseNo()+")" );
        }

        TextView btnOperation = holder.getView(R.id.btnOperation);
        btnOperation.setOnClickListener(ManualManagerListAdapter.this);
        btnOperation.setTag(position);
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ManualManagerListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private ManualManagerListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(ManualManagerListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, ManualManagerListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }



}
