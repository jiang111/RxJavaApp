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
import com.jiang.android.rxjavaapp.common.SPKey;
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
                    DbUtil.getOperatorsService().save(lists);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Boolean>() {
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
        lists.add(new operators(0l, "Creating", 1l));
        lists.add(new operators(1l, "Transforming", 2l));
        lists.add(new operators(2l, "Filtering", 3l));
        lists.add(new operators(3l, "Combining", 4l));
        lists.add(new operators(4l, "Error Handling", 5l));
        lists.add(new operators(5l, "Utility", 6l));
        lists.add(new operators(6l, "Conditional", 7l));
        lists.add(new operators(7l, "Mathematical", 8l));
        lists.add(new operators(8l, "Async", 9l));
        lists.add(new operators(9l, "Connect", 10l));
        lists.add(new operators(10l, "Convert", 11l));
        lists.add(new operators(11l, "Blocking", 12l));
        lists.add(new operators(12l, "String", 13l));
        return lists;
    }
    /**
     *
     *
     * Creating 创建操作 - Create/Defer/From/Just/Start/Repeat/Range
     Transforming 变换操作 - Buffer/Window/Map/FlatMap/GroupBy/Scan
     Filtering 过滤操作 - Debounce/Distinct/Filter/Sample/Skip/Take
     Combining 结合操作 - And/StartWith/Join/Merge/Switch/Zip
     Error Handling 错误处理 - Catch/Retry
     Utility 辅助操作 - Delay/Do/ObserveOn/SubscribeOn/Subscribe
     Conditional 条件和布尔操作 - All/Amb/Contains/SkipUntil/TakeUntil
     Mathematical 算术和聚合操作 - Average/Concat/Count/Max/Min/Sum/Reduce
     Async 异步操作 - Start/ToAsync/StartFuture/FromAction/FromCallable/RunAsync
     Connect 连接操作 - Connect/Publish/RefCount/Replay
     Convert 转换操作 - ToFuture/ToList/ToIterable/ToMap/toMultiMap
     Blocking 阻塞操作 - ForEach/First/Last/MostRecent/Next/Single/Latest
     String 字符串操作 - ByLine/Decode/Encode/From/Join/Split/StringConcat
     *
     *
     */
}
