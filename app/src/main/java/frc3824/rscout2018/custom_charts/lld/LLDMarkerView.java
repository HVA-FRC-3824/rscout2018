package frc3824.rscout2018.custom_charts.lld;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import frc3824.rscout2018.R;


public class LLDMarkerView extends MarkerView
{
    private TextView mTeamNumber;
    private TextView mMax;
    private TextView mMin;
    private TextView mAvg;
    private TextView mStd;

    private int screenWidth;
    private Context mContext;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public LLDMarkerView(Context context)
    {
        super(context, R.layout.marker_lld);

        mTeamNumber = findViewById(R.id.team_number);
        mMax = findViewById(R.id.max);
        mMin = findViewById(R.id.min);
        mAvg = findViewById(R.id.avg);
        mStd = findViewById(R.id.std);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

        mContext = context;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        LLDEntry le = (LLDEntry) e;

        mTeamNumber.setText(String.format("%d", le.getTeamNumber()));
        mMax.setText(String.format(java.util.Locale.US, "Max: %.2f", le.getMax()));
        mMin.setText(String.format(java.util.Locale.US, "Min: %.2f", le.getMin()));
        mAvg.setText(String.format(java.util.Locale.US, "Average: %.2f", le.getAvg()));
        mStd.setText(String.format(java.util.Locale.US, "Std: %.2f", le.getStd()));
    }

    @Override
    public int getXOffset(float xpos)
    {
        if (xpos + getWidth() > screenWidth)
        {
            return (int) (screenWidth - xpos - getWidth() - Utils.convertDpToPixel(16));
        }

        return 0;
    }

    @Override
    public int getYOffset(float ypos)
    {
        return -getHeight();
    }
}
