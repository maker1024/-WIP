package com.anights.wip;

import android.os.Build;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.anights.wip.frame.ActivityFrame;
import com.anightswip.bundlemain.FragmentMobileDataChart;
import com.anightswip.bundlemain.FragmentMobileDataList;
import com.anightswip.bundleplatform.baselayer.dialog.DialogNormal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class ActivityFrameTest {

    @Test
    public void uiFrameTest() throws Exception {

        ActivityFrame activity = Robolectric.setupActivity(ActivityFrame.class);
        TextView titleTv = activity.findViewById(R.id.title);
        assertEquals("Mobile Data Usage",
                titleTv.getText().toString());

        RadioButton mCbChart = activity.findViewById(R.id.cb_chart);
        RadioButton mCbList = activity.findViewById(R.id.cb_list);
        assertTrue(mCbChart.isChecked());
        assertFalse(mCbList.isChecked());
        List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
        assertSame(1, fragments.size());
        assertSame(FragmentMobileDataChart.class, fragments.get(0).getClass());

        mCbList.performClick();
        assertTrue(mCbList.isChecked());
        assertFalse(mCbChart.isChecked());
        List<Fragment> fragments2 = activity.getSupportFragmentManager().getFragments();
        assertSame(2, fragments2.size());
        assertSame(FragmentMobileDataChart.class, fragments2.get(0).getClass());
        assertSame(FragmentMobileDataList.class, fragments2.get(1).getClass());

        activity.onBackPressed();
        DialogNormal dialog = (DialogNormal) ShadowAlertDialog.getLatestDialog();
        assertTrue(dialog.isShowing());
        TextView msg = dialog.findViewById(R.id.dialog_custom_view_id_msg);
        TextView left = dialog.findViewById(R.id.dialog_custom_view_id_leftbtn);
        TextView right = dialog.findViewById(R.id.dialog_custom_view_id_rightbtn);
        assertSame("确定退出App吗？", msg.getText().toString());
        assertSame("确定", left.getText().toString());
        assertSame("取消", right.getText().toString());

        right.performClick();
        assertFalse(dialog.isShowing());

        activity.onBackPressed();
        left.performClick();
        assertTrue(activity.isFinishing());
    }

}
