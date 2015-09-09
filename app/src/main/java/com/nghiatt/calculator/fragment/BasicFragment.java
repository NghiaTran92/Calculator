package com.nghiatt.calculator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nghiatt.calculator.HistoryActivity;
import com.nghiatt.calculator.MainActivity;
import com.nghiatt.calculator.MainApplication;
import com.nghiatt.calculator.R;
import com.nghiatt.calculator.model.HistoryItem;
import com.nghiatt.calculator.utils.RoundUtils;
import com.nghiatt.polishnotation.EnumTypeDecimal;
import com.nghiatt.polishnotation.rpn.ReversePolishNotation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 09/09/2015.
 */
public class BasicFragment extends BaseFragment<MainActivity>  implements View.OnClickListener {

    private View mRootView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_basic,container,false);

        mTxtExpression = (TextView) mRootView.findViewById(R.id.txt_expression);

        mTxtResult = (TextView) mRootView.findViewById(R.id.txt_result);
        mTxtResult.setText(R.string.num_0);
        mImgHistory = (ImageView) mRootView.findViewById(R.id.img_history);

        mBtnNum0 = (Button) mRootView.findViewById(R.id.btn_num_0);
        mBtnNum1 = (Button) mRootView.findViewById(R.id.btn_num_1);
        mBtnNum2 = (Button) mRootView.findViewById(R.id.btn_num_2);
        mBtnNum3 = (Button) mRootView.findViewById(R.id.btn_num_3);
        mBtnNum4 = (Button) mRootView.findViewById(R.id.btn_num_4);
        mBtnNum5 = (Button) mRootView.findViewById(R.id.btn_num_5);
        mBtnNum6 = (Button) mRootView.findViewById(R.id.btn_num_6);
        mBtnNum7 = (Button) mRootView.findViewById(R.id.btn_num_7);
        mBtnNum8 = (Button) mRootView.findViewById(R.id.btn_num_8);
        mBtnNum9 = (Button) mRootView.findViewById(R.id.btn_num_9);

        mBtnAdd = (Button) mRootView.findViewById(R.id.btn_add);
        mBtnSubtract = (Button) mRootView.findViewById(R.id.btn_subtract);
        mBtnMultiplication = (Button) mRootView.findViewById(R.id.btn_multiplication);
        mBtnDivision = (Button) mRootView.findViewById(R.id.btn_division);
        mBtnEqual = (Button) mRootView.findViewById(R.id.btn_equal);

        mBtnCE = (Button) mRootView.findViewById(R.id.btn_ce);
        mBtnC = (Button) mRootView.findViewById(R.id.btn_c);
        mBtnSymOpen = (Button) mRootView.findViewById(R.id.btn_sym_open);
        mBtnSymClose = (Button) mRootView.findViewById(R.id.btn_sym_close);
        mBtnSymDot = (Button) mRootView.findViewById(R.id.btn_sym_dot);

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

        updateExpression(getArguments());

        return mRootView;
    }

    public void updateExpression(Bundle bundle) {
        if (bundle != null) {
            HistoryItem historyItem = (HistoryItem) bundle.getSerializable(MainActivity.EXTRA_NAME_UPDATE_EXPRESSION);
            if (historyItem != null) {
                mTxtExpression.setText(historyItem.expression);
                mTxtResult.setText(getString(R.string.sym_equal)+" "+historyItem.result);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void addCharacter(View view) {
        if (view instanceof Button) {
            Button btn = (Button) view;
            String str = btn.getText().toString();
            mTxtExpression.setText(mTxtExpression.getText() + str);
        }
    }

    String resultRound = "";

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.img_history:
                Intent i = new Intent(this.getContext(), HistoryActivity.class);
                startActivity(i);
                this.getMainActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;

            case R.id.btn_equal:
                String expression = mTxtExpression.getText().toString();
                if (mReversePolishNotation == null) {
                    mReversePolishNotation = new ReversePolishNotation(this.getContext(), expression);
                } else if (!expression.equals(mReversePolishNotation.getExpressionOrigin())) {
                    mReversePolishNotation = new ReversePolishNotation(this.getContext(), expression);
                }
                boolean isOk = mReversePolishNotation.convertInfixToPostfix();
                if (isOk) {
                    try {
                        double result = mReversePolishNotation.calculate();
                        resultRound = RoundUtils.round(result);

                        mTxtResult.setText(getString(R.string.sym_equal) + " " + resultRound);
                        HistoryItem historyItem = new HistoryItem();
                        historyItem.date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSS").format(new Date());
                        historyItem.expression = mTxtExpression.getText().toString();
                        historyItem.result = resultRound;
                        historyItem.typeCalcu= EnumTypeDecimal.DECIMAL.getValue();

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
                    if ("".equals(oldStr)) {
                        mTxtResult.setText(getString(R.string.num_0));
                    }
                    mTxtExpression.setText(oldStr);
                }
                break;
            case R.id.btn_c:
                mTxtExpression.setText("");
                mTxtResult.setText(R.string.num_0);
                break;

            default:
                addCharacter(view);
                break;
        }
    }
}
