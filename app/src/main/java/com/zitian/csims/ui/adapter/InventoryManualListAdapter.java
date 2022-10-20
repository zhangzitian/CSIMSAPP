package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.model.InventoryManualList;

public class InventoryManualListAdapter extends ListBaseAdapter<InventoryManualList.Bean>  implements View.OnClickListener{

    public int selectCount = 0;

    public InventoryManualListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_manual_list_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        InventoryManualList.Bean item = mDataList.get(position);

        TextView tv_prodNo = holder.getView(R.id.tv_prodNo);
        tv_prodNo.setText("产品编码：" + item.getWh_prodNo());

        TextView title2 = holder.getView(R.id.tv_ProdName);
        title2.setText("产品名称：" + item.getWh_prodName());

        TextView title3 = holder.getView(R.id.tv_fromNoAndArea);
        title3.setText("库位/库区:" + item.getWh_wareHouseNo());

        CheckBox checkBox = holder.getView(R.id.mWareHouseNo);
        checkBox.setTag(position);

        title3.setOnClickListener(InventoryManualListAdapter.this);
        title3.setTag(position);

        //未开始，已领取，已完成，已审核，已关闭
        //Button btnGain = holder.getView(R.id.btnGain);
        Button btnOperation = holder.getView(R.id.btnOperation);
        //btnGain.setOnClickListener(InventoryManualListAdapter.this);
        btnOperation.setOnClickListener(InventoryManualListAdapter.this);
        //btnGain.setTag(position);
        btnOperation.setTag(position);

        checkBox.setOnClickListener(InventoryManualListAdapter.this);
        checkBox.setTag(position);

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(item.isHasChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setHasChecked(true);
                    selectCount++;
                } else {
                    item.setHasChecked(false);
                    selectCount--;
                }
            }
        });

        TextView status = holder.getView(R.id.tv_Status);
        if(item.getR_error5().contains("Table_InventoryManualScattered"))
        {
            checkBox.setVisibility(View.GONE);
            if(item.getU1_userid().equals(CSIMSApplication.getAppContext().getUser().getO_id()))
            {
                if(item.getU1_errorType().equals("暂停"))
                {
                    status.setText("暂停");
                    checkBox.setVisibility(View.VISIBLE);
                }
                else if(item.getU1_addDate().equals("") && item.getWh_status() == 3)
                {
                    status.setText("预留-未盘");
                }
                else if(!item.getU1_addDate().equals("") && item.getWh_status() == 3 && item.getU1_isError()==1)
                {
                    status.setText("预留-纠错");
                }
                else if(!item.getU1_addDate().equals("") && item.getWh_status() == 3)
                {
                    status.setText("预留-已盘");
                }
                else if(item.getU1_addDate().equals("") && item.getWh_isInvalid() == 1)
                {
                    status.setText("作废-未盘");
                }
                else if(!item.getU1_addDate().equals("") && item.getWh_isInvalid() == 1 && item.getU1_isError()==1)
                {
                    status.setText("作废-纠错");
                }
                else if(!item.getU1_addDate().equals("") && item.getWh_isInvalid() == 1)
                {
                    status.setText("作废-已盘");
                }
                else if(item.getU1_isError()==1)
                {
                    status.setText("纠错");
                }
                else if(item.getU1_addDate().equals(""))
                {
                    status.setText("未盘");
                }
                else if(!item.getU1_addDate().equals(""))
                {
                    status.setText("已盘");
                }
            }
            else if(item.getU2_userid().equals(CSIMSApplication.getAppContext().getUser().getO_id()))
            {
                if(item.getU2_errorType().equals("暂停"))
                {
                    status.setText("暂停");
                    checkBox.setVisibility(View.VISIBLE);
                }
                //else if(item.getU2_contrastResult()==1)
                //{
                //    status.setText("比对正确");
                //}
                //else if(item.getU2_contrastResult()==2)
                //{
                //    status.setText("比对错误");
                //}
                else if(item.getU2_addDate().equals("") && item.getWh_status() == 3)
                {
                    status.setText("预留-未盘");
                }
                else if(!item.getU2_addDate().equals("") && item.getWh_status() == 3 && item.getU2_isError()==1)
                {
                    status.setText("预留-纠错");
                }
                else if(!item.getU2_addDate().equals("") && item.getWh_status() == 3)
                {
                    status.setText("预留-已盘");
                }
                else if(item.getU2_addDate().equals("") && item.getWh_isInvalid() == 1)
                {
                    status.setText("作废-未盘");
                }
                else if(!item.getU2_addDate().equals("") && item.getWh_isInvalid() == 1 && item.getU2_isError()==1)
                {
                    status.setText("作废-纠错");
                }
                else if(!item.getU2_addDate().equals("") && item.getWh_isInvalid() == 1)
                {
                    status.setText("作废-已盘");
                }
                else if(item.getU2_isError()==1)
                {
                    status.setText("纠错");
                }
                else if(item.getU2_addDate().equals(""))
                {
                    status.setText("未盘");
                }
                else if(!item.getU2_addDate().equals(""))
                {
                    status.setText("已盘");
                }
            }else
            {
                status.setText("");
            }
            btnOperation.setVisibility(View.VISIBLE);
        }
        else
        {
            if(item.getU1_userid().equals(CSIMSApplication.getAppContext().getUser().getO_id()))
            {
                //if(item.getU1_contrastResult()==1)
                //{
                //    status.setText("比对正确");
                //}
                //else if(item.getU1_contrastResult()==2)
                //{
                //    status.setText("比对错误");
                //}else
                if(item.getU1_isError()==1)
                {
                    status.setText("纠错");
                }
                else if(item.getU1_addDate().equals(""))
                {
                    status.setText("未盘");
                }else if(!item.getU1_addDate().equals(""))
                {
                    status.setText("已盘");
                }
            }
            else if(item.getU2_userid().equals(CSIMSApplication.getAppContext().getUser().getO_id()))
            {
                //if(item.getU2_contrastResult()==1)
                //{
                //    status.setText("比对正确");
                //}
                //else if(item.getU2_contrastResult()==2)
                //{
                //    status.setText("比对错误");
                //}else
                if(item.getU2_isError()==1)
                {
                    status.setText("纠错");
                }
                else if(item.getU2_addDate().equals(""))
                {
                    status.setText("未盘");
                }else if(!item.getU2_addDate().equals(""))
                {
                    status.setText("已盘");
                }
            }else
            {
                status.setText("");
            }
            checkBox.setVisibility(View.GONE);
            btnOperation.setVisibility(View.VISIBLE);
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
        void onItemClick(View v, InventoryManualListAdapter.ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private InventoryManualListAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(InventoryManualListAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, InventoryManualListAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

}
