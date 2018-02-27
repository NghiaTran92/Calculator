package com.nghiatt.calculator;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nghiatt.calculator.adapter.CalculatorModeAdapter;
import com.nghiatt.calculator.fragment.BasicFragment;
import com.nghiatt.calculator.fragment.ProgrammingFragment;
import com.nghiatt.calculator.model.HistoryItem;
import com.nghiatt.calculator.model.ModeItem;
import com.nghiatt.polishnotation.EnumTypeDecimal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private final String TAG = "MainActivity";
    //private final String EXTRA_EXPRESSION = "Expression";
    public static final String EXTRA_NAME_UPDATE_EXPRESSION = "UpdateExpression";

    private TextView mTxtTitle;


    private Toolbar mToolbar;
    private AppCompatSpinner mSpinnerMode;
    private CalculatorModeAdapter mModeAdapter;


    private int mOldModeSelected = -1;
    private List<ModeItem> mModeList;
    private boolean isUserClick = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtTitle.setText(R.string.app_name);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSpinnerMode = (AppCompatSpinner) findViewById(R.id.spinner_mode);
        mModeList = getModeList();
        mModeAdapter = new CalculatorModeAdapter(this, mModeList);
        mSpinnerMode.setAdapter(mModeAdapter);
        mSpinnerMode.setOnItemSelectedListener(this);

        updateExpressionInFragment(getIntent());

        // replace Basic Fragment first
        // 0 : Basic Fragment

        mSpinnerMode.setSelection(0);
    }

    private List<ModeItem> getModeList() {
        ArrayList<ModeItem> list = new ArrayList<>();
        list.add(new ModeItem(getString(R.string.basic)));
        list.add(new ModeItem(getString(R.string.programming)));
        return list;
    }

    protected void updateExpressionInFragment(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            HistoryItem historyItem = (HistoryItem) bundle.getSerializable(EXTRA_NAME_UPDATE_EXPRESSION);
//            List<Fragment> list= getListFragmentVisible();
//            for(Fragment fragment:list){
//                if(fragment instanceof BasicFragment){
//                    Log.i(TAG," BasicFragment visible");
//                }else if(fragment instanceof  ProgrammingFragment){
//                    Log.i(TAG," ProgrammingFragment visible");
//                }
//            }
            int index = 0;
            if (historyItem.typeCalcu == EnumTypeDecimal.DECIMAL.getValue()) {
                startFragment(BasicFragment.class, bundle);
            } else {
                index = 1;
                startFragment(ProgrammingFragment.class, bundle);
            }
            isUserClick = false;
            mSpinnerMode.setSelection(index);

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateExpressionInFragment(intent);
    }

    /*
    * read more: http://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
    * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (isUserClick) {
            if (mOldModeSelected != position) {
                mOldModeSelected = position;
                switch (position) {
                    //1: programming fragment
                    case 1:

                        startFragment(ProgrammingFragment.class, null);
                        break;

                    //0: basic fragment
                    default:

                        startFragment(BasicFragment.class, null);
                        break;
                }

            }
        } else {
            isUserClick = true;
            mOldModeSelected = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
