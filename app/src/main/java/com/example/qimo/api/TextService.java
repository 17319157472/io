package com.example.qimo.api;

import com.example.qimo.bean.ListBean;
import com.example.qimo.bean.TreeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface TextService {
    String treeUrl="https://www.wanandroid.com/";
    String listUrl="https://www.wanandroid.com/";

    @GET("project/tree/json")
    Observable<TreeBean> getTree();

    @GET("project/list/{path}/json")
    Observable<ListBean> getList(@Path("path")int path, @Query("cid") String cid);
}
