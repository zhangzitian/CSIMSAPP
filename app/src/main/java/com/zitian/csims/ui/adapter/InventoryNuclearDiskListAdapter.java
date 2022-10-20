package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.InventoryManualList;
import com.zitian.csims.model.InventoryNuclearDiskList;
import com.zitian.csims.model.WarehousingInManagerTaskList;


public class InventoryNuclearDiskListAdapter  extends ListBaseAdapter<InventoryNuclearDiskList.Bean>  implements View.OnClickListener{

    public InventoryNuclearDiskListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_nuclear_disk_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        InventoryNuclearDiskList.Bean item = mDataList.get(position);
        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getWh_prodNo());

        TextView tv_prodName = holder.getView(R.id.mWareHouseNo);
        tv_prodName.setText("产品名称：" + item.getWh_prodName());

        TextView m_type = holder.getView(R.id.mType);
        m_type.setVisibility(View.GONE);

        TextView tv_Status = holder.getView(R.id.tv_Status);
        if(item.getU1_addDate().equals(""))
        {
            TextView tv_piecesNum = holder.getView(R.id.tv_piecesNum);
            tv_piecesNum.setText("件：" + item.getWh_nbox_num());

            TextView tv_packNum = holder.getView(R.id.tv_packNum);
            tv_packNum.setText("包：" + item.getWh_npack_num());

            TextView tv_bookNum = holder.getView(R.id.tv_bookNum);
            tv_bookNum.setText("册：" + item.getWh_nbook_num());

            TextView tv_total = holder.getView(R.id.tv_total);
            tv_total.setText("总数：" + item.getWh_prodNumber());

            tv_Status.setText("未盘");
        }else
        {
            TextView tv_piecesNum = holder.getView(R.id.tv_piecesNum);
            tv_piecesNum.setText("件：" + item.getU1_nbox_num());

            TextView tv_packNum = holder.getView(R.id.tv_packNum);
            tv_packNum.setText("包：" + item.getU1_npack_num());

            TextView tv_bookNum = holder.getView(R.id.tv_bookNum);
            tv_bookNum.setText("册：" + item.getU1_nbook_num());

            TextView tv_total = holder.getView(R.id.tv_total);
            tv_total.setText("总数：" + item.getU1_prodNumber());

            tv_Status.setText("已盘");
        }

        if(item.getU1_errorType().equals("增项"))
        {
            tv_Status.setText("增项-"+tv_Status.getText());
        }
        else if(item.getU1_errorType().equals("增加好书类型"))
        {
            tv_Status.setText("好书类型-"+tv_Status.getText());
        }
        else
        {
            //tv_Status.setText(tv_Status.getText());
            //tv_Status.setText("正常-"+tv_Status.getText());
        }

        TextView tv_createTime = holder.getView(R.id.tv_createTime);
        tv_createTime.setVisibility(View.GONE);

        //TextView tv_wareHouseNo = holder.getView(R.id.tv_wareHouseNo);
        //tv_wareHouseNo.setText("库位号/库区：" + item.getWh_wareHouseNo() );

        TextView btnOperation = holder.getView(R.id.btnOperation);
        btnOperation.setOnClickListener(InventoryNuclearDiskListAdapter.this);
        btnOperation.setTag(position);

        TextView btnGain = holder.getView(R.id.btnGain);
        btnGain.setOnClickListener(InventoryNuclearDiskListAdapter.this);
        btnGain.setTag(position);
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
