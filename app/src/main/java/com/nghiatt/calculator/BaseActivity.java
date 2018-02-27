package com.nghiatt.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 08/09/2015.
 */
public class BaseActivity extends AppCompatActivity {
    private final String TAG="BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass, Bundle bundle){
        startFragment(fragmentClass, bundle, false);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, boolean isAddBackStack) {
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            if (bundle != null) {
                fragment.setArguments(bundle);
            }

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment, fragmentClass.getCanonicalName());
            if (isAddBackStack) {
                fragmentTransaction.addToBackStack(fragmentClass.getCanonicalName());
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public List<Fragment> getListFragmentVisible(){
        List<Fragment> list=new ArrayList<>();
        List<Fragment> allFragment=getSupportFragmentManager().getFragments();
        for(Fragment fragment:allFragment){
            if(fragment.isVisible()){
                list.add(fragment);
            }
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG,"BackStackEntryCount="+getSupportFragmentManager().getBackStackEntryCount()+" -- list fragment="+getSupportFragmentManager().getFragments().size());
        super.onBackPressed();
    }
}
