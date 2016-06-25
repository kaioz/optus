package com.cocosw.optus.app;

import com.cocosw.framework.debug.DebugActivityLifeCycle;
import com.cocosw.framework.debug.DebugFragmentLiftCycle;
import com.cocosw.framework.debug.DebugUtils;
import com.cocosw.framework.debug.ViewServerActiviyCycle;
import com.cocosw.optus.service.MockServiceProvider;
import com.readystatesoftware.notificationlog.Log;

import timber.log.Timber;

/**
 * Project: Pactera
 * Created by LiaoKai(soarcn) on 2015/5/9.
 */
class Config implements Runnable {

    private final OptusApplication app;

    public Config(OptusApplication app) {
        this.app = app;
    }

    @Override
    public void run() {
        if (DebugUtils.isViewServerNeeded(app))
            app.registerActivityLifecycle(new ViewServerActiviyCycle());

        app.registerActivityLifecycle(new DebugActivityLifeCycle());
        app.registerFragmentLifecycle(new DebugFragmentLiftCycle());
        DebugUtils.setupStrictMode();
        Timber.plant(new Timber.DebugTree());
        Log.initialize(app, app.getApplicationInfo().icon);
    }
}
