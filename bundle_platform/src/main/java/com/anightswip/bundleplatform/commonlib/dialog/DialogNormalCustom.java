package com.anightswip.bundleplatform.commonlib.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.anightswip.bundleplatform.R;

/**
 * 快速自定义普通对话框
 *
 * 通过contentView()方法传入dialog的布局，布局里控件ID使用预定的，如下：
 * R.id.dialog_custom_view_id_msg：      对话框的消息
 * R.id.dialog_custom_view_id_leftbtn：  对话框的左边按钮
 * R.id.dialog_custom_view_id_rightbtn： 对话框的右边按钮
 * R.id.dialog_custom_view_id_lr_divide：对话框的左后按钮分隔符
 * R.id.dialog_custom_view_id_title：    对话框的标题
 * R.id.dialog_custom_view_id_close：    对话框的关闭按钮
 * 以上都是非必须，如果这样做的话，对话框是个空view，只有蒙层显示
 */
public class DialogNormalCustom extends DialogBaseCommon {

    private String mTitle;
    private CharSequence mMsg;
    private String mLeftStr;
    private String mRightStr;
    private IDialogClickedListener mLeftListener;
    private IDialogClickedListener mRightListener;
    private IDialogClickedListener mCloseListener;
    private View mRoot;
    private float mMarginLR;

    public DialogNormalCustom(@NonNull Context context) {
        super(context);
    }

    public DialogNormalCustom(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected View buildView() {
        if (mRoot == null) {
            return new View(getContext());
        }
        View msg = mRoot.findViewById(R.id.dialog_custom_view_id_msg);
        View left = mRoot.findViewById(R.id.dialog_custom_view_id_leftbtn);
        View right = mRoot.findViewById(R.id.dialog_custom_view_id_rightbtn);
        View divideLR = mRoot.findViewById(R.id.dialog_custom_view_id_lr_divide);
        View title = mRoot.findViewById(R.id.dialog_custom_view_id_title);
        View close = mRoot.findViewById(R.id.dialog_custom_view_id_close);

        if (title instanceof TextView) {
            ((TextView) title).setText(mTitle);
            if (TextUtils.isEmpty(mTitle)) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
            }
        }

        if (msg instanceof TextView) {
            ((TextView) msg).setText(mMsg);
            if (TextUtils.isEmpty(mMsg)) {
                msg.setVisibility(View.GONE);
            } else {
                msg.setVisibility(View.VISIBLE);
            }
        }

        if (left instanceof TextView) {
            ((TextView) left).setText(mLeftStr);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLeftListener != null) {
                        mLeftListener.onClicked(DialogNormalCustom.this);
                    }
                }
            });
            if (!TextUtils.isEmpty(mLeftStr)) {
                left.setVisibility(View.VISIBLE);
            } else {
                left.setVisibility(View.GONE);
            }
        }

        if (right instanceof TextView) {
            ((TextView) right).setText(mRightStr);
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRightListener != null) {
                        mRightListener.onClicked(DialogNormalCustom.this);
                    }
                }
            });
            if (!TextUtils.isEmpty(mRightStr)) {
                right.setVisibility(View.VISIBLE);
            } else {
                right.setVisibility(View.GONE);
            }
        }

        if (left != null
                && right != null
                && left.getVisibility() == View.VISIBLE
                && right.getVisibility() == View.VISIBLE) {
            divideLR.setVisibility(View.VISIBLE);
        } else {
            divideLR.setVisibility(View.GONE);
        }

        if (close != null) {
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCloseListener != null) {
                        mCloseListener.onClicked(DialogNormalCustom.this);
                    } else {
                        dismiss();
                    }
                }
            });
        }
        return mRoot;
    }

    public DialogNormalCustom title(String title) {
        mTitle = title;
        return this;
    }

    public DialogNormalCustom msg(CharSequence msg) {
        mMsg = msg;
        return this;
    }

    public DialogNormalCustom leftBtn(String text, IDialogClickedListener listener) {
        mLeftStr = text;
        mLeftListener = listener;
        return this;
    }

    public DialogNormalCustom rightBtn(String text, IDialogClickedListener listener) {
        mRightStr = text;
        mRightListener = listener;
        return this;
    }

    public DialogNormalCustom closeBtn(IDialogClickedListener listener) {
        mCloseListener = listener;
        return this;
    }

    public DialogNormalCustom contentView(@NonNull View view) {
        mRoot = view;
        return this;
    }

    public DialogNormalCustom contentView(@LayoutRes int layoutId) {
        mRoot = LayoutInflater.from(getContext()).inflate(layoutId, null);
        return this;
    }

    public DialogNormalCustom marginLR(float marginDipNum) {
        mMarginLR = marginDipNum;
        return this;
    }

    public DialogNormalCustom cancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    @Override
    protected int resetDialogWidth(int displayWidth) {
        return (int) (displayWidth - 2 *
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        mMarginLR,
                        getContext().getResources().getDisplayMetrics()));
    }
}
