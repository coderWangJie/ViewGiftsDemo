package com.viewgiftlibs;

import android.content.Context;

/**
 * View相关工具
 * Created by WangJ on 2017/7/25.
 */

public class ViewUtil {
    /**
     * 获取sp、dp对应的px值
     * @param context 上下文
     * @param dipValue dp、sp的值
     * @return 转换后的px值
     */
    public static float dip2px(Context context, int dipValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return dipValue * scale;
    }
}
