package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import frc3824.rscout2018.R;

/**
 * Created by frc3824.
 */

public class ClimbView extends LinearLayout
{
    LineChart mTimeChart;


    PieChart mStatusChart;
    PieChart mMethodChart;

    public ClimbView(Context context,
                     @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_climb, this, true);

        //mTimeChart = findViewById(R.id.)
    }
}
