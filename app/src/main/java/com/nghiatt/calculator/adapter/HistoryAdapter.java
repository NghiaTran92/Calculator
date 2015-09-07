package com.nghiatt.calculator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nghiatt.calculator.MainApplication;
import com.nghiatt.calculator.R;
import com.nghiatt.calculator.model.HistoryItem;

import java.util.List;

/**
 * Created by ngh on 9/6/2015.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> mList;
    private OnItemClickListener listener;

    public void setOnItemCLickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public HistoryAdapter(List<HistoryItem> list){
        mList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        final ViewHolder viewholder = new ViewHolder(v);
        viewholder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(viewholder.getAdapterPosition());
                }
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryItem historyItem=mList.get(position);
        Context context= MainApplication.getApplication();
        holder.textViewExpression.setText(historyItem.expression!=null?historyItem.expression:"");
        holder.textViewResult.setText(historyItem.result!=null?context.getString(R.string.sym_equal)+" "+historyItem.result:"");
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewExpression;
        public TextView textViewResult;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=(View)itemView.findViewById(R.id.container);
            textViewExpression = (TextView) itemView.findViewById(R.id.txt_expression);
            textViewResult=(TextView)itemView.findViewById(R.id.txt_result);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}


