package com.anights.wip.loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.anights.wip.databinding.ActivitySplashBinding;
import com.anights.wip.frame.ActivityFrame;
import com.anightswip.bundleplatform.baselayer.BaseActivity;

/**
 * 启动页
 */
public class ActivitySplash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplash.this, ActivityFrame.class));
                finish();
            }
        }, 2000);
    }
}
