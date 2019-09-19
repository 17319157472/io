package com.example.qimo.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qimo.MainActivity;
import com.example.qimo.R;
import com.example.qimo.adapter.ReAdpter;
import com.example.qimo.api.TextService;
import com.example.qimo.bean.ListBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListFragment extends Fragment implements ReAdpter.OnItemClick {
    private static final String TAG = "ListFragment";
    private int id;
    private View view;
    private RecyclerView mRv;
    private int mPage = 1;
    private List<ListBean.DataBean.DatasBean> list = new ArrayList<>();
    private ReAdpter reAdpter;

    public ListFragment() {
    }

    @SuppressLint("ValidFragment")
    public ListFragment(int id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TextService.listUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TextService textService = retrofit.create(TextService.class);
        String sid = String.valueOf(id);
        Observable<ListBean> observable = textService.getList(mPage, sid);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListBean listBean) {
                        List<ListBean.DataBean.DatasBean> datas = listBean.getData().getDatas();
                        list.addAll(datas);
                        reAdpter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //设置
        mRv.setLayoutManager(manager);
        //创建适配器
        reAdpter = new ReAdpter(list, getContext());
        //设置
        mRv.setAdapter(reAdpter);
        //点击监听
        reAdpter.setOnItemClick(this);
    }

    @Override
    public void onItemClick(int i, ListBean.DataBean.DatasBean datasBean) {
        FragmentActivity activity = getActivity();
        MainActivity mainActivity = (MainActivity) activity;
        FragmentManager manager = mainActivity.getSupportFragmentManager();
        ContentFragment contentFragment = new ContentFragment();
        manager.beginTransaction()
                .replace(R.id.rv, contentFragment)
                .commit();
    }
}
