package com.nghiatt.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String EXTRA_EXPRESSION = "Expression";

    private Button mBtnNum0;
    private Button mBtnNum1;
    private Button mBtnNum2;
    private Button mBtnNum3;
    private Button mBtnNum4;
    private Button mBtnNum5;
    private Button mBtnNum6;
    private Button mBtnNum7;
    private Button mBtnNum8;
    private Button mBtnNum9;
    private Button mBtnAdd;
    private Button mBtnSubtract;
    private Button mBtnMultiplication;
    private Button mBtnDivision;
    private Button mBtnEqual;
    private Button mBtnCE;
    private Button mBtnC;
    private Button mBtnSymOpen;
    private Button mBtnSymClose;
    private Button mBtnSymDot;

    private TextView mTxtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtResult = (TextView) findViewById(R.id.txt_result);

        mBtnNum0 = (Button) findViewById(R.id.btn_num_0);
        mBtnNum1 = (Button) findViewById(R.id.btn_num_1);
        mBtnNum2 = (Button) findViewById(R.id.btn_num_2);
        mBtnNum3 = (Button) findViewById(R.id.btn_num_3);
        mBtnNum4 = (Button) findViewById(R.id.btn_num_4);
        mBtnNum5 = (Button) findViewById(R.id.btn_num_5);
        mBtnNum6 = (Button) findViewById(R.id.btn_num_6);
        mBtnNum7 = (Button) findViewById(R.id.btn_num_7);
        mBtnNum8 = (Button) findViewById(R.id.btn_num_8);
        mBtnNum9 = (Button) findViewById(R.id.btn_num_9);

        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnSubtract = (Button) findViewById(R.id.btn_subtract);
        mBtnMultiplication = (Button) findViewById(R.id.btn_multiplication);
        mBtnDivision = (Button) findViewById(R.id.btn_division);
        mBtnEqual = (Button) findViewById(R.id.btn_equal);

        mBtnCE = (Button) findViewById(R.id.btn_ce);
        mBtnC = (Button) findViewById(R.id.btn_c);
        mBtnSymOpen = (Button) findViewById(R.id.btn_sym_open);
        mBtnSymClose = (Button) findViewById(R.id.btn_sym_close);
        mBtnSymDot = (Button) findViewById(R.id.btn_sym_dot);

        mBtnNum0.setOnClickListener(this);
        mBtnNum1.setOnClickListener(this);
        mBtnNum2.setOnClickListener(this);
        mBtnNum3.setOnClickListener(this);
        mBtnNum4.setOnClickListener(this);
        mBtnNum5.setOnClickListener(this);
        mBtnNum6.setOnClickListener(this);
        mBtnNum7.setOnClickListener(this);
        mBtnNum8.setOnClickListener(this);
        mBtnNum9.setOnClickListener(this);

        mBtnAdd.setOnClickListener(this);
        mBtnSubtract.setOnClickListener(this);
        mBtnMultiplication.setOnClickListener(this);
        mBtnDivision.setOnClickListener(this);
        mBtnEqual.setOnClickListener(this);

        mBtnCE.setOnClickListener(this);
        mBtnC.setOnClickListener(this);
        mBtnSymOpen.setOnClickListener(this);
        mBtnSymClose.setOnClickListener(this);
        mBtnSymDot.setOnClickListener(this);

    }

    /*
    * read more: http://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
    * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_EXPRESSION, mTxtResult.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String expression = savedInstanceState.getString(EXTRA_EXPRESSION);
        if (expression != null) {
            mTxtResult.setText(expression);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_equal:

                break;
            case R.id.btn_ce:
                String oldStr = mTxtResult.getText().toString();
                if (oldStr.length() > 0) {
                    oldStr = oldStr.substring(0, oldStr.length() - 1);
                    mTxtResult.setText(oldStr);
                }
                break;
            case R.id.btn_c:
                mTxtResult.setText("");
                break;

            default:
                if (view instanceof Button) {
                    Button btn = (Button) view;
                    String str = btn.getText().toString();
                    mTxtResult.setText(mTxtResult.getText() + str);
                }
                break;
        }
    }
}
