package com.nghiatt.calculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nghiatt.calculator.R;
import com.nghiatt.calculator.model.ModeCalcuItem;
import com.nghiatt.calculator.model.ModeItem;

import java.util.List;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 08/09/2015.
 */
public class CalculateModeAdapter extends BaseAdapter {

    private List<ModeCalcuItem> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public CalculateModeAdapter(Context context, List<ModeCalcuItem> list) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        return getCustomView(position, view, viewGroup,R.layout.layout_view_mode);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent,R.layout.item_mode);
    }

    public View getCustomView(int position, View view, ViewGroup viewGroup, int layoutID) {
        ViewHolder holder=null;
        if(view==null){
            view=mInflater.inflate(layoutID,viewGroup,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        ModeCalcuItem item=mList.get(position);
        holder.txtName.setText(item.getName());

        return view;
    }

    public static class ViewHolder{
        public TextView txtName;
        public ViewHolder(View view){
            txtName=(TextView)view.findViewById(R.id.txt_name);
        }
    }
}
