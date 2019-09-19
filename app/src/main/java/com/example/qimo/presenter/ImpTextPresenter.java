package com.example.qimo.presenter;

import com.example.qimo.bean.TreeBean;
import com.example.qimo.callback.TextCaallBack;
import com.example.qimo.model.ImpTextModel;
import com.example.qimo.view.TextView;

public class ImpTextPresenter implements TextPresenter, TextCaallBack {
    private ImpTextModel impTextModel;
    private TextView textView;

    public ImpTextPresenter(TextView textView) {
        impTextModel=new ImpTextModel();
        this.textView = textView;
    }

    @Override
    public void getTree() {
        if (impTextModel!=null){
            impTextModel.getData(this);
        }
    }

    @Override
    public void getTreeSuccsee(TreeBean treeBean) {
        if (textView!=null){
            textView.getTreeSuccsee(treeBean);
        }
    }

    @Override
    public void getTreeFail(String error) {
        if (textView!=null){
            textView.getTreeFail(error);
        }
    }
}
