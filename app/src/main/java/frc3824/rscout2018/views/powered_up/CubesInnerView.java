package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Pair;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.views.heatmap.HeatMap;
import frc3824.rscout2018.views.heatmap.HeatMapMarkerCallback;

/**
 * Created by frc3824
 */
public class CubesInnerView extends HeatMap
{
    Bitmap mFieldBitmap = null;
    Bitmap mBackgroundBitmap;
    Paint mCanvasPaint;
    int mScreenWidth;
    int mScreenHeight;
    ArrayList<Pair<Float, Float>> mData;

    public CubesInnerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field_top_down);
        setMinimum(0);
        setMaximum(100);
        setMarkerCallback(new HeatMapMarkerCallback.CircleHeatMapMarker(0xff9400D3));
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void setData(ArrayList<Pair<Float, Float>> data)
    {
        mData = data;
        new CubesInnerView.UpdateTask().execute();
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

    private class UpdateTask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects)
        {
            if(mData != null)
            {
                clearData();

                for (Pair<Float, Float> pair: mData)
                {
                    addData(new HeatMap.DataPoint(pair.first,
                                                  pair.second,
                                                  100));
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
