package com.zitian.csims.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zitian.csims.R;
import com.zitian.csims.model.OutofSpaceForkliftList;

import java.util.List;

public class OutofSpaceForkliftListAdapter2 extends BaseAdapter {

    private List<OutofSpaceForkliftList.Bean> mContentList;
    private LayoutInflater mInflater;
    private OutofSpaceForkliftListAdapter2.MyClickListener mListener;

    public OutofSpaceForkliftListAdapter2(Context context, List<OutofSpaceForkliftList.Bean> contentList) {
        mContentList = contentList;
        mInflater = LayoutInflater.from(context);
        //mListener = listener;
    }

    public void setListener(MyClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        //Log.i(TAG, "getCount");
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        //Log.i(TAG, "getItem");
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Log.i(TAG, "getItemId");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i(TAG, "getView");
        OutofSpaceForkliftListAdapter2.AboutAllTeachAdaoperViewHolder aboutAllTeachAdaoperViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_outof_space_forklift_list_item, null);
            aboutAllTeachAdaoperViewHolder = new OutofSpaceForkliftListAdapter2.AboutAllTeachAdaoperViewHolder();

//            aboutAllTeachAdaoperViewHolder.aratar = (ImageView) convertView.findViewById(R.id.aratar);
//            aboutAllTeachAdaoperViewHolder.tvname = (TextView) convertView.findViewById(R.id.tvname);
//            aboutAllTeachAdaoperViewHolder.btnguanzhu = (Button) convertView.findViewById(R.id.btnguanzhu);
//            aboutAllTeachAdaoperViewHolder.btnyuyue = (Button) convertView.findViewById(R.id.btnyuyue);

            convertView.setTag(aboutAllTeachAdaoperViewHolder);
        } else {
            aboutAllTeachAdaoperViewHolder = (OutofSpaceForkliftListAdapter2.AboutAllTeachAdaoperViewHolder) convertView.getTag();
        }

        OutofSpaceForkliftList.Bean bean = mContentList.get(position);//实例指定位置的水果
        //timetableViewHolder.aratar.setText("" + bean.getGName() + "");
        //aboutAllTeachAdaoperViewHolder.tvname.setText(""+bean.getTeacherName());
//        aboutAllTeachAdaoperViewHolder.btnguanzhu.setOnClickListener(mListener);
//        aboutAllTeachAdaoperViewHolder.btnguanzhu.setTag(position);
//        aboutAllTeachAdaoperViewHolder.btnyuyue.setOnClickListener(mListener);
//        aboutAllTeachAdaoperViewHolder.btnyuyue.setTag(position);
        //（是否关注 0：未关注 1：已关注）
//        if(bean.getIsFollow() == 0)
//        {
//            aboutAllTeachAdaoperViewHolder.btnguanzhu.setText("+未关注");
//        }else
//        {
//            aboutAllTeachAdaoperViewHolder.btnguanzhu.setText("√已关注");
//        }
        return convertView;
    }

    public class AboutAllTeachAdaoperViewHolder {
        ImageView aratar;
        TextView tvname;
        Button btnguanzhu;
        Button btnyuyue;
    }

    /**
     * 用于回调的抽象类
     * @author Ivan Xu
     * 2014-11-26
     */
    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }
        public abstract void myOnClick(int position, View v);
    }

}