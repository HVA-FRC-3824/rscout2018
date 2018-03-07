package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.utilities.Utilities;
import frc3824.rscout2018.views.heatmap.HeatMap;
import frc3824.rscout2018.views.heatmap.HeatMapMarkerCallback;

/**
 * Created by frc3824
 */
public class StartLocationView extends HeatMap
{

    Bitmap mBackgroundBitmap;
    Paint mCanvasPaint;
    int mWidth;
    int mHeight;
    ArrayList<TeamMatchData> mTeamMatchData;

    public StartLocationView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setMinimum(0);
        setMaximum(100);
        setMarkerCallback(new HeatMapMarkerCallback.CircleHeatMapMarker(0xff9400D3));
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public ArrayList<TeamMatchData> getData()
    {
        return mTeamMatchData;
    }

    public void setData(ArrayList<TeamMatchData> teamMatchData)
    {
        mTeamMatchData = teamMatchData;
        new UpdateTask().execute();
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mWidth = width;
        mHeight = height;
        if(mBackgroundBitmap != null)
        {
            mBackgroundBitmap.recycle();
        }
        mBackgroundBitmap = Utilities.decodeSampledBitmapFromResource(getResources(), R.drawable.blue_top_down,
                                                                      mWidth, mHeight);
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        Bitmap temp = Bitmap.createBitmap(mBackgroundBitmap, 0, 0, mBackgroundBitmap.getWidth(), mBackgroundBitmap.getHeight(), matrix, true);
        mBackgroundBitmap.recycle();
        mBackgroundBitmap = temp;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mCanvasPaint);
        super.onDraw(canvas);
    }

    private class UpdateTask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects)
        {
            if(mTeamMatchData != null)
            {
                clearData();

                for (TeamMatchData tmd : mTeamMatchData)
                {
                    if(tmd.getStartLocationX() < 0.5)
                    {
                        addData(new HeatMap.DataPoint((float) tmd.getStartLocationX(),
                                                      (float) tmd.getStartLocationY(),
                                                      100));
                    }
                    else
                    {
                        addData(new HeatMap.DataPoint(1.0f - (float) tmd.getStartLocationX(),
                                                      (float) tmd.getStartLocationY(),
                                                      100));
                    }
                }
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects)
        {
            forceRefresh();
        }

    }
}
