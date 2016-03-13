/**
 * created by jiang, 16/3/13
 * Copyright (c) 2016, jyuesong@gmail.com All Rights Reserved.
 * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #                                                   #
 */

package com.jiang.android.rxjavaapp;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.jiang.android.rxjavaapp.actiity.MainActivity;
import com.jiang.android.rxjavaapp.common.CommonString;
import com.jiang.android.rxjavaapp.common.OperatorsUrl;
import com.jiang.android.rxjavaapp.common.SPKey;
import com.jiang.android.rxjavaapp.database.alloperators;
import com.jiang.android.rxjavaapp.database.helper.DbUtil;
import com.jiang.android.rxjavaapp.database.operators;
import com.jiang.android.rxjavaapp.utils.L;
import com.jiang.android.rxjavaapp.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jiang on 16/3/13.
 */
public class InitDataService extends IntentService {

    public InitDataService() {
        super("InitDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {

                try {
                    List<operators> lists = getOperatorsData();
                    List<alloperators> alloperatorses = getAllOperators();
                    DbUtil.getOperatorsService().save(lists);
                    DbUtil.getAllOperatorsService().save(alloperatorses);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(InitDataService.this, "数据库初始化成功", Toast.LENGTH_SHORT).show();
                SharePrefUtil.saveBoolean(InitDataService.this, SPKey.FIRST_ENTER, false);
                Intent dialogIntent = new Intent(getBaseContext(), MainActivity.class);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(dialogIntent);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(InitDataService.this, "保存数据失败，请退出重试,错误信息:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                L.e(e.getMessage());
            }

            @Override
            public void onNext(Boolean operatorss) {

            }
        });
    }


    public List<operators> getOperatorsData() {
        List<operators> lists = new ArrayList<>();
        lists.add(new operators(1l, "RxJava介绍", 1l));
        lists.add(new operators(2l, "Creating", 2l));
        lists.add(new operators(3l, "Transforming", 3l));
        lists.add(new operators(4l, "Filtering", 4l));
        lists.add(new operators(5l, "Combining", 5l));
        lists.add(new operators(6l, "Error Handling", 6l));
        lists.add(new operators(7l, "Utility", 7l));
        lists.add(new operators(8l, "Conditional", 8l));
        lists.add(new operators(9l, "Mathematical", 9l));
        lists.add(new operators(10l, "Async", 10l));
        lists.add(new operators(11l, "Connect", 11l));
        lists.add(new operators(12l, "Convert", 12l));
        lists.add(new operators(13l, "Blocking", 13l));
        lists.add(new operators(14l, "String", 14l));
        return lists;
    }

    /**
     * Creating 创建操作 - Create/Defer/From/Just/Start/Repeat/Range
     * Transforming 变换操作 - Buffer/Window/Map/FlatMap/GroupBy/Scan
     * Filtering 过滤操作 - Debounce/Distinct/Filter/Sample/Skip/Take
     * Combining 结合操作 - And/StartWith/Join/Merge/Switch/Zip
     * Error Handling 错误处理 - Catch/Retry
     * Utility 辅助操作 - Delay/Do/ObserveOn/SubscribeOn/Subscribe
     * Conditional 条件和布尔操作 - All/Amb/Contains/SkipUntil/TakeUntil
     * Mathematical 算术和聚合操作 - Average/Concat/Count/Max/Min/Sum/Reduce
     * Async 异步操作 - Start/ToAsync/StartFuture/FromAction/FromCallable/RunAsync
     * Connect 连接操作 - Connect/Publish/RefCount/Replay
     * Convert 转换操作 - ToFuture/ToList/ToIterable/ToMap/toMultiMap
     * Blocking 阻塞操作 - ForEach/First/Last/MostRecent/Next/Single/Latest
     * String 字符串操作 - ByLine/Decode/Encode/From/Join/Split/StringConcat
     */

    public List<alloperators> getAllOperators() {

        List<alloperators> alloperatorses = new ArrayList<>();
        getIntroduceList(alloperatorses);
        return alloperatorses;
    }

    private void getIntroduceList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(1l, "ReactiveX", "什么是Rx，Rx的理念和优势", CommonString.SPLASH_INDEX_URL, OperatorsUrl.INTRODUCE, 1l));
        alloperatorses.add(new alloperators(2l, "Observables", "简要介绍Observable的观察者模型", CommonString.OBSERVABLES, OperatorsUrl.OBSERVABLES, 1l));
        alloperatorses.add(new alloperators(3l, "Single", "一种特殊的只发射单个值的Observable", CommonString.SPLASH_INDEX_URL, OperatorsUrl.SINGLE, 1l));
        alloperatorses.add(new alloperators(4l, "Subject", "Observable和Observer的复合体，也是二者的桥梁", CommonString.SUBJECT, OperatorsUrl.SUBJECT, 1l));
        alloperatorses.add(new alloperators(5l, "Scheduler", "介绍了各种异步任务调度和默认调度器", CommonString.SPLASH_INDEX_URL, OperatorsUrl.SCHEDULE, 1l));

    }


    /**
     *
     *
     * ReactiveX - 什么是Rx，Rx的理念和优势
     Observables - 简要介绍Observable的观察者模型
     Single - 一种特殊的只发射单个值的Observable
     Subject - Observable和Observer的复合体，也是二者的桥梁
     */

}
