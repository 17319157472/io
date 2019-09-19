package com.example.qimo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qimo.R;
import com.example.qimo.bean.ListBean;

import java.util.List;

public class ReAdpter extends RecyclerView.Adapter {
    private List<ListBean.DataBean.DatasBean> list;
    private Context context;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public ReAdpter(List<ListBean.DataBean.DatasBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyHolder myHolder= (MyHolder) viewHolder;
        final ListBean.DataBean.DatasBean datasBean = list.get(i);
        myHolder.tv.setText(list.get(i).getSuperChapterName());
        Glide.with(context).load(list.get(i).getEnvelopePic()).into(myHolder.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick!=null){
                    onItemClick.onItemClick(i,datasBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv_list);
            iv=itemView.findViewById(R.id.iv_list);
        }
    }
    public interface OnItemClick{
        void onItemClick(int i, ListBean.DataBean.DatasBean datasBean);
    }
}
