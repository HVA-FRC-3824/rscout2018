package frc3824.rscout2018.views.powered_up;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.powered_up.CubeEvent;
import frc3824.rscout2018.utilities.Constants;

/**
 * Created by frc3824
 */
public class SavableCubes extends View implements View.OnClickListener
{
    static boolean mPickedUp;
    static boolean mFirst = true;
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
    Boolean mAuto = null;
    long mStartTime;
    Button mUndoButton = null;

    TeamMatchData mTeamMatchData;
    ArrayList<CubeEvent> mCubeEvents = null;
    CubeEvent mTempCubeEvent;

    EventSelect mEventSelect;
    PickUpOk mPickUpOk;


    public SavableCubes(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        mFieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field_top_down);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if(sharedPreferences.getBoolean(Constants.Settings.BLUE_LEFT, false))
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            mFieldBitmap = Bitmap.createBitmap(mFieldBitmap, 0, 0, mFieldBitmap.getWidth(), mFieldBitmap.getHeight(), matrix, true);
        }
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
        if (mTeamMatchData != null)
        {
            if (mAuto)
            {
                mCubeEvents = mTeamMatchData.getAutoCubeEvents();
            }
            else
            {
                mCubeEvents = mTeamMatchData.getTeleopCubeEvents();
            }
        }
    }

    public TeamMatchData getData()
    {
        return mTeamMatchData;
    }

    public void setData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
        if(mAuto != null)
        {
            if (mAuto)
            {
                mCubeEvents = mTeamMatchData.getAutoCubeEvents();
            }
            else
            {
                mCubeEvents = mTeamMatchData.getTeleopCubeEvents();
            }
        }
    }

    public void setUndoButton(Button undo)
    {
        mUndoButton = undo;
        mUndoButton.setOnClickListener(this);
    }

    public void start()
    {
        mStartTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mScreenWidth = width;
        mScreenHeight = height;
        mBackgroundBitmap = Bitmap.createScaledBitmap(mFieldBitmap, width, height, false);
        mPickedUpBitmap = Bitmap.createScaledBitmap(mPickedUpBitmap,
                                                    height / 15,
                                                    height / 15,
                                                    false);
        mPlacedBitmap = Bitmap.createScaledBitmap(mPlacedBitmap, height / 15, height / 15, false);
        mDroppedBitmap = Bitmap.createScaledBitmap(mDroppedBitmap, height / 15, height / 15, false);
        mLaunchSuccessBitmap = Bitmap.createScaledBitmap(mLaunchSuccessBitmap,
                                                         height / 15,
                                                         height / 15,
                                                         false);
        mLaunchFailureBitmap = Bitmap.createScaledBitmap(mLaunchFailureBitmap,
                                                         height / 15,
                                                         height / 15,
                                                         false);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mCanvasPaint);

        if (mCubeEvents != null)
        {
            for (CubeEvent event : mCubeEvents)
            {
                float x = event.getLocationX() * mScreenWidth;
                float y = event.getLocationY() * mScreenHeight;
                switch (event.getEvent())
                {
                    case Constants.MatchScouting.CubeEvents.PICK_UP:
                        canvas.drawBitmap(mPickedUpBitmap,
                                          x - mPickedUpBitmap.getWidth() / 2,
                                          y - mPickedUpBitmap.getHeight() / 2,
                                          mCanvasPaint);
                        break;
                    case Constants.MatchScouting.CubeEvents.PLACED:
                        canvas.drawBitmap(mPlacedBitmap,
                                          x - mPlacedBitmap.getWidth() / 2,
                                          y - mPlacedBitmap.getHeight() / 2,
                                          mCanvasPaint);
                        break;
                    case Constants.MatchScouting.CubeEvents.DROPPED:
                        canvas.drawBitmap(mDroppedBitmap,
                                          x - mDroppedBitmap.getWidth() / 2,
                                          y - mDroppedBitmap.getHeight() / 2,
                                          mCanvasPaint);
                        break;
                    case Constants.MatchScouting.CubeEvents.LAUNCH_SUCCESS:
                        canvas.drawBitmap(mLaunchSuccessBitmap,
                                          x - mLaunchSuccessBitmap.getWidth() / 2,
                                          y - mLaunchFailureBitmap.getHeight() / 2,
                                          mCanvasPaint);
                        break;
                    case Constants.MatchScouting.CubeEvents.LAUNCH_FAILURE:
                        canvas.drawBitmap(mLaunchSuccessBitmap,
                                          x - mLaunchSuccessBitmap.getWidth() / 2,
                                          y - mLaunchSuccessBitmap.getHeight() / 2,
                                          mCanvasPaint);
                        canvas.drawBitmap(mLaunchFailureBitmap,
                                          x - mLaunchFailureBitmap.getWidth() / 2,
                                          y - mLaunchFailureBitmap.getHeight() / 2,
                                          mCanvasPaint);
                        break;
                    default:
                        assert (false);
                }
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

            if (mFirst)
            {
                if (mTeamMatchData.getStartedWithCube())
                {
                    mPickedUp = true;
                }
                else
                {
                    mPickedUp = false;
                }
            }

            mTempCubeEvent = new CubeEvent();
            long time = Calendar.getInstance().getTimeInMillis() - mStartTime;
            mTempCubeEvent.setTime(time);
            mTempCubeEvent.setLocationX(x / (float) mScreenWidth);
            mTempCubeEvent.setLocationY(y / (float) mScreenHeight);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("Event");

            if (mPickedUp)
            {
                builder.setItems(Constants.MatchScouting.CubeEvents.EVENT_OPTIONS,
                                 mEventSelect);
            }
            else
            {
                builder.setItems(new String[]{Constants.MatchScouting.CubeEvents.PICK_UP},
                                 mPickUpOk);
            }

            builder.create().show();
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        // Remove the last cube event
        if (!mCubeEvents.isEmpty())
        {
            mCubeEvents.remove(mCubeEvents.size() - 1);
            if (mCubeEvents.isEmpty())
            {
                mUndoButton.setVisibility(GONE);

                if(mAuto)
                {
                    mFirst = true;
                }
                else // Teleop
                {
                    if(mTeamMatchData.getAutoCubeEvents().isEmpty())
                    {
                        mFirst = true;
                    }
                    else
                    {
                        if(mTeamMatchData.getAutoCubeEvents().get(mTeamMatchData.getAutoCubeEvents().size() - 1).getEvent().equals(Constants.MatchScouting.CubeEvents.PICK_UP))
                        {
                            mPickedUp = false;
                        }
                        else
                        {
                            mPickedUp = true;
                        }
                    }
                }
            }
            else if(mCubeEvents.get(mCubeEvents.size() - 1).equals(Constants.MatchScouting.CubeEvents.PICK_UP))
            {
                mPickedUp = true;
            }
            else
            {
                mPickedUp = false;
            }
            invalidate();
        }
    }

    private class EventSelect implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            mFirst = false;
            mTempCubeEvent.setEvent(Constants.MatchScouting.CubeEvents.EVENT_OPTIONS[which]);
            mCubeEvents.add(mTempCubeEvent);
            mTempCubeEvent = null;
            mUndoButton.setVisibility(VISIBLE);
            mPickedUp = !mPickedUp;
            invalidate();
        }
    }

    private class PickUpOk implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            mFirst = false;
            mTempCubeEvent.setEvent(Constants.MatchScouting.CubeEvents.PICK_UP);
            mCubeEvents.add(mTempCubeEvent);
            mTempCubeEvent = null;
            mUndoButton.setVisibility(VISIBLE);
            mPickedUp = !mPickedUp;
            invalidate();
        }
    }
}
