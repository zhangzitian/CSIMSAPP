package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.DeliveryNoteModels;

public class DeliveryNoteAdapter extends ListBaseAdapter<DeliveryNoteModels.Bean>   {
    public DeliveryNoteAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_warehouse_datalist_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        DeliveryNoteModels.Bean item = mDataList.get(position);

        TextView tv_factoryid = holder.getView(R.id.tv_factoryid);
        tv_factoryid.setText("加工厂名称：" + item.getfactory_id());

        TextView title2 = holder.getView(R.id.mlicense_number);
        title2.setText("车号：" + item.getlicense_number());

        TextView title1 = holder.getView(R.id.tv_license_type);
        title1.setText("车型：" + item.getlicense_type());

        TextView title3 = holder.getView(R.id.tv_product_total);
        title3.setText("品种数：" + item.get_Count());

    }
}