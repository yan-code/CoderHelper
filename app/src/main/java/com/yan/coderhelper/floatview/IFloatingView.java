package com.yan.coderhelper.floatview;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;

/**
 * Created by Yunpeng Li on 2018/3/15.
 */

public interface IFloatingView {

    FloatingView remove();

    FloatingView add();

    FloatingView attach(Activity activity);

    FloatingView attach(FrameLayout container);

    FloatingView detach(Activity activity);

    FloatingView detach(FrameLayout container);

    EnFloatingView getView();

    FloatingView icon(@DrawableRes int resId);

    FloatingView layoutParams(ViewGroup.LayoutParams params);

    FloatingView listener(MagnetViewListener magnetViewListener);

}
