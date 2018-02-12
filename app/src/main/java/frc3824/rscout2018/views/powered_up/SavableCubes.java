package frc3824.rscout2018.views.powered_up;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.powered_up.CubeEvent;
import frc3824.rscout2018.utilities.Constants;

/**
 * Created by frc3824
 */
public class SavableCubes extends View
{
    Context mContext;
    Bitmap mFieldBitmap;
    Bitmap mBackgroundBitmap;
    Paint mCanvasPaint;
    int mScreenWidth;
    int mScreenHeight;
    boolean mAuto;
    long mStartTime;
    boolean mPickedUp;

    TeamMatchData mTeamMatchData;


    public SavableCubes(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field_top_down);
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
        mStartTime = -1;
    }

    public void setAuto(boolean auto)
    {
        mAuto = auto;
        if(mAuto)
        {
            mPickedUp = true;
        }
    }

    public TeamMatchData getData()
    {
        return mTeamMatchData;
    }

    public void setData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
    }

    public void start()
    {
        mStartTime = Calendar.getInstance().getTimeInMillis();
    }

    public void stop()
    {
        mStartTime = -1;
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = e.getX();
            float y = e.getY();

            if (mStartTime < 0)
            {
                return false;
            }

            final CubeEvent cubeEvent = new CubeEvent();
            long time = Calendar.getInstance().getTimeInMillis() - mStartTime;
            cubeEvent.setTime(time);
            cubeEvent.setLocationX(x / (float)mScreenWidth);
            cubeEvent.setLocationY(y / (float)mScreenHeight);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("Event")

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    });

            if(mPickedUp)
            {
                builder.setItems(Constants.MatchScouting.CubeEvents.EVENT_OPTIONS,
                                 new DialogInterface.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which)
                                     {
                                         cubeEvent.setEvent(Constants.MatchScouting.CubeEvents.EVENT_OPTIONS[which]);

                                     }
                                 });
            }
            else
            {
                builder.setMessage("Picked Up");
            }

            if (mAuto)
            {
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mTeamMatchData.getAutoCubeEvents().add(cubeEvent);
                        mPickedUp = !mPickedUp;
                    }
                });
            }
            else // Teleop
            {
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mTeamMatchData.getTeleopCubeEvents().add(cubeEvent);
                    }
                });
            }

        }
        return true;
    }
}
