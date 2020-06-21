package com.anights.wip;

import com.anightswip.bundlemain.db.MainDatabase;
import com.anightswip.bundleplatform.baselayer.BaseApplication;
import com.anightswip.bundleplatform.commonlib.executor.AppExecutors;


public class WipApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MainDatabase.init(this);
        AppExecutors.init();
    }
}
