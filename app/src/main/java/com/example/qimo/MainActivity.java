package com.example.qimo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.qimo.adapter.TextPAdapter;
import com.example.qimo.bean.TreeBean;
import com.example.qimo.fragment.ListFragment;
import com.example.qimo.presenter.ImpTextPresenter;
import com.example.qimo.view.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView {


    private TabLayout mTab;
    private ViewPager mVp;
    private List<TreeBean.DataBean> list=new ArrayList<>();
    /**
     * 查看收藏
     */
    private Button mBtnLook;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private TextPAdapter textPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        ImpTextPresenter impTextPresenter = new ImpTextPresenter(this);
        impTextPresenter.getTree();
    }

    private void initView() {

        mTab = (TabLayout) findViewById(R.id.tab);
        mVp = (ViewPager) findViewById(R.id.vp);
        mBtnLook = (Button) findViewById(R.id.btn_look);
        mBtnLook.setOnClickListener(this);
        //集合
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        //创建适配器
        textPAdapter = new TextPAdapter(getSupportFragmentManager(), fragments, titles, this);
        //设置适配器
        mVp.setAdapter(textPAdapter);
        //联动
        mTab.setupWithViewPager(mVp);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_look:
                break;
        }
    }

    @Override
    public void getTreeSuccsee(TreeBean treeBean) {
        List<TreeBean.DataBean> data = treeBean.getData();
        for (int i=0;i<data.size();i++){
            fragments.add(new ListFragment(data.get(i).getId()));
            titles.add(data.get(i).getName());
        }
        textPAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTreeFail(String error) {

    }
}
