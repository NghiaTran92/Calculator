package com.nghiatt.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nghiatt.calculator.adapter.HistoryAdapter;
import com.nghiatt.calculator.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tran.thanh.nghia on 04/09/2015.
 */
public class HistoryActivity extends AppCompatActivity {


    private RecyclerView mHistoryRecyclerView;
    private HistoryAdapter mAdapter;
    private List<HistoryItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryRecyclerView=(RecyclerView)findViewById(R.id.list_history);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mHistoryRecyclerView.setLayoutManager(layoutManager);
        mHistoryRecyclerView.setHasFixedSize(true);
        mList=new ArrayList<>();
        List<HistoryItem> historyItemList= MainApplication.historyDatabase.getAllHistory();
        if(historyItemList!=null && historyItemList.size()>0){
            mList.addAll(historyItemList);
        }
        mAdapter=new HistoryAdapter(mList);
        mAdapter.setOnItemCLickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i=new Intent(HistoryActivity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(MainActivity.EXTRA_NAME_UPDATE_EXPRESSION,mList.get(position));
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        mHistoryRecyclerView.setAdapter(mAdapter);
    }
}
