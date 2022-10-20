package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zitian.csims.R;
import com.zitian.csims.model.InventoryEmpty;
import com.zitian.csims.model.InventoryEmpty2;
import com.zitian.csims.model.InventoryErrorList;

import java.util.List;

public class InventoryEmptyAdapter extends RecyclerView.Adapter<InventoryEmptyAdapter.ViewHolder>  implements View.OnClickListener,View.OnLongClickListener{

    public List<InventoryEmpty2.Bean> mDataList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout bt1;
        TextView tv1;
        ImageView iv1;

        RelativeLayout bt2;
        TextView tv2;
        ImageView iv2;

        RelativeLayout bt3;
        TextView tv3;
        ImageView iv3;

        RelativeLayout bt4;
        TextView tv4;
        ImageView iv4;

        public ViewHolder(View itemView) {
            super(itemView);
            //注意这里可能需要import com.example.lenovo.myrecyclerview.R; 才能使用R.id
            //imageAvatar = (ImageView)itemView.findViewById(R.id.horizontalImageView);
            //nameText =(TextView) itemView.findViewById(R.id.horizontalTextViewName);
            //contentsText = (TextView)itemView.findViewById(R.id.horizontalTextViewContent);

            bt1 = (RelativeLayout)itemView.findViewById(R.id.bt1);
            tv1 = (TextView)itemView.findViewById(R.id.tv1);
            iv1 = (ImageView)itemView.findViewById(R.id.iv1);

            bt2 = (RelativeLayout)itemView.findViewById(R.id.bt2);
            tv2 = (TextView)itemView.findViewById(R.id.tv2);
            iv2 = (ImageView)itemView.findViewById(R.id.iv2);

            bt3 = (RelativeLayout)itemView.findViewById(R.id.bt3);
            tv3 = (TextView)itemView.findViewById(R.id.tv3);
            iv3 = (ImageView)itemView.findViewById(R.id.iv3);

            bt4 = (RelativeLayout)itemView.findViewById(R.id.bt4);
            tv4 = (TextView)itemView.findViewById(R.id.tv4);
            iv4 = (ImageView)itemView.findViewById(R.id.iv4);
        }
    }
    public  InventoryEmptyAdapter(List<InventoryEmpty2.Bean> listDatas){
        mDataList = listDatas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory_empty_item,
                parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InventoryEmpty2.Bean listData = mDataList.get(position);
        //holder.imageAvatar.setImageResource(listData.getImageView());
        //holder.nameText.setText(listData.getNameText());
        //holder.contentsText.setText(listData.getContentsText());

        //String  str = listData.getStatus();
        //if(str.endsWith("@"))
        //    str = str.substring(0,str.length()-2);

        String[] str2 = listData.getStatus().split("@");
        if(str2.length == 3 )
        {
            //1
            String str3 = str2[0].split(",")[0];
            String str4 = str3.substring(0,str3.lastIndexOf("-"))+"-1";
            holder.tv1.setText(str4);
            Have(holder.bt1,holder.tv1,holder.iv1);
            //2
            holder.tv2.setText(str2[0].split(",")[0]);
            //是否满盘
            if(str2[0].split(",")[1].equals("0"))
            {
                Have2(holder.bt2,holder.tv2,holder.iv2);
            }else if(str2[0].split(",")[1].equals("2"))
            {
                Have3(holder.bt2,holder.tv2,holder.iv2);
            }
            //是否纠错
            if(str2[0].split(",")[2].equals("1"))
            {
                Have4(holder.iv2,View.VISIBLE);
            }else
            {
                Have4(holder.iv2,View.GONE);
            }
            //是否弃用和作废
            if(str2[0].split(",")[3].equals("3"))
            {
                holder.tv2.setText(str2[0].split(",")[0] + "留");//Have(holder.bt2,holder.tv2,holder.iv2);
                //holder.tv2.setTextSize(10);
                Have(holder.bt2,holder.tv2,holder.iv2);
            }else if(str2[0].split(",")[4].equals("1"))
            {
                holder.tv2.setText(str2[0].split(",")[0] + "废");//Have(holder.bt2,holder.tv2,holder.iv2);
                //holder.tv2.setTextSize(10);
                Have(holder.bt2,holder.tv2,holder.iv2);
            }
            //3
            holder.tv3.setText(str2[1].split(",")[0]);
            //是否满盘
            if(str2[1].split(",")[1].equals("0"))
            {
                Have2(holder.bt3,holder.tv3,holder.iv3);
            }else if(str2[1].split(",")[1].equals("2"))
            {
                Have3(holder.bt3,holder.tv3,holder.iv3);
            }
            //是否纠错
            if(str2[1].split(",")[2].equals("1"))
            {
                Have4(holder.iv3,View.VISIBLE);
            }else
            {
                Have4(holder.iv3,View.GONE);
            }
            //是否弃用和作废
            if(str2[1].split(",")[3].equals("3"))
            {
                holder.tv3.setText(str2[1].split(",")[0] + "留");
                //holder.tv3.setTextSize(10);
                Have(holder.bt3,holder.tv3,holder.iv3);
            }else if(str2[1].split(",")[4].equals("1"))
            {
                holder.tv3.setText(str2[1].split(",")[0] + "废");
                //holder.tv3.setTextSize(10);
                Have(holder.bt3,holder.tv3,holder.iv3);
            }
            //4
            holder.tv4.setText(str2[2].split(",")[0]);
            //是否满盘
            if(str2[2].split(",")[1].equals("0"))
            {
                Have2(holder.bt4,holder.tv4,holder.iv4);
            }else if(str2[2].split(",")[1].equals("2"))
            {
                Have3(holder.bt4,holder.tv4,holder.iv4);
            }
            //是否纠错
            if(str2[2].split(",")[2].equals("1"))
            {
                Have4(holder.iv4,View.VISIBLE);
            }else
            {
                Have4(holder.iv4,View.GONE);
            }
            //是否弃用和作废
            if(str2[2].split(",")[3].equals("3"))
            {
                holder.tv4.setText(str2[2].split(",")[0] + "留");
                //holder.tv4.setTextSize(10);
                Have(holder.bt4,holder.tv4,holder.iv4);
            }else if(str2[2].split(",")[4].equals("1"))
            {
                holder.tv4.setText(str2[2].split(",")[0] + "废");
                //holder.tv4.setTextSize(10);
                Have(holder.bt4,holder.tv4,holder.iv4);
            }
        }
        if(str2.length == 4)
        {
            //1
            holder.tv1.setText(str2[0].split(",")[0]);
            //是否满盘
            if(str2[0].split(",")[1].equals("0"))
            {
                Have2(holder.bt1,holder.tv1,holder.iv1);
            }
            else if(str2[0].split(",")[1].equals("2"))
            {
                Have3(holder.bt1,holder.tv1,holder.iv1);
            }
            //显示纠错
            if(str2[0].split(",")[2].equals("1"))
            {
                Have4(holder.iv1,View.VISIBLE);
            }
            else
            {
                Have4(holder.iv1,View.GONE);
            }
            //是否弃用和作废
            if(str2[0].split(",")[3].equals("3"))
            {
                holder.tv1.setText(str2[0].split(",")[0] + "留");
                //holder.tv1.setTextSize(10);
                Have(holder.bt1,holder.tv1,holder.iv1);
            }
            else if(str2[0].split(",")[4].equals("1"))
            {
                holder.tv1.setText(str2[0].split(",")[0] + "废");
                //holder.tv1.setTextSize(10);
                Have(holder.bt1,holder.tv1,holder.iv1);
            }
            //2
            holder.tv2.setText(str2[1].split(",")[0]);
            //显示纠错
            if(str2[1].split(",")[2].equals("1"))
            {
                Have4(holder.iv2,View.VISIBLE);
            }
            else
            {
                Have4(holder.iv2,View.GONE);
            }
            //是否满盘
            if(str2[1].split(",")[1].equals("0"))
            {
                Have2(holder.bt2,holder.tv2,holder.iv2);
            }
            else if(str2[1].split(",")[1].equals("2"))
            {
                Have3(holder.bt2,holder.tv2,holder.iv2);
            }
            //是否弃用和作废
            if(str2[1].split(",")[3].equals("3"))
            {
                holder.tv2.setText(str2[1].split(",")[0] + "留");
                //holder.tv2.setTextSize(10);
                Have(holder.bt2,holder.tv2,holder.iv2);
            }
            else if(str2[1].split(",")[4].equals("1"))
            {
                holder.tv2.setText(str2[1].split(",")[0] + "废");
                //holder.tv2.setTextSize(10);
                Have(holder.bt2,holder.tv2,holder.iv2);
            }

            holder.tv3.setText(str2[2].split(",")[0]);
            //是否满盘
            if(str2[2].split(",")[1].equals("0"))
            {
                Have2(holder.bt3,holder.tv3,holder.iv3);
            }
            else if(str2[2].split(",")[1].equals("2"))
            {
                Have3(holder.bt3,holder.tv3,holder.iv3);
            }
            //显示纠错
            if(str2[2].split(",")[2].equals("1"))
            {
                Have4(holder.iv3,View.VISIBLE);
            }
            else
            {
                Have4(holder.iv3,View.GONE);
            }
            //是否弃用和作废
            if(str2[2].split(",")[3].equals("3"))
            {
                holder.tv3.setText(str2[2].split(",")[0] + "留");
                //holder.tv3.setTextSize(10);
                Have(holder.bt3,holder.tv3,holder.iv3);
            }else if(str2[2].split(",")[4].equals("1"))
            {
                holder.tv3.setText(str2[2].split(",")[0] + "废");
                //holder.tv3.setTextSize(10);
                Have(holder.bt3,holder.tv3,holder.iv3);
            }
            holder.tv4.setText(str2[3].split(",")[0]);
            //是否满盘
            if(str2[3].split(",")[1].equals("0"))
            {
                Have2(holder.bt4,holder.tv4,holder.iv4);
            }
            else if(str2[3].split(",")[1].equals("2"))
            {
                Have3(holder.bt4,holder.tv4,holder.iv4);
            }
            //显示纠错
            if(str2[3].split(",")[2].equals("1"))
            {
                Have4(holder.iv4,View.VISIBLE);
            }
            else
            {
                Have4(holder.iv4,View.GONE);
            }
            //是否弃用和作废
            if(str2[3].split(",")[3].equals("3"))
            {
                holder.tv4.setText(str2[3].split(",")[0] + "留");
                //holder.tv4.setTextSize(10);
                Have(holder.bt4,holder.tv4,holder.iv4);
            }
            else if(str2[3].split(",")[4].equals("1"))
            {
                holder.tv4.setText(str2[3].split(",")[0] + "废");
                //holder.tv4.setTextSize(10);
                Have(holder.bt4,holder.tv4,holder.iv4);
            }
        }

        holder.bt1.setOnClickListener(InventoryEmptyAdapter.this);
        holder.bt2.setOnClickListener(InventoryEmptyAdapter.this);
        holder.bt3.setOnClickListener(InventoryEmptyAdapter.this);
        holder.bt4.setOnClickListener(InventoryEmptyAdapter.this);

        holder.bt1.setOnLongClickListener(InventoryEmptyAdapter.this);
        holder.bt2.setOnLongClickListener(InventoryEmptyAdapter.this);
        holder.bt3.setOnLongClickListener(InventoryEmptyAdapter.this);
        holder.bt4.setOnLongClickListener(InventoryEmptyAdapter.this);

        holder.bt1.setTag(position);
        holder.bt2.setTag(position);
        holder.bt3.setTag(position);
        holder.bt4.setTag(position);
    }

    public void Have(RelativeLayout rl,TextView tv,ImageView iv)
    {
        rl.setBackgroundResource(R.drawable.about_btn_time_bg_white_point);
        tv.setTextColor(Color.parseColor("#cccccc"));
        iv.setVisibility(View.INVISIBLE);
    }

    public void Have2(RelativeLayout rl,TextView tv,ImageView iv)
    {
        rl.setBackgroundResource(R.drawable.about_btn_time_bg_white);
        tv.setTextColor(Color.parseColor("#20A1FF"));
        iv.setVisibility(View.INVISIBLE);
    }

    public void Have3(RelativeLayout rl,TextView tv,ImageView iv)
    {
        rl.setBackgroundResource(R.drawable.about_btn_time_bg_red);
        tv.setTextColor(Color.parseColor("#ffffff"));
        iv.setVisibility(View.INVISIBLE);
    }

    public void Have4(ImageView iv,int visibility)
    {
        iv.setVisibility(visibility);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, InventoryEmptyAdapter.ViewName viewName, int position);
        void onItemLongClick(View v, InventoryEmptyAdapter.ViewName viewName, int position);
    }
    private InventoryEmptyAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(InventoryEmptyAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemClick(v, InventoryEmptyAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }
    @Override
    public boolean onLongClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                default:
                    mOnItemClickListener.onItemLongClick(v, InventoryEmptyAdapter.ViewName.ITEM, position);
                    break;
            }
        }
        return true;
    }

}