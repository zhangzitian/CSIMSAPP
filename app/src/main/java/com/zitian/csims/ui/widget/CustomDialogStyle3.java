package com.zitian.csims.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitian.csims.R;

public class CustomDialogStyle3 extends Dialog {

    public CustomDialogStyle3(Context context) {
        super(context);
    }

    public CustomDialogStyle3(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialogStyle3(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private String title;  //标题
        //private String message;//提示消息
        private String prodNoStr;//提示消息
        private String prodNameStr;//提示消息
        private String nbox_numStr;//提示消息
        private String npack_numStr;//提示消息
        private String nbook_numStr;//提示消息

        private String negative_text;//消极的
        private String positive_text;//积极的
        private DialogInterface.OnClickListener negativeListener;//消极的监听
        private DialogInterface.OnClickListener positiveListener;//积极的监听

        public Builder(Context context) {
            this.context = context;
        }

        public CustomDialogStyle3.Builder setTitle(String title) {
            if (title == null) {
                this.title = "提醒";
            }
            this.title = title;
            return this;
        }

        public CustomDialogStyle3.Builder setProdNo(String prodNoStr) {
            //if (prodNoStr == null) {
            //    this.prodNoStr = "";
            //}
            this.prodNoStr = prodNoStr;
            return this;
        }

        public CustomDialogStyle3.Builder setProdName(String prodNameStr) {
            //if (prodNameStr == null) {
            //    this.prodNameStr = "提醒";
            //}
            this.prodNameStr = prodNameStr;
            return this;
        }

        public CustomDialogStyle3.Builder setNbox_num(String nbox_numStr) {
            //if (nbox_numStr == null) {
            //    this.nbox_numStr = "提醒";
            //}
            this.nbox_numStr = nbox_numStr;
            return this;
        }

        public CustomDialogStyle3.Builder setNpack_num(String npack_numStr) {
            //if (npack_numStr == null) {
            //    this.npack_numStr = "提醒";
            //}
            this.npack_numStr = npack_numStr;
            return this;
        }

        public CustomDialogStyle3.Builder setNbook_num(String nbook_numStr) {
            //if (nbook_numStr == null) {
            //    this.nbook_numStr = "提醒";
            //}
            this.nbook_numStr = nbook_numStr;
            return this;
        }

//        public CustomDialogStyle3.Builder setMessage(String message) {
//            if (message == null) {
//                this.message = "您没有填写提示信息哦";
//            }
//            this.message = message;
//            return this;
//        }

        public CustomDialogStyle3.Builder setNegativeButton(String negative_text, DialogInterface.OnClickListener negativeListener) {
            if (negative_text == null) {
                this.negative_text = "取消";
            }
            this.negative_text = negative_text;
            this.negativeListener = negativeListener;

            return this;
        }

        public CustomDialogStyle3.Builder setPositionButton(String positive_text, DialogInterface.OnClickListener positiveListener) {
            if (positive_text == null) {
                this.positive_text = "确定";
            }
            this.positive_text = positive_text;
            this.positiveListener = positiveListener;

            return this;
        }

        private TextView tv_title_custom_dialog;  //标题
        //public AutoCompleteTextView tv_message_custom_dialog;//提示信息
        public AutoCompleteTextView prodNo;
        public TextView prodName;
        public EditText nbox_num;
        public EditText npack_num;
        public EditText nbook_num;

        private Button btn_negative_custom_dialog;//消极
        private Button btn_positive_custom_dialog;//积极


        public CustomDialogStyle3 create() {
            final CustomDialogStyle3 dialog = new CustomDialogStyle3(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_style_layout3, null);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//加上这一句，取消原来的标题栏，没加这句之前，发现在三星的手机上会有一条蓝色的线
//            dialog.addContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tv_title_custom_dialog = (TextView) view.findViewById(R.id.tv_title_custom_dialog);

            prodNo = (AutoCompleteTextView) view.findViewById(R.id.prodNo);
            prodName = (TextView) view.findViewById(R.id.prodName);
            nbox_num = (EditText) view.findViewById(R.id.nbox_num);
            npack_num = (EditText) view.findViewById(R.id.npack_num);
            nbook_num = (EditText) view.findViewById(R.id.nbook_num);

            btn_negative_custom_dialog = (Button) view.findViewById(R.id.btn_negative_custom_dialog);
            btn_positive_custom_dialog = (Button) view.findViewById(R.id.btn_positive_custom_dialog);
            tv_title_custom_dialog.setText(title);
            //tv_message_custom_dialog.setHint(message);

            prodNo.setText(prodNoStr);
            prodName.setText(prodNameStr);

            nbox_num.setText(nbox_numStr);
            npack_num.setText(npack_numStr);
            nbook_num.setText(nbook_numStr);
            nbox_num.setFocusable(true);
            btn_negative_custom_dialog.setText(negative_text);
            btn_positive_custom_dialog.setText(positive_text);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            btn_negative_custom_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeListener.onClick(dialog, Dialog.BUTTON_NEGATIVE);
                }
            });
            btn_positive_custom_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveListener.onClick(dialog, Dialog.BUTTON_POSITIVE);
                }
            });
            return dialog;
        }
    }
}