package com.nghiatt.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nghiatt.calculator.model.HistoryItem;
import com.nghiatt.calculator.utils.RoundUtils;
import com.nghiatt.polishnotation.EnumOperater;
import com.nghiatt.polishnotation.rpn.ReversePolishNotation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String EXTRA_EXPRESSION = "Expression";
    public static final String EXTRA_NAME_UPDATE_EXPRESSION="UpdateExpression";

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

    private TextView mTxtExpression;
    private TextView mTxtResult;

    private ImageView mImgHistory;

    private ReversePolishNotation mReversePolishNotation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtExpression = (TextView) findViewById(R.id.txt_expression);
        mTxtResult = (TextView) findViewById(R.id.txt_result);

        mImgHistory=(ImageView)findViewById(R.id.img_history);

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

        mImgHistory.setOnClickListener(this);

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

        updateExpression(getIntent());
    }

    protected void updateExpression(Intent intent){
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            HistoryItem historyItem = (HistoryItem) bundle.getSerializable(MainActivity.EXTRA_NAME_UPDATE_EXPRESSION);
            if (historyItem != null) {
                mTxtExpression.setText(historyItem.expression);
                mTxtResult.setText(getString(R.string.sym_equal) + historyItem.result);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateExpression(intent);
    }

    /*
        * read more: http://inthecheesefactory.com/blog/fragment-state-saving-best-practices/en
        * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_EXPRESSION, mTxtExpression.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String expression = savedInstanceState.getString(EXTRA_EXPRESSION);
        if (expression != null) {
            mTxtExpression.setText(expression);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.img_history:
                Intent i=new Intent(this,HistoryActivity.class);
                startActivity(i);
                break;

            case R.id.btn_equal:
                String expression = mTxtExpression.getText().toString();
                expression = expression.replace(getString(R.string.sym_mul), EnumOperater.MULTIPLICATION.getValue())
                                    .replace(getString(R.string.sym_division), EnumOperater.DIVISION.getValue())
                                    .replace(getString(R.string.sym_sub),EnumOperater.SUBTRACT.getValue())
                                    .replace(getString(R.string.sym_add), EnumOperater.ADD.getValue());
                if (mReversePolishNotation == null) {
                    mReversePolishNotation = new ReversePolishNotation(expression);
                } else if (!expression.equals(mReversePolishNotation.getExpressionOrigin())) {
                    mReversePolishNotation = new ReversePolishNotation(expression);
                }
                boolean isOk = mReversePolishNotation.convertInfixToPostfix();
                if (isOk) {
                    try {
                        double result = mReversePolishNotation.calculate();
                        mTxtResult.setText(getString(R.string.sym_equal)+ result);
                        HistoryItem historyItem=new HistoryItem();
                        historyItem.date=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSS").format(new Date());
                        historyItem.expression=mTxtExpression.getText().toString();
                        historyItem.result=result+"";

                        MainApplication.historyDatabase.insert(historyItem);

                    } catch (Exception e) {
                        e.printStackTrace();
                        mTxtResult.setText(R.string.syntax_error);
                    }
                } else {
                    mTxtResult.setText(R.string.syntax_error);
                }
                break;
            case R.id.btn_ce:
                String oldStr = mTxtExpression.getText().toString();
                if (oldStr.length() > 0) {
                    oldStr = oldStr.substring(0, oldStr.length() - 1);
                    mTxtExpression.setText(oldStr);
                }
                break;
            case R.id.btn_c:
                mTxtExpression.setText("");
                break;

            default:
                if (view instanceof Button) {
                    Button btn = (Button) view;
                    String str = btn.getText().toString();
                    mTxtExpression.setText(mTxtExpression.getText() + str);
                }
                break;
        }
    }
}
