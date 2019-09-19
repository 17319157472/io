package com.example.qimo.model;

import android.util.Log;

import com.example.qimo.api.TextService;
import com.example.qimo.bean.TreeBean;
import com.example.qimo.callback.TextCaallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpTextModel implements TextModel{
    private static final String TAG ="ImpTextModel" ;

    @Override
    public void getData(final TextCaallBack textCaallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TextService.treeUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TextService textService = retrofit.create(TextService.class);
        Observable<TreeBean> observable = textService.getTree();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TreeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TreeBean treeBean) {
                        textCaallBack.getTreeSuccsee(treeBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError:"+e.getMessage());
                        textCaallBack.getTreeFail("onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
