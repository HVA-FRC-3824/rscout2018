package frc3824.rscout2018.custom_charts.lld;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarLineChartBase;

public class LLDChart extends BarLineChartBase<LLDData> implements LLDDataProvider
{
    LLDRenderer mRenderer;

    public LLDChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    protected void init()
    {
        super.init();
        mRenderer = new LLDRenderer(this, mAnimator, mViewPortHandler);
        mXAxis.mAxisMinimum = -0.5f;
    }

    public LLDData getLLDData()
    {
        return mData;
    }

}
