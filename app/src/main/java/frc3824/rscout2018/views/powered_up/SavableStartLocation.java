package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;

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
        mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field_top_down);
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(5); // todo: determine
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
        if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE || e.getAction() == MotionEvent.ACTION_UP)
        {
            float x = e.getX();
            float y = e.getY();

            mTeamMatchData.setStartLocationX(x / (float)mScreenWidth);
            mTeamMatchData.setStartLocationY(y * (float)mScreenHeight);
            invalidate();
        }
        return true;
    }
}
