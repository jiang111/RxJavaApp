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

package com.jiang.android.rxjavaapp.database.helper;


import com.jiang.android.rxjavaapp.database.alloperatorsDao;
import com.jiang.android.rxjavaapp.database.operatorsDao;

/**
 * Created by jiang on 16/3/13.
 */
public class DbUtil {

    private static AllOperatorsService allOperatorsService;
    private static OperatorsService operatorsService;


    private static operatorsDao getOperatorsDao() {
        return DbCore.getDaoSession().getOperatorsDao();
    }

    private static alloperatorsDao getAllOperatorsDao() {
        return DbCore.getDaoSession().getAlloperatorsDao();
    }

    public static AllOperatorsService getAllOperatorsService() {
        if (allOperatorsService == null) {
            allOperatorsService = new AllOperatorsService(getAllOperatorsDao());
        }
        return allOperatorsService;
    }

    public static OperatorsService getOperatorsService() {
        if (operatorsService == null) {
            operatorsService = new OperatorsService(getOperatorsDao());
        }
        return operatorsService;
    }
}
