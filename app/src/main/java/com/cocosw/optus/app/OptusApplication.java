package com.cocosw.optus.app;


import com.cocosw.framework.app.CocoApp;

/**
 * Project: Pactera
 * Created by LiaoKai(soarcn) on 2015/5/10.
 */
public class OptusApplication extends CocoApp {

    @Override
    protected Runnable config() {
        return new Config(this);
    }


}
