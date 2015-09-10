package com.nghiatt.calculator.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nghiatt.calculator.MainApplication;
import com.nghiatt.calculator.R;
import com.nghiatt.calculator.model.HistoryItem;
import com.nghiatt.polishnotation.EnumTypeDecimal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngh on 9/6/2015.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> mList;

    private OnViewClickListener mViewListener;
    private Context context;
    private Animation mAnimationRotate90;
    private Animation mAnimationReverseRotate90;
    private Animation mAnimationRightToLeft;
    private Animation mAnimationLeftToRight;
    private LayoutInflater inflater;
    private List<Integer> listDeleteClick;


    public HistoryAdapter(Context context, List<HistoryItem> list) {
        mList = list;
        this.context = context;
        listDeleteClick = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        mAnimationRotate90 = AnimationUtils.loadAnimation(context, R.anim.view_rotate_90);
        mAnimationReverseRotate90 = AnimationUtils.loadAnimation(context, R.anim.view_rotate_reverse_90);
        mAnimationRightToLeft = AnimationUtils.loadAnimation(context, R.anim.right_to_left_view);
        mAnimationLeftToRight = AnimationUtils.loadAnimation(context, R.anim.left_to_right_view);

    }


    public void setOnViewClickListener(OnViewClickListener listener) {
        mViewListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_history, parent, false);
        final ViewHolder viewholder = new ViewHolder(v);

        viewholder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewListener != null) {
                    mViewListener.onViewClick(view, viewholder.getAdapterPosition());
                }
            }
        });
        viewholder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexItemClick = viewholder.getAdapterPosition();
                int findIndex = listDeleteClick.lastIndexOf(indexItemClick);
                if (findIndex >= 0) {
                    listDeleteClick.remove(findIndex);
                    closeDelete(viewholder);
                } else {
                    listDeleteClick.add(indexItemClick);
                    openDelete(viewholder);
                }

                if (mViewListener != null) {
                    mViewListener.onViewClick(view, indexItemClick);
                }
            }
        });
        viewholder.btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexItemClick = viewholder.getAdapterPosition();
                if (mViewListener != null) {
                    int findIndex = listDeleteClick.lastIndexOf(indexItemClick);
                    if (findIndex >= 0) {
                        listDeleteClick.remove(findIndex);
                    }
                    mViewListener.onViewClick(view, indexItemClick);
                }
            }
        });
        return viewholder;
    }

    private void openDelete(ViewHolder viewHolder) {
        viewHolder.imgDelete.startAnimation(mAnimationRotate90);
        viewHolder.btnConfirmDelete.setVisibility(View.VISIBLE);
        viewHolder.btnConfirmDelete.startAnimation(mAnimationRightToLeft);
    }

    private void closeDelete(final ViewHolder viewHolder) {
        viewHolder.imgDelete.startAnimation(mAnimationReverseRotate90);

        mAnimationLeftToRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewHolder.btnConfirmDelete.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        viewHolder.btnConfirmDelete.startAnimation(mAnimationLeftToRight);

    }

    private String getHeader(int type) {
        if (type == EnumTypeDecimal.BINARY.getValue()) {
            return context.getString(R.string.binary);
        } else if (type == EnumTypeDecimal.DECIMAL.getValue()) {
            return context.getString(R.string.decimal);
        } else if (type == EnumTypeDecimal.OCTAL.getValue()) {
            return context.getString(R.string.octal);
        } else { // hexa
            return context.getString(R.string.hexa);
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryItem historyItem = mList.get(position);

        Context context = MainApplication.getApplication();
        holder.btnConfirmDelete.setVisibility(View.GONE);
        holder.textViewExpression.setText(historyItem.expression != null ? historyItem.expression : "");
        holder.textViewResult.setText(historyItem.result != null ? context.getString(R.string.sym_equal) + " " + historyItem.result : "");
        if (holder.header == null) {
            holder.header = getHeader(historyItem.typeCalcu);
        }
        holder.textViewHeader.setText(holder.header);

        int findIndex = listDeleteClick.lastIndexOf(position);
        if (findIndex >= 0) {

            openDelete(holder);
        }
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewExpression;
        public TextView textViewResult;
        public TextView textViewHeader;
        public ImageView imgDelete;
        public Button btnConfirmDelete;
        public View view;
        public String header;

        public ViewHolder(View itemView) {
            super(itemView);
            view = (View) itemView.findViewById(R.id.container);
            textViewExpression = (TextView) itemView.findViewById(R.id.txt_expression);
            textViewResult = (TextView) itemView.findViewById(R.id.txt_result);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
            btnConfirmDelete = (Button) itemView.findViewById(R.id.btn_confirm_delete);
            textViewHeader = (TextView) itemView.findViewById(R.id.txt_header);

        }
    }


    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }
}


