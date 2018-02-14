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

import java.util.ArrayList;
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
    Bitmap mPickedUpBitmap;
    Bitmap mPlacedBitmap;
    Bitmap mDroppedBitmap;
    Bitmap mLaunchSuccessBitmap;
    Bitmap mLaunchFailureBitmap;
    Paint mCanvasPaint;
    int mScreenWidth;
    int mScreenHeight;
    boolean mAuto;
    long mStartTime;
    static boolean mPickedUp;

    TeamMatchData mTeamMatchData;
    CubeEvent mTempCubeEvent;

    EventSelect mEventSelect;
    PickUpOk mPickUpOk;


    public SavableCubes(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field_top_down);
        mPickedUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.level_up);
        mPlacedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.place);
        mDroppedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.breakable);
        mLaunchSuccessBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cannon);
        mLaunchFailureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
        mCanvasPaint = new Paint(Paint.DITHER_FLAG);
        mStartTime = -1;

        mEventSelect = new EventSelect();
        mPickUpOk = new PickUpOk();
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
        mPickedUpBitmap = Bitmap.createScaledBitmap(mPickedUpBitmap, height / 15, height / 15, false);
        mPlacedBitmap = Bitmap.createScaledBitmap(mPlacedBitmap, height / 15, height / 15, false);
        mDroppedBitmap = Bitmap.createScaledBitmap(mDroppedBitmap, height / 15, height / 15, false);
        mLaunchSuccessBitmap = Bitmap.createScaledBitmap(mLaunchSuccessBitmap, height / 15, height / 15, false);
        mLaunchFailureBitmap = Bitmap.createScaledBitmap(mLaunchFailureBitmap, height / 15, height / 15, false);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mCanvasPaint);

        ArrayList<CubeEvent> events;
        if(mAuto)
        {
            events = mTeamMatchData.getAutoCubeEvents();
        }
        else
        {
            events = mTeamMatchData.getTeleopCubeEvents();
        }

        for(CubeEvent event : events)
        {
            float x = event.getLocationX() * mScreenWidth;
            float y = event.getLocationY() * mScreenHeight;
            switch (event.getEvent())
            {
                case Constants.MatchScouting.CubeEvents.PICK_UP:
                    canvas.drawBitmap(mPickedUpBitmap, x - mPickedUpBitmap.getWidth() / 2, y - mPickedUpBitmap.getHeight() / 2, mCanvasPaint);
                    break;
                case Constants.MatchScouting.CubeEvents.PLACED:
                    canvas.drawBitmap(mPlacedBitmap, x - mPlacedBitmap.getWidth() / 2, y - mPlacedBitmap.getHeight() / 2, mCanvasPaint);
                    break;
                case Constants.MatchScouting.CubeEvents.DROPPED:
                    canvas.drawBitmap(mDroppedBitmap, x - mDroppedBitmap.getWidth() / 2, y - mDroppedBitmap.getHeight() / 2, mCanvasPaint);
                    break;
                case Constants.MatchScouting.CubeEvents.LAUNCH_SUCCESS:
                    canvas.drawBitmap(mLaunchSuccessBitmap, x - mLaunchSuccessBitmap.getWidth() / 2, y - mLaunchFailureBitmap.getHeight() / 2, mCanvasPaint);
                    break;
                case Constants.MatchScouting.CubeEvents.LAUNCH_FAILURE:
                    canvas.drawBitmap(mLaunchSuccessBitmap, x - mLaunchSuccessBitmap.getWidth() / 2, y - mLaunchSuccessBitmap.getHeight() / 2, mCanvasPaint);
                    canvas.drawBitmap(mLaunchFailureBitmap, x - mLaunchFailureBitmap.getWidth() / 2, y - mLaunchFailureBitmap.getHeight() / 2, mCanvasPaint);
                    break;
                default:
                    assert(false);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_UP)
        {
            float x = e.getX();
            float y = e.getY();

            if (mStartTime < 0)
            {
                return false;
            }

            mTempCubeEvent = new CubeEvent();
            long time = Calendar.getInstance().getTimeInMillis() - mStartTime;
            mTempCubeEvent.setTime(time);
            mTempCubeEvent.setLocationX(x / (float)mScreenWidth);
            mTempCubeEvent.setLocationY(y / (float)mScreenHeight);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("Event");

            if(mPickedUp)
            {
                builder.setItems(Constants.MatchScouting.CubeEvents.EVENT_OPTIONS,
                                 mEventSelect);
            }
            else
            {
                builder.setItems(new String[]{Constants.MatchScouting.CubeEvents.PICK_UP}, mPickUpOk);
            }

            builder.create().show();
        }
        return true;
    }

    private class Cancel implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            mTempCubeEvent = null;
        }
    }

    private class EventSelect implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            mTempCubeEvent.setEvent(Constants.MatchScouting.CubeEvents.EVENT_OPTIONS[which]);
            if(mAuto)
            {
                mTeamMatchData.getAutoCubeEvents().add(mTempCubeEvent);
            }
            else
            {
                mTeamMatchData.getTeleopCubeEvents().add(mTempCubeEvent);
            }
            mPickedUp = !mPickedUp;
            invalidate();
        }
    }

    private class PickUpOk implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            mTempCubeEvent.setEvent(Constants.MatchScouting.CubeEvents.PICK_UP);
            if(mAuto)
            {
                mTeamMatchData.getAutoCubeEvents().add(mTempCubeEvent);
            }
            else
            {
                mTeamMatchData.getTeleopCubeEvents().add(mTempCubeEvent);
            }
            mPickedUp = !mPickedUp;
            invalidate();
        }
    }
}
