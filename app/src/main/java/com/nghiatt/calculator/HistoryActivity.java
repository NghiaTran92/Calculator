package com.nghiatt.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.history);

        mHistoryRecyclerView = (RecyclerView) findViewById(R.id.list_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mHistoryRecyclerView.setLayoutManager(layoutManager);
        mHistoryRecyclerView.setHasFixedSize(true);
        mList = new ArrayList<>();

        mAdapter = new HistoryAdapter(this, mList);
        mAdapter.setOnViewClickListener(new HistoryAdapter.OnViewClickListener() {
            @Override
            public void onViewClick(View view, int position) {
                int id = view.getId();
                switch (id) {
                    case R.id.btn_confirm_delete:
                        deleteHistoryByIndex(position);
                        break;
                    case R.id.container:
                        Intent i = new Intent(HistoryActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(MainActivity.EXTRA_NAME_UPDATE_EXPRESSION, mList.get(position));
                        i.putExtras(bundle);
                        startActivity(i);
                        break;
                    default:

                        break;
                }
            }
        });

        mHistoryRecyclerView.setAdapter(mAdapter);
        mHistoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        updateList();
    }

    private void deleteHistoryByIndex(int index) {
        HistoryItem historyItem = mList.get(index);
        MainApplication.historyDatabase.delete(historyItem);
        mList.remove(index);
        mAdapter.notifyDataSetChanged();
    }

    private void updateList() {
        mList.clear();
        List<HistoryItem> historyItemList = MainApplication.historyDatabase.getAllHistory();
        if (historyItemList != null && historyItemList.size() > 0) {
            mList.addAll(historyItemList);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_history) {
            MainApplication.historyDatabase.deleteAll();
            updateList();
            return true;
        } else if (id == android.R.id.home) {

            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
