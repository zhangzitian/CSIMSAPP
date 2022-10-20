package com.zitian.csims.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.zitian.csims.R;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    protected void openActivity(Class<?> mClass) {
        Log.d(TAG, "openActivity: "+mClass.getSimpleName());
        openActivity(mClass,null);
    }

    protected void openActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(),mClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity with bundle: open "+mClass.getSimpleName());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    protected void openActivityWithoutAnim(Class<?> mClass) {
        Log.d(TAG, "openActivityWithoutAnim: "+mClass.getSimpleName());
        openActivity(mClass,null);
    }

    protected void openActivityWithoutAnim(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(),mClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivityWithoutAnim with bundle: "+mClass.getSimpleName());
        startActivity(intent);
    }

    protected void openActivity(String action) {
        openActivity(action,null);
    }

    protected void openActivity(String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity by action: action----"+action);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}