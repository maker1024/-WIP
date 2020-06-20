package com.anightswip.bundleplatform.commonlib.utils;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Fragment的一些便利方法
 */

public class FragmentChangeUtil {

    /**
     * fragment的切换工具方法
     *
     * @param fm
     * @param containerId                     这个fragment被加载到哪个布局下
     * @param packageNameFragmentAndUniqueTag 要切换到哪个fragment的包名，顺便这个包名也作为标识fragment的唯一tag
     * @param arguments                       fragment的参数
     * @return
     */
    public static Fragment changePage(FragmentManager fm,
                                      @IdRes int containerId,
                                      String packageNameFragmentAndUniqueTag,
                                      @Nullable Bundle arguments) {
        Fragment fragment = fm.findFragmentByTag(packageNameFragmentAndUniqueTag);
        if (fragment == null) {
            try {
                fragment = fm.getFragmentFactory()
                        .instantiate(ClassLoader.getSystemClassLoader(), packageNameFragmentAndUniqueTag);
            } catch (Exception e) {
                Log.e("FragmentChangeUtil", "changePage():invalid Fragment Name");
                e.printStackTrace();
                return null;
            }
        }
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        if (fragment.isAdded()) {
            if (fragment.isHidden()) {
                fm.beginTransaction()
                        .show(fragment)
                        .commit();
                for (Fragment frag : fm.getFragments()) {
                    if (frag != fragment) {
                        fm.beginTransaction()
                                .hide(frag)
                                .commit();
                    }
                }
            } else {
                return fragment;
            }
        } else {
            fm.beginTransaction()
                    .add(containerId, fragment, packageNameFragmentAndUniqueTag)
                    .commit();
            for (Fragment frag : fm.getFragments()) {
                if (frag != fragment) {
                    fm.beginTransaction()
                            .hide(frag)
                            .commit();
                }
            }
        }
        return fragment;
    }
}
