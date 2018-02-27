package com.nghiatt.calculator.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 08/09/2015.
 */
public class BaseFragment<T extends Activity> extends Fragment {
    protected T mActivity;

    @Override
    public void onAttach(Activity activity) {
        this.mActivity=(T)activity;
        super.onAttach(activity);
    }

    public T getMainActivity(){
        return mActivity;
    }
}
