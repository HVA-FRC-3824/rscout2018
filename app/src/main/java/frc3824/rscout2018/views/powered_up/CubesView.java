package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.database.data_models.powered_up.CubeEvent;
import frc3824.rscout2018.utilities.Constants;

/**
 * Created by frc3824
 */
public class CubesView extends ConstraintLayout implements RadioGroup.OnCheckedChangeListener
{
    CubesInnerView mCubes;
    RadioGroup mOption;

    ArrayList<TeamMatchData> mMatches;

    ArrayList<Pair<Float, Float>> mAutoPickUp;
    ArrayList<Pair<Float, Float>> mAutoPlaced;
    ArrayList<Pair<Float, Float>> mAutoDropped;
    ArrayList<Pair<Float, Float>> mAutoLaunchSuccess;
    ArrayList<Pair<Float, Float>> mAutoLaunchFailure;

    ArrayList<Pair<Float, Float>> mTeleopPlaced;
    ArrayList<Pair<Float, Float>> mTeleopPickUp;
    ArrayList<Pair<Float, Float>> mTeleopDropped;
    ArrayList<Pair<Float, Float>> mTeleopLaunchSuccess;
    ArrayList<Pair<Float, Float>> mTeleopLaunchFailure;

    TextView mShortCycleTime;
    TextView mMediumCycleTime;
    TextView mLongCycleTime;

    public CubesView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_cubes, this, true);

        mCubes = findViewById(R.id.locations);

        mOption = findViewById(R.id.options);
        mOption.setOnCheckedChangeListener(this);

        mShortCycleTime = findViewById(R.id.short_cycle);
        mMediumCycleTime = findViewById(R.id.medium_cycle);
        mLongCycleTime = findViewById(R.id.long_cycle);

        if(mMatches != null)
        {
            new UpdateTask().execute();
        }
    }

    public void setTeam(Team team)
    {
        mMatches = new ArrayList<>(team.getMatches().values());
        if(mCubes != null)
        {
            new UpdateTask().execute();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.auto_pickup:
                if(mAutoPickUp != null)
                {
                    mCubes.setData(mAutoPickUp);
                }
                return;
            case R.id.auto_placed:
                if(mAutoPlaced != null)
                {
                    mCubes.setData(mAutoPlaced);
                }
                return;
            case R.id.auto_dropped:
                if(mAutoDropped != null)
                {
                    mCubes.setData(mAutoDropped);
                }
                return;
            case R.id.auto_launch_success:
                if(mAutoLaunchSuccess != null)
                {
                    mCubes.setData(mAutoLaunchSuccess);
                }
                return;
            case R.id.auto_launch_failure:
                if(mAutoLaunchFailure != null)
                {
                    mCubes.setData(mAutoLaunchFailure);
                }
                return;
            case R.id.teleop_pickup:
                if(mTeleopPickUp != null)
                {
                    mCubes.setData(mTeleopPickUp);
                }
                return;
            case R.id.teleop_placed:
                if(mTeleopPlaced != null)
                {
                    mCubes.setData(mTeleopPlaced);
                }
                return;
            case R.id.teleop_dropped:
                if(mTeleopDropped != null)
                {
                    mCubes.setData(mTeleopDropped);
                }
                return;
            case R.id.teleop_launch_success:
                if(mTeleopLaunchSuccess != null)
                {
                    mCubes.setData(mTeleopLaunchSuccess);
                }
                return;
            case R.id.teleop_launch_failure:
                if(mTeleopLaunchFailure != null)
                {
                    mCubes.setData(mTeleopLaunchFailure);
                }
                return;
            default:
                assert(false);
        }
    }

    private class UpdateTask extends AsyncTask
    {
        double shortCycleSum = 0;
        double mediumCycleSum = 0;
        double longCycleSum = 0;
        int shortCycleNum = 0;
        int mediumCycleNum = 0;
        int longCycleNum = 0;

        @Override
        protected Object doInBackground(Object[] objects)
        {
            mAutoPickUp = new ArrayList<>();
            mAutoPlaced = new ArrayList<>();
            mAutoDropped = new ArrayList<>();
            mAutoLaunchSuccess = new ArrayList<>();
            mAutoLaunchFailure = new ArrayList<>();

            mTeleopPickUp = new ArrayList<>();
            mTeleopPlaced = new ArrayList<>();
            mTeleopDropped = new ArrayList<>();
            mTeleopLaunchSuccess = new ArrayList<>();
            mTeleopLaunchFailure = new ArrayList<>();

            for(TeamMatchData tmd : mMatches)
            {
                // Shouldn't ever happen
                if(tmd == null)
                {
                    continue;
                }

                for(CubeEvent cubeEvent : tmd.getAutoCubeEvents())
                {
                    switch (cubeEvent.getEvent())
                    {
                        case Constants.MatchScouting.CubeEvents.PICK_UP:
                            mAutoPickUp.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        case Constants.MatchScouting.CubeEvents.PLACED:
                            mAutoPlaced.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        case Constants.MatchScouting.CubeEvents.DROPPED:
                            mAutoDropped.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        case Constants.MatchScouting.CubeEvents.LAUNCH_SUCCESS:
                            mAutoLaunchSuccess.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        case Constants.MatchScouting.CubeEvents.LAUNCH_FAILURE:
                            mAutoLaunchFailure.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        default:
                            assert(false);
                    }
                }

                CubeEvent previousEvent = null;
                for(CubeEvent cubeEvent : tmd.getTeleopCubeEvents())
                {
                    switch (cubeEvent.getEvent())
                    {
                        case Constants.MatchScouting.CubeEvents.PICK_UP:
                            mTeleopPickUp.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        case Constants.MatchScouting.CubeEvents.PLACED:
                            mTeleopPlaced.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            if(previousEvent != null)
                            {
                                assert(previousEvent.getEvent().equals(Constants.MatchScouting.CubeEvents.PICK_UP));
                                // Convert time to seconds
                                double currentTime = ((double)cubeEvent.getTime()) / 1000.0;
                                double pickupTime = ((double)previousEvent.getTime()) / 1000.0;
                                double distance = Math.sqrt(Math.pow(cubeEvent.getLocationX() - previousEvent.getLocationX(), 2) + Math.pow(cubeEvent.getLocationY() - previousEvent.getLocationY(), 2));
                                if(distance < Constants.TeamStats.Cubes.SHORT_DISTANCE)
                                {
                                    shortCycleSum += currentTime - pickupTime;
                                    shortCycleNum ++;
                                }
                                else if(distance < Constants.TeamStats.Cubes.MEDIUM_DISTANCE)
                                {
                                    mediumCycleSum += currentTime - pickupTime;
                                    mediumCycleNum ++;
                                }
                                else
                                {
                                    longCycleSum += currentTime - pickupTime;
                                    longCycleNum ++;
                                }
                            }
                            break;
                        case Constants.MatchScouting.CubeEvents.DROPPED:
                            mTeleopDropped.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        case Constants.MatchScouting.CubeEvents.LAUNCH_SUCCESS:
                            mTeleopLaunchSuccess.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            if(previousEvent != null)
                            {
                                assert(previousEvent.getEvent().equals(Constants.MatchScouting.CubeEvents.PICK_UP));
                                // Convert time to seconds
                                double currentTime = ((double)cubeEvent.getTime()) / 1000.0;
                                double pickupTime = ((double)previousEvent.getTime()) / 1000.0;
                                double distance = Math.sqrt(Math.pow(cubeEvent.getLocationX() - previousEvent.getLocationX(), 2) + Math.pow(cubeEvent.getLocationY() - previousEvent.getLocationY(), 2));
                                if(distance < Constants.TeamStats.Cubes.SHORT_DISTANCE)
                                {
                                    shortCycleSum += currentTime - pickupTime;
                                    shortCycleNum ++;
                                }
                                else if(distance < Constants.TeamStats.Cubes.MEDIUM_DISTANCE)
                                {
                                    mediumCycleSum += currentTime - pickupTime;
                                    mediumCycleNum ++;
                                }
                                else
                                {
                                    longCycleSum += currentTime - pickupTime;
                                    longCycleNum ++;
                                }
                            }
                            break;
                        case Constants.MatchScouting.CubeEvents.LAUNCH_FAILURE:
                            mTeleopLaunchFailure.add(new Pair<>(cubeEvent.getLocationX(), cubeEvent.getLocationY()));
                            break;
                        default:
                            assert(false);
                    }
                    previousEvent = cubeEvent;
                }
            }

            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects)
        {
            if(shortCycleNum == 0)
            {
                mShortCycleTime.setText("N/A");
            }
            else
            {
                mShortCycleTime.setText(String.format("%.2fs", shortCycleSum / (double)shortCycleNum));
            }

            if(mediumCycleNum == 0)
            {
                mMediumCycleTime.setText("N/A");
            }
            else
            {
                mMediumCycleTime.setText(String.format("%.2fs", mediumCycleSum / (double)mediumCycleNum));
            }

            if(longCycleNum == 0)
            {
                mLongCycleTime.setText("N/A");
            }
            else
            {
                mLongCycleTime.setText(String.format("%.2fs", longCycleSum / (double)longCycleNum));
            }

            onCheckedChanged(mOption, mOption.getCheckedRadioButtonId());
        }
    }
}
