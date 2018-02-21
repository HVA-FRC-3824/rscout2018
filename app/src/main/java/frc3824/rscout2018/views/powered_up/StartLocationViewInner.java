package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.views.heatmap.HeatMap;
import frc3824.rscout2018.views.heatmap.HeatMapMarkerCallback;

/**
 * Created by frc3824
 */
public class StartLocationViewInner extends HeatMap
{

    Bitmap mFieldBitmap;
    Bitmap mBackgroundBitmap;
    Paint mCanvasPaint;
    int mScreenWidth;
    int mScreenHeight;
    ArrayList<TeamMatchData> mTeamMatchData;

    public StartLocationViewInner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_top_down);
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        mFieldBitmap = Bitmap.createBitmap(mFieldBitmap, 0, 0, mFieldBitmap.getWidth(), mFieldBitmap.getHeight(), matrix, true);
        setMinimum(0);
        setMaximum(100);
        setMarkerCallback(new HeatMapMarkerCallback.CircleHeatMapMarker(0xff9400D3));
    }

    public ArrayList<TeamMatchData> getData()
    {
        return mTeamMatchData;
    }

    public void setData(ArrayList<TeamMatchData> teamMatchData)
    {
        mTeamMatchData = teamMatchData;

        clearData();

        for(TeamMatchData tmd : mTeamMatchData)
        {
            addData(new HeatMap.DataPoint((float)tmd.getStartLocationX(), (float)tmd.getStartLocationY(), 100));
        }
        forceRefresh();
        invalidate();
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mScreenWidth = width;
        mScreenHeight = height;
        mBackgroundBitmap = Bitmap.createScaledBitmap(mFieldBitmap, width, height, false);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mCanvasPaint);
        super.onDraw(canvas);
    }
}
