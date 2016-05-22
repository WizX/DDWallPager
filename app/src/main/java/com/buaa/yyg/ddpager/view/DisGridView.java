package com.buaa.yyg.ddpager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 事件冲突解决
 * Created by yyg on 2016/4/24.
 */
public class DisGridView extends GridView{

    public DisGridView(Context context) {
        super(context);
    }

    public DisGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * View的测量，获得页面的整体尺寸
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //int的最高两位表示模式，最后30位表示大小，Integer.MAX_VALUE >> 2是最大值右移2位，即后30位。
        //由于手机不可能用得到这么大的数据，所有模式用AT_MOST模式
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        //宽度不变，交由父类处理
        super.onMeasure(widthMeasureSpec, height);
    }
}
