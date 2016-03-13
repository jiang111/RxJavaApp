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
import com.jiang.android.rxjavaapp.base.BaseAppManager;
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
                BaseAppManager.getInstance().clear();
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

    public List<alloperators> getAllOperators() {

        List<alloperators> alloperatorses = new ArrayList<>();
        getIntroduceList(alloperatorses);
        getCreatingList(alloperatorses);
        getTransformList(alloperatorses);
        return alloperatorses;
    }

    private void getIntroduceList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(1l, "ReactiveX", "什么是Rx，Rx的理念和优势", CommonString.SPLASH_INDEX_URL, OperatorsUrl.INTRODUCE, 1l));
        alloperatorses.add(new alloperators(2l, "Observables", "简要介绍Observable的观察者模型", CommonString.OBSERVABLES, OperatorsUrl.OBSERVABLES, 1l));
        alloperatorses.add(new alloperators(3l, "Single", "一种特殊的只发射单个值的Observable", CommonString.SPLASH_INDEX_URL, OperatorsUrl.SINGLE, 1l));
        alloperatorses.add(new alloperators(4l, "Subject", "Observable和Observer的复合体，也是二者的桥梁", CommonString.SUBJECT, OperatorsUrl.SUBJECT, 1l));
        alloperatorses.add(new alloperators(5l, "Scheduler", "介绍了各种异步任务调度和默认调度器", CommonString.SPLASH_INDEX_URL, OperatorsUrl.SCHEDULE, 1l));

    }

    private void getCreatingList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(6l, "just()", "将一个或多个对象转换成发射这个或这些对象的一个Observable", CommonString.JUST, OperatorsUrl.JUST, 2l));
        alloperatorses.add(new alloperators(7l, "from()", "将一个Iterable, 一个Future, 或者一个数组转换成一个Observable", CommonString.FROM, OperatorsUrl.FROM, 2l));
        alloperatorses.add(new alloperators(8l, "repeat()", "创建一个重复发射指定数据或数据序列的Observable", CommonString.REPEAT, OperatorsUrl.REPEAT, 2l));
        alloperatorses.add(new alloperators(9l, "repeatWhen()", "创建一个重复发射指定数据或数据序列的Observable，它依赖于另一个Observable发射的数据", CommonString.REPEAT_WHEN, OperatorsUrl.REPEAT, 2l));
        alloperatorses.add(new alloperators(10l, "create()", "使用一个函数从头创建一个Observable", CommonString.CREATE, OperatorsUrl.CREATE, 2l));
        alloperatorses.add(new alloperators(11l, "defer()", "只有当订阅者订阅才创建Observable；为每个订阅创建一个新的Observable", CommonString.DEFER, OperatorsUrl.DEFER, 2l));
        alloperatorses.add(new alloperators(12l, "range()", "创建一个发射指定范围的整数序列的Observable", CommonString.RANGE, OperatorsUrl.DEFER, 2l));
        alloperatorses.add(new alloperators(13l, "interval()", "创建一个按照给定的时间间隔发射整数序列的Observable", CommonString.INTERVAL, OperatorsUrl.INTERVAL, 2l));
        alloperatorses.add(new alloperators(14l, "timer()", "创建一个按照给定的时间间隔发射整数序列的Observable", CommonString.TIMER, OperatorsUrl.TIMER, 2l));
        alloperatorses.add(new alloperators(15l, "empty()", "创建一个什么都不做直接通知完成的Observable", CommonString.EMPTY, OperatorsUrl.EMPTY, 2l));
        alloperatorses.add(new alloperators(16l, "error()", "创建一个什么都不做直接通知错误的Observable", CommonString.EMPTY, OperatorsUrl.EMPTY, 2l));
        alloperatorses.add(new alloperators(17l, "never()", "创建一个不发射任何数据的Observable", CommonString.EMPTY, OperatorsUrl.EMPTY, 2l));

    }

    private void getTransformList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(18l, "map()", "对序列的每一项都应用一个函数来变换Observable发射的数据序列", CommonString.MAP, OperatorsUrl.MAP, 3l));
        alloperatorses.add(new alloperators(19l, "flatMap()", "将Observable发射的数据集合变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable", CommonString.FLATMAP, OperatorsUrl.FLATMAP, 3l));
        alloperatorses.add(new alloperators(20l, "concatMap()", "将Observable发射的数据集合变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable", CommonString.CONTACTMAP, OperatorsUrl.CONTACTMAP, 3l));
        alloperatorses.add(new alloperators(21l, "switchMap()", "将Observable发射的数据集合变换为Observables集合，然后只发射这些Observables最近发射的数据", CommonString.SWITCHMAP, OperatorsUrl.SWITCHMAP, 3l));
        alloperatorses.add(new alloperators(22l, "scan()", "对Observable发射的每一项数据应用一个函数，然后按顺序依次发射每一个值", CommonString.SCAN, OperatorsUrl.SCAN, 3l));
        alloperatorses.add(new alloperators(23l, "groupBy()", "将Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据", CommonString.GROUPBY, OperatorsUrl.GROUPBY, 3l));
        alloperatorses.add(new alloperators(24l, "buffer()", "它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个", CommonString.BUFFER, OperatorsUrl.BUFFER, 3l));
        alloperatorses.add(new alloperators(25l, "window()", "定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项", CommonString.WINDOW, OperatorsUrl.WINDOW, 3l));
        alloperatorses.add(new alloperators(26l, "cast()", "在发射之前强制将Observable发射的所有数据转换为指定类型", CommonString.CAST, OperatorsUrl.CAST, 3l));

    }

}
