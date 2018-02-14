package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import frc3824.rscout2018.R;

/**
 * Created by frc3824
 */
public class SavableClimb extends RelativeLayout implements RadioGroup.OnCheckedChangeListener
{
    Timer mTimer;
    RadioGroup mStatus;
    RadioGroup mMethod;

    public SavableClimb(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.savable_climb, this, true);

        mTimer = findViewById(R.id.timer);
        mTimer.setButtonListener(new ClimbTimerButtonListener());
        mStatus = findViewById(R.id.status);
        mStatus.setOnCheckedChangeListener(this);
        mMethod = findViewById(R.id.method);
        for (int i = 0; i < mMethod.getChildCount(); i++)
        {
            mMethod.getChildAt(i).setEnabled(false);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.no_attempt:
            case R.id.on_platform:
            case R.id.did_not_finish:
            case R.id.fell:
                for (int i = 0; i < mMethod.getChildCount(); i++)
                {
                    mMethod.getChildAt(i).setEnabled(false);
                }
                mMethod.check(-1);
                break;
            case R.id.climb:
                for (int i = 0; i < mMethod.getChildCount(); i++)
                {
                    mMethod.getChildAt(i).setEnabled(true);
                }
                break;
            default:
                assert (false);
        }
    }

    private class ClimbTimerButtonListener implements Timer.ButtonListener
    {

        @Override
        public void onStart()
        {
            mStatus.check(R.id.climb);
            for (int i = 0; i < mStatus.getChildCount(); i++)
            {
                mStatus.getChildAt(i).setEnabled(false);
            }
        }

        @Override
        public void onStop()
        {

        }

        @Override
        public void onReset()
        {
            mStatus.check(-1);
            for (int i = 0; i < mStatus.getChildCount(); i++)
            {
                mStatus.getChildAt(i).setEnabled(true);
            }
            for (int i = 0; i < mMethod.getChildCount(); i++)
            {
                mMethod.getChildAt(i).setEnabled(false);
            }
        }
    }
}
