package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.utilities.Constants;

/**
 * Created by frc3824
 */

public class IndividualStartLocationView extends View
{
    Bitmap mFieldBitmap;
    Bitmap mBackgroundBitmap;
    Paint mCanvasPaint;
    Paint mPointPaint;
    int mScreenWidth;
    int mScreenHeight;
    TeamMatchData mTeamMatchData = null;

    public IndividualStartLocationView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        MatchLogistics match  = Database.getInstance().getMatchLogistics(mTeamMatchData.getMatchNumber());
        int position = match.getTeamNumbers().indexOf(mTeamMatchData.getTeamNumber());        if(position < 3) // Blue
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
        if(mTeamMatchData != null)
        {
            invalidate();
        }
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
        if(mTeamMatchData != null)
        {
            canvas.drawPoint((float)mTeamMatchData.getStartLocationX() * mScreenWidth, (float)mTeamMatchData.getStartLocationY() * mScreenHeight, mPointPaint);
        }
    }
}
