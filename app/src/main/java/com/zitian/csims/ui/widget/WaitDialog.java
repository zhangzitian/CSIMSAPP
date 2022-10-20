package com.zitian.csims.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zitian.csims.R;

public class WaitDialog  extends Dialog {
    private Dialog progressDialog;

    public WaitDialog(Context context) {
        super(context);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setCanceledOnTouchOutside(false);
        //setProgressStyle(STYLE_SPINNER);
        //setMessage(context.getString(R.string.wait_dialog_title));
        progressDialog = new Dialog(context,R.style.progress_dialog);
        progressDialog.setContentView(R.layout.progress_dialog);
        //progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(R.string.wait_dialog_title);
        progressDialog.show();
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }

    public void cancel(){
        progressDialog.dismiss();
    }

    public void dismiss(){
        progressDialog.dismiss();
    }

    public void show(){
        progressDialog.show();
    }

    public boolean isShowing() {
        return progressDialog.isShowing();
    }
    public void setCancelable(boolean flag) {
        progressDialog.setCancelable(flag);
        progressDialog.setCanceledOnTouchOutside(false);
    }

}
