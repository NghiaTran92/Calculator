package com.nghiatt.calculator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nghiatt.calculator.HistoryActivity;
import com.nghiatt.calculator.MainActivity;
import com.nghiatt.calculator.MainApplication;
import com.nghiatt.calculator.R;
import com.nghiatt.calculator.adapter.CalculateModeAdapter;
import com.nghiatt.calculator.adapter.CalculatorModeAdapter;
import com.nghiatt.calculator.model.HistoryItem;
import com.nghiatt.calculator.model.ModeCalcuItem;
import com.nghiatt.calculator.model.ModeItem;
import com.nghiatt.calculator.utils.RoundUtils;
import com.nghiatt.polishnotation.EnumTypeDecimal;
import com.nghiatt.polishnotation.rpn.ReversePolishNotation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 09/09/2015.
 */
public class ProgrammingFragment extends BaseFragment<MainActivity> implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String BINARY_MODE = "binary";
    private static final String OCTAL_MODE = "octal";
    private static final String HEXA_MODE = "hexa";

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
    private Button mBtnNumA;
    private Button mBtnNumB;
    private Button mBtnNumC;
    private Button mBtnNumD;
    private Button mBtnNumE;
    private Button mBtnNumF;

    private Button mBtnAdd;
    private Button mBtnSubtract;
    private Button mBtnMultiplication;
    private Button mBtnDivision;
    private Button mBtnEqual;
    private Button mBtnCE;
    private Button mBtnC;

    private TextView mTxtExpression;
    private TextView mTxtResult;

    private ImageView mImgHistory;

    private AppCompatSpinner mSpinnerModeCalcu;
    private CalculateModeAdapter mModeAdapter;

    private List<View> mListAllNum;
    private List<ModeCalcuItem> mListModeCalcu;

    private HashMap<String, List<View>> mGroupNum;
    private ReversePolishNotation mReversePolishNotation;

    private int mOldModeSelected = -1;
    private boolean isUserClick = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_programming, container, false);
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
        mBtnNumA = (Button) mRootView.findViewById(R.id.btn_num_A);
        mBtnNumB = (Button) mRootView.findViewById(R.id.btn_num_B);
        mBtnNumC = (Button) mRootView.findViewById(R.id.btn_num_C);
        mBtnNumD = (Button) mRootView.findViewById(R.id.btn_num_D);
        mBtnNumE = (Button) mRootView.findViewById(R.id.btn_num_E);
        mBtnNumF = (Button) mRootView.findViewById(R.id.btn_num_F);

        mBtnAdd = (Button) mRootView.findViewById(R.id.btn_add);
        mBtnSubtract = (Button) mRootView.findViewById(R.id.btn_subtract);
        mBtnMultiplication = (Button) mRootView.findViewById(R.id.btn_multiplication);
        mBtnDivision = (Button) mRootView.findViewById(R.id.btn_division);
        mBtnEqual = (Button) mRootView.findViewById(R.id.btn_equal);

        mBtnCE = (Button) mRootView.findViewById(R.id.btn_ce);
        mBtnC = (Button) mRootView.findViewById(R.id.btn_c);

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
        mBtnNumA.setOnClickListener(this);
        mBtnNumB.setOnClickListener(this);
        mBtnNumC.setOnClickListener(this);
        mBtnNumD.setOnClickListener(this);
        mBtnNumE.setOnClickListener(this);
        mBtnNumF.setOnClickListener(this);

        mBtnAdd.setOnClickListener(this);
        mBtnSubtract.setOnClickListener(this);
        mBtnMultiplication.setOnClickListener(this);
        mBtnDivision.setOnClickListener(this);
        mBtnEqual.setOnClickListener(this);

        mBtnCE.setOnClickListener(this);
        mBtnC.setOnClickListener(this);

        createGroup();

        mSpinnerModeCalcu = (AppCompatSpinner) mRootView.findViewById(R.id.spinner_mode_calcu);
        mListModeCalcu = getListModeCalcu();
        mModeAdapter = new CalculateModeAdapter(this.getContext(), mListModeCalcu);
        mSpinnerModeCalcu.setAdapter(mModeAdapter);
        mSpinnerModeCalcu.setOnItemSelectedListener(this);

        updateExpression(getArguments());

        return mRootView;
    }

    public void updateExpression(Bundle bundle) {
        if (bundle != null) {
            HistoryItem historyItem = (HistoryItem) bundle.getSerializable(MainActivity.EXTRA_NAME_UPDATE_EXPRESSION);
            if (historyItem != null) {
                isUserClick=false;
                for(int i=0;i<mListModeCalcu.size();i++){
                    if(historyItem.typeCalcu==mListModeCalcu.get(i).getTypeDecimal().getValue()){
                        mSpinnerModeCalcu.setSelection(i);
                    }
                }
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

    private List<ModeCalcuItem> getListModeCalcu() {
        ArrayList<ModeCalcuItem> list = new ArrayList<>();
        list.add(new ModeCalcuItem(getString(R.string.binary), EnumTypeDecimal.BINARY));
        list.add(new ModeCalcuItem(getString(R.string.octal), EnumTypeDecimal.OCTAL));
        list.add(new ModeCalcuItem(getString(R.string.hexa), EnumTypeDecimal.HEXA));
        return list;
    }

    private void createGroup() {
        mListAllNum = new ArrayList<>();
        // Group hexa
        mListAllNum.add(mBtnNum0);
        mListAllNum.add(mBtnNum1);
        mListAllNum.add(mBtnNum2);
        mListAllNum.add(mBtnNum3);
        mListAllNum.add(mBtnNum4);
        mListAllNum.add(mBtnNum5);
        mListAllNum.add(mBtnNum6);
        mListAllNum.add(mBtnNum7);
        mListAllNum.add(mBtnNum8);
        mListAllNum.add(mBtnNum9);
        mListAllNum.add(mBtnNumA);
        mListAllNum.add(mBtnNumB);
        mListAllNum.add(mBtnNumC);
        mListAllNum.add(mBtnNumD);
        mListAllNum.add(mBtnNumE);
        mListAllNum.add(mBtnNumF);

        List<View> viewList = new ArrayList<>();
        mGroupNum = new HashMap<>();

        viewList.add(mBtnNum0);
        viewList.add(mBtnNum1);
        mGroupNum.put(BINARY_MODE, viewList);

        viewList = new ArrayList<>();
        viewList.add(mBtnNum0);
        viewList.add(mBtnNum1);
        viewList.add(mBtnNum2);
        viewList.add(mBtnNum3);
        viewList.add(mBtnNum4);
        viewList.add(mBtnNum5);
        viewList.add(mBtnNum6);
        viewList.add(mBtnNum7);
        mGroupNum.put(OCTAL_MODE, viewList);

    }

    private void setEnableByMode(String mode) {
        switch (mode) {

            case BINARY_MODE:
                for (View v : mListAllNum) {
                    v.setEnabled(false);
                }
                for (String key : mGroupNum.keySet()) {
                    if (BINARY_MODE.equals(key)) {
                        for (View v : mGroupNum.get(key)) {
                            v.setEnabled(true);
                        }
                    }
                }
                break;
            case OCTAL_MODE:
                for (View v : mListAllNum) {
                    v.setEnabled(false);
                }
                for (String key : mGroupNum.keySet()) {
                    if (OCTAL_MODE.equals(key)) {
                        for (View v : mGroupNum.get(key)) {
                            v.setEnabled(true);
                        }
                    }
                }
                break;

            // hexa
            default:
                for (View v : mListAllNum) {
                    v.setEnabled(true);
                }
                break;
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
                EnumTypeDecimal typeDecimal = getTypeDecimalSpinner();
                if (mReversePolishNotation == null) {
                    mReversePolishNotation = new ReversePolishNotation(this.getContext(), expression, typeDecimal);
                } else if (!expression.equals(mReversePolishNotation.getExpressionOrigin())) {
                    mReversePolishNotation = new ReversePolishNotation(this.getContext(), expression, typeDecimal);
                }
                boolean isOk = mReversePolishNotation.convertInfixToPostfix();
                if (isOk) {
                    try {
                        double result = mReversePolishNotation.calculate();
                        int resultInt = (int) result;
                        resultRound = Integer.toString(resultInt, typeDecimal.getValue());

                        mTxtResult.setText(getString(R.string.sym_equal) + " " + resultRound);
                        HistoryItem historyItem = new HistoryItem();
                        historyItem.date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSS").format(new Date());
                        historyItem.expression = mTxtExpression.getText().toString();
                        historyItem.result = resultRound;
                        historyItem.typeCalcu = typeDecimal.getValue();

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

    private void addCharacter(View view) {
        if (view instanceof Button) {
            Button btn = (Button) view;
            String str = btn.getText().toString();
            mTxtExpression.setText(mTxtExpression.getText() + str);
        }
    }

    private EnumTypeDecimal getTypeDecimalSpinner() {
        int position = mSpinnerModeCalcu.getSelectedItemPosition();
        return mListModeCalcu.get(position).getTypeDecimal();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (isUserClick) {
            if (mOldModeSelected != position) {
                if (mOldModeSelected != -1) {
                    String value = mTxtResult.getText().toString().replace(getString(R.string.sym_equal), "").replace(" ", "");
                    int resultOldType = Integer.parseInt(value, mListModeCalcu.get(mOldModeSelected).getTypeDecimal().getValue());
                    mTxtResult.setText(Integer.toString(resultOldType, mListModeCalcu.get(position).getTypeDecimal().getValue()));
                }
                mOldModeSelected = position;
                switch (position) {
                    //1: octal mode
                    case 1:
                        setEnableByMode(OCTAL_MODE);
                        break;
                    // 2: hexa mode
                    case 2:
                        setEnableByMode(HEXA_MODE);
                        break;
                    //0: binary mode
                    default:
                        setEnableByMode(BINARY_MODE);
                        break;
                }
                mTxtExpression.setText("");

            }
        }else{
            isUserClick=true;
            mOldModeSelected=position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
