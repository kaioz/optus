package com.cocosw.optus.service;

import com.cocosw.framework.network.Network;
import com.cocosw.optus.model.Route;

import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Project: Optus
 * Created by LiaoKai(soarcn) on 2016/6/25.
 */
public class OptusService extends Network {

    public Observable<Route[]> getRoute() {
        return Observable.fromCallable(new Callable<Route[]>() {
            @Override
            public Route[] call() throws Exception {
                return Network.request("http://express-it.optusnet.com.au/sample.json", Route[].class);
            }
        });
    }

}
