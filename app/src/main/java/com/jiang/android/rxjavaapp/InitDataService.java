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

import de.greenrobot.event.EventBus;
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

    long i = 1l;
    long parentId = 1l;

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
                EventBus.getDefault().post(1);

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
        getFilterList(alloperatorses);
        getCombinList(alloperatorses);
        getErrorList(alloperatorses);
        getUtilityList(alloperatorses);
        getStringList(alloperatorses);
        return alloperatorses;
    }


    private void getStringList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "byLine()", "将一个字符串的Observable转换为一个行序列的Observable，这个Observable将原来的序列当做流处理，然后按换行符分割", CommonString.byLine, OperatorsUrl.byLine, parentId));
        alloperatorses.add(new alloperators(i++, "decode()", "将一个多字节的字符流转换为一个Observable，它按字符边界发射字节数组", CommonString.decode, OperatorsUrl.decode, parentId));
        alloperatorses.add(new alloperators(i++, "encode()", "对一个发射字符串的Observable执行变换操作，变换后的Observable发射一个在原始字符串中表示多字节字符边界的字节数组", CommonString.encode, OperatorsUrl.encode, parentId));
        alloperatorses.add(new alloperators(i++, "from()", "将一个字符流或者Reader转换为一个发射字节数组或者字符串的Observable", CommonString.from_String, OperatorsUrl.from_String, parentId));
        alloperatorses.add(new alloperators(i++, "join()", "将一个发射字符串序列的Observable转换为一个发射单个字符串的Observable，后者用一个指定的字符串连接所有的字符串", CommonString.join, OperatorsUrl.join, parentId));
        alloperatorses.add(new alloperators(i++, "split()", "将一个发射字符串的Observable转换为另一个发射字符串的Observable，后者使用一个指定的正则表达式边界分割前者发射的所有字符串", CommonString.split, OperatorsUrl.split, parentId));
        alloperatorses.add(new alloperators(i++, "stringConcat()", "将一个发射字符串序列的Observable转换为一个发射单个字符串的Observable，后者连接前者发射的所有字符串", CommonString.stringConcat, OperatorsUrl.stringConcat, parentId));
        parentId++;
    }


    private void getUtilityList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "Materialize()", "将Observable转换成一个通知列表convert an Observable into a list of Notifications", CommonString.Materialize, OperatorsUrl.Materialize, parentId));
        alloperatorses.add(new alloperators(i++, "Dematerialize()", "将上面的结果逆转回一个Observable", CommonString.Dematerialize, OperatorsUrl.Dematerialize, parentId));
        alloperatorses.add(new alloperators(i++, "Timestamp()", "给Observable发射的每个数据项添加一个时间戳", CommonString.Timestamp, OperatorsUrl.Timestamp, parentId));
        alloperatorses.add(new alloperators(i++, "Serialize()", "强制Observable按次序发射数据并且要求功能是完好的", CommonString.Serialize, OperatorsUrl.Serialize, parentId));
        alloperatorses.add(new alloperators(i++, "ObserveOn()", "指定观察者观察Observable的调度器", CommonString.ObserveOn, OperatorsUrl.ObserveOn, parentId));
        alloperatorses.add(new alloperators(i++, "SubscribeOn()", "指定Observable执行任务的调度器", CommonString.SubscribeOn, OperatorsUrl.SubscribeOn, parentId));
        alloperatorses.add(new alloperators(i++, "doOnEach()", "注册一个动作，对Observable发射的每个数据项使用", CommonString.doOnEach, OperatorsUrl.doOnEach, parentId));
        alloperatorses.add(new alloperators(i++, "doOnSubscribe()", "注册一个动作，在观察者订阅时使用", CommonString.doOnSubscribe, OperatorsUrl.doOnSubscribe, parentId));
        alloperatorses.add(new alloperators(i++, "doOnUnsubscribe()", "注册一个动作，在观察者取消订阅时使用", CommonString.doOnUnsubscribe, OperatorsUrl.doOnUnsubscribe, parentId));
        alloperatorses.add(new alloperators(i++, "doOnCompleted()", "注册一个动作，对正常完成的Observable使用", CommonString.doOnCompleted, OperatorsUrl.doOnCompleted, parentId));
        alloperatorses.add(new alloperators(i++, "doOnError()", "注册一个动作，对Observable发射的每个数据项使用", CommonString.doOnError, OperatorsUrl.doOnError, parentId));
        alloperatorses.add(new alloperators(i++, "doOnTerminate()", "注册一个动作，对完成的Observable使用，无论是否发生错误", CommonString.doOnTerminate, OperatorsUrl.doOnTerminate, parentId));
        alloperatorses.add(new alloperators(i++, "finallyDo()", "注册一个动作，在Observable完成时使用", CommonString.finallyDo, OperatorsUrl.finallyDo, parentId));
        alloperatorses.add(new alloperators(i++, "Delay()", "延时发射Observable的结果", CommonString.Delay, OperatorsUrl.Delay, parentId));
        alloperatorses.add(new alloperators(i++, "delaySubscription()", "延时处理订阅请求", CommonString.delaySubscription, OperatorsUrl.delaySubscription, parentId));
        alloperatorses.add(new alloperators(i++, "TimeInterval()", "定期发射数据", CommonString.TimeInterval, OperatorsUrl.TimeInterval, parentId));
        alloperatorses.add(new alloperators(i++, "Using()", " 创建一个只在Observable生命周期存在的资源", CommonString.Using, OperatorsUrl.Using, parentId));
        alloperatorses.add(new alloperators(i++, "single()", " 强制返回单个数据，否则抛出异常", CommonString.First, OperatorsUrl.First, parentId));
        alloperatorses.add(new alloperators(i++, "toFuture(), toIterable(), toList()", "将Observable转换为其它对象或数据结构", CommonString.To, OperatorsUrl.To, parentId));
        parentId++;
    }


    private void getErrorList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "onErrorResumeNext()", "指示Observable在遇到错误时发射一个数据序列", CommonString.EMPTY, OperatorsUrl.ERROR, parentId));
        alloperatorses.add(new alloperators(i++, "onErrorReturn()", "指示Observable在遇到错误时发射一个特定的数据", CommonString.EMPTY, OperatorsUrl.ERROR, parentId));
        alloperatorses.add(new alloperators(i++, "onExceptionResumeNext()", "instructs an Observable to continue emitting items after it encounters an exception (but not another variety of throwable)指示Observable遇到错误时继续发射数据", CommonString.EMPTY, OperatorsUrl.ERROR, parentId));
        alloperatorses.add(new alloperators(i++, "retry()", "指示Observable遇到错误时重试", CommonString.RETRY, OperatorsUrl.RETRY, parentId));
        alloperatorses.add(new alloperators(i++, "retryWhen()", "指示Observable遇到错误时，将错误传递给另一个Observable来决定是否要重新给订阅这个Observable", CommonString.RETRYWHEN, OperatorsUrl.RETRY, parentId));
        parentId++;
    }


    private void getCombinList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "startWith()", "在数据序列的开头增加一项数据", CommonString.STARTWITH, OperatorsUrl.STARTWITH, parentId));
        alloperatorses.add(new alloperators(i++, "merge()", "将多个Observable合并为一个", CommonString.MERGE, OperatorsUrl.MERGE, parentId));
        alloperatorses.add(new alloperators(i++, "mergeDelayError()", "合并多个Observables，让没有错误的Observable都完成后再发射错误通知", CommonString.MERGEDELAY, OperatorsUrl.MERGEDELAY, parentId));
        alloperatorses.add(new alloperators(i++, "zip()", "使用一个函数组合多个Observable发射的数据集合，然后再发射这个结果", CommonString.ZIP, OperatorsUrl.ZIP, parentId));
        alloperatorses.add(new alloperators(i++, "and(), then(), and when()", "(rxjava-joins)通过模式和计划组合多个Observables发射的数据集合", CommonString.AND, OperatorsUrl.AND, parentId));
        alloperatorses.add(new alloperators(i++, "combineLatest()", "当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），然后发射这个函数的结果", CommonString.COMBINLASTED, OperatorsUrl.COMBINLASTED, parentId));
        alloperatorses.add(new alloperators(i++, "join() and groupJoin()", "无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射", CommonString.JOIN, OperatorsUrl.JOIN, parentId));
        alloperatorses.add(new alloperators(i++, "switchOnNext()", "将一个发射Observables的Observable转换成另一个Observable，后者发射这些Observables最近发射的数据", CommonString.SWITHONNEXT, OperatorsUrl.SWITHONNEXT, parentId));
        parentId++;
    }

    private void getIntroduceList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "ReactiveX", "什么是Rx，Rx的理念和优势", CommonString.SPLASH_INDEX_URL, OperatorsUrl.INTRODUCE, parentId));
        alloperatorses.add(new alloperators(i++, "Observables", "简要介绍Observable的观察者模型", CommonString.OBSERVABLES, OperatorsUrl.OBSERVABLES, parentId));
        alloperatorses.add(new alloperators(i++, "Single", "一种特殊的只发射单个值的Observable", CommonString.SPLASH_INDEX_URL, OperatorsUrl.SINGLE, parentId));
        alloperatorses.add(new alloperators(i++, "Subject", "Observable和Observer的复合体，也是二者的桥梁", CommonString.SUBJECT, OperatorsUrl.SUBJECT, parentId));
        alloperatorses.add(new alloperators(i++, "Scheduler", "介绍了各种异步任务调度和默认调度器", CommonString.SPLASH_INDEX_URL, OperatorsUrl.SCHEDULE, parentId));
        parentId++;
    }

    private void getCreatingList(List<alloperators> alloperatorses) {

        alloperatorses.add(new alloperators(i++, "just()", "将一个或多个对象转换成发射这个或这些对象的一个Observable", CommonString.JUST, OperatorsUrl.JUST, parentId));
        alloperatorses.add(new alloperators(i++, "from()", "将一个Iterable, 一个Future, 或者一个数组转换成一个Observable", CommonString.FROM, OperatorsUrl.FROM, parentId));
        alloperatorses.add(new alloperators(i++, "repeat()", "创建一个重复发射指定数据或数据序列的Observable", CommonString.REPEAT, OperatorsUrl.REPEAT, parentId));
        alloperatorses.add(new alloperators(i++, "repeatWhen()", "创建一个重复发射指定数据或数据序列的Observable，它依赖于另一个Observable发射的数据", CommonString.REPEAT_WHEN, OperatorsUrl.REPEAT, parentId));
        alloperatorses.add(new alloperators(i++, "create()", "使用一个函数从头创建一个Observable", CommonString.CREATE, OperatorsUrl.CREATE, parentId));
        alloperatorses.add(new alloperators(i++, "defer()", "只有当订阅者订阅才创建Observable；为每个订阅创建一个新的Observable", CommonString.DEFER, OperatorsUrl.DEFER, parentId));
        alloperatorses.add(new alloperators(i++, "range()", "创建一个发射指定范围的整数序列的Observable", CommonString.RANGE, OperatorsUrl.DEFER, parentId));
        alloperatorses.add(new alloperators(i++, "interval()", "创建一个按照给定的时间间隔发射整数序列的Observable", CommonString.INTERVAL, OperatorsUrl.INTERVAL, parentId));
        alloperatorses.add(new alloperators(i++, "timer()", "创建一个按照给定的时间间隔发射整数序列的Observable", CommonString.TIMER, OperatorsUrl.TIMER, parentId));
        alloperatorses.add(new alloperators(i++, "empty()", "创建一个什么都不做直接通知完成的Observable", CommonString.EMPTY, OperatorsUrl.EMPTY, parentId));
        alloperatorses.add(new alloperators(i++, "error()", "创建一个什么都不做直接通知错误的Observable", CommonString.EMPTY, OperatorsUrl.EMPTY, parentId));
        alloperatorses.add(new alloperators(i++, "never()", "创建一个不发射任何数据的Observable", CommonString.EMPTY, OperatorsUrl.EMPTY, parentId));
        parentId++;
    }

    private void getTransformList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "map()", "对序列的每一项都应用一个函数来变换Observable发射的数据序列", CommonString.MAP, OperatorsUrl.MAP, parentId));
        alloperatorses.add(new alloperators(i++, "flatMap()", "将Observable发射的数据集合变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable", CommonString.FLATMAP, OperatorsUrl.FLATMAP, parentId));
        alloperatorses.add(new alloperators(i++, "concatMap()", "将Observable发射的数据集合变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable", CommonString.CONTACTMAP, OperatorsUrl.CONTACTMAP, parentId));
        alloperatorses.add(new alloperators(i++, "switchMap()", "将Observable发射的数据集合变换为Observables集合，然后只发射这些Observables最近发射的数据", CommonString.SWITCHMAP, OperatorsUrl.SWITCHMAP, parentId));
        alloperatorses.add(new alloperators(i++, "scan()", "对Observable发射的每一项数据应用一个函数，然后按顺序依次发射每一个值", CommonString.SCAN, OperatorsUrl.SCAN, parentId));
        alloperatorses.add(new alloperators(i++, "groupBy()", "将Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据", CommonString.GROUPBY, OperatorsUrl.GROUPBY, parentId));
        alloperatorses.add(new alloperators(i++, "buffer()", "它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个", CommonString.BUFFER, OperatorsUrl.BUFFER, parentId));
        alloperatorses.add(new alloperators(i++, "window()", "定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项", CommonString.WINDOW, OperatorsUrl.WINDOW, parentId));
        alloperatorses.add(new alloperators(i++, "cast()", "在发射之前强制将Observable发射的所有数据转换为指定类型", CommonString.CAST, OperatorsUrl.CAST, parentId));
        parentId++;
    }

    private void getFilterList(List<alloperators> alloperatorses) {
        alloperatorses.add(new alloperators(i++, "filter()", "过滤数据", CommonString.FILTER, OperatorsUrl.FILTER, parentId));
        alloperatorses.add(new alloperators(i++, "takeLast()", "只发射最后的N项数据", CommonString.TAKE_LAST, OperatorsUrl.TAKE_LAST, parentId));
        alloperatorses.add(new alloperators(i++, "last()", "只发射最后的一项数据", CommonString.LAST, OperatorsUrl.LAST, parentId));
        alloperatorses.add(new alloperators(i++, "lastOrDefault()", "只发射最后的一项数据，如果Observable为空就发射默认值", CommonString.LAST_OR_DEFAULT, OperatorsUrl.LAST_OR_DEFAULT, parentId));
        alloperatorses.add(new alloperators(i++, "takeLastBuffer()", "将最后的N项数据当做单个数据发射", CommonString.TAKE_LAST_BUFFER, OperatorsUrl.TAKE_LAST_BUFFER, parentId));
        alloperatorses.add(new alloperators(i++, "skip()", "跳过开始的N项数据", CommonString.SKIP, OperatorsUrl.SKIP, parentId));
        alloperatorses.add(new alloperators(i++, "skipLast()", "跳过最后的N项数据", CommonString.SKIP_LAST, OperatorsUrl.SKIP_LAST, parentId));
        alloperatorses.add(new alloperators(i++, "take()", "只发射开始的N项数据", CommonString.TAKE, OperatorsUrl.TAKE, parentId));
        alloperatorses.add(new alloperators(i++, "first() and takeFirst()", "只发射第一项数据，或者满足某种条件的第一项数据", CommonString.FIRST, OperatorsUrl.FIRST, parentId));
        alloperatorses.add(new alloperators(i++, "firstOrDefault()", "只发射第一项数据，如果Observable为空就发射默认值", CommonString.FIRST_DEFAULT, OperatorsUrl.FIRST_DEFAULT, parentId));
        alloperatorses.add(new alloperators(i++, "elementAt()", "发射第N项数据", CommonString.ELEMENT_AT, OperatorsUrl.ELEMENT_AT, parentId));
        alloperatorses.add(new alloperators(i++, "elementAtOrDefault()", "发射第N项数据，如果Observable数据少于N项就发射默认值", CommonString.ELEMENT_DEFAULT, OperatorsUrl.ELEMENT_DEFAULT, parentId));
        alloperatorses.add(new alloperators(i++, "sample() or throttleLast()", "定期发射Observable最近的数据", CommonString.SAMPLE, OperatorsUrl.SAMPLE, parentId));
        alloperatorses.add(new alloperators(i++, "throttleFirst()", "定期发射Observable发射的第一项数据", CommonString.THROLFIRST, OperatorsUrl.THROLFIRST, parentId));
        alloperatorses.add(new alloperators(i++, "throttleWithTimeout() or debounce()", "只有当Observable在指定的时间后还没有发射数据时，才发射一个数据", CommonString.DEBOUND, OperatorsUrl.DEBOUND, parentId));
        alloperatorses.add(new alloperators(i++, "timeout()", "如果在一个指定的时间段后还没发射数据，就发射一个异常", CommonString.TIMEOUT, OperatorsUrl.TIMEOUT, parentId));
        alloperatorses.add(new alloperators(i++, "distinct()", "过滤掉重复数据", CommonString.DISTINCT, OperatorsUrl.DISTINCT, parentId));
        alloperatorses.add(new alloperators(i++, "distinctUntilChanged()", "过滤掉连续重复的数据", CommonString.UNTILCHANGED, OperatorsUrl.UNTILCHANGED, parentId));
        alloperatorses.add(new alloperators(i++, "ofType()", "只发射指定类型的数据", CommonString.OF_TYPE, OperatorsUrl.OF_TYPE, parentId));
        alloperatorses.add(new alloperators(i++, "ignoreElements()", "丢弃所有的正常数据，只发射错误或完成通知", CommonString.IGNORE_ELEMENT, OperatorsUrl.IGNORE_ELEMENT, parentId));
        parentId++;
    }
}
