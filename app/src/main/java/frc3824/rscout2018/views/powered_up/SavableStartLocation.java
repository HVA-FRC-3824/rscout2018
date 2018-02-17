package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.utilities.Constants;

/**
 * Created by frc3824
 */
public class SavableStartLocation extends View
{
    Context mContext;
    Bitmap mFieldBitmap;
    Bitmap mBackgroundBitmap;
    Paint mCanvasPaint;
    Paint mPointPaint;
    int mScreenWidth;
    int mScreenHeight;

    TeamMatchData mTeamMatchData;

    public SavableStartLocation(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        mContext = context;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int position = Integer.parseInt(sharedPreferences.getString(Constants.Settings.MATCH_SCOUT_POSITION, "-1"));
        if(position < 3) // Blue
        {
            mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_top_down);
            if(sharedPreferences.getBoolean(Constants.Settings.BLUE_LEFT, false))
            {
                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                mFieldBitmap = Bitmap.createBitmap(mFieldBitmap, 0, 0, mFieldBitmap.getWidth(), mFieldBitmap.getHeight(), matrix, true);
            }
        }
        else // Red
        {
            mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_top_down);
            if(sharedPreferences.getBoolean(Constants.Settings.BLUE_LEFT, false))
            {
                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                mFieldBitmap = Bitmap.createBitmap(mFieldBitmap, 0, 0, mFieldBitmap.getWidth(), mFieldBitmap.getHeight(), matrix, true);
            }
        }
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setColor(getResources().getColor(R.color.LightGreen));
        mPointPaint.setStrokeWidth(25);
    }

    public TeamMatchData getData()
    {
        return mTeamMatchData;
    }

    public void setData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
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
        canvas.drawPoint(mTeamMatchData.getStartLocationX() * mScreenWidth, mTeamMatchData.getStartLocationY() * mScreenHeight, mPointPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = e.getX() / (float)mScreenWidth;
            float y = e.getY() / (float)mScreenHeight;

            mTeamMatchData.setStartLocationX(x);
            mTeamMatchData.setStartLocationY(y);
            invalidate();
        }
        return super.onTouchEvent(e);
    }
}
