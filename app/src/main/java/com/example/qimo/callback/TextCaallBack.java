package com.example.qimo.callback;

import com.example.qimo.bean.TreeBean;

public interface TextCaallBack {
    void getTreeSuccsee(TreeBean treeBean);
    void getTreeFail(String error);
}
