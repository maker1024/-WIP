package com.anightswip.bundleplatform.baselayer.toast;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.anightswip.bundleplatform.R;
import com.anightswip.bundleplatform.baselayer.BaseActivity;

public class ActivityToastTest extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastView.init(this);
        setTheme(R.style.AppTheme);
        TextView tv = new TextView(mContext);
        tv.setId(R.id.img);
        tv.setText("点击弹toast");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.show("success show");
            }
        });
        setContentView(tv);
    }


}
