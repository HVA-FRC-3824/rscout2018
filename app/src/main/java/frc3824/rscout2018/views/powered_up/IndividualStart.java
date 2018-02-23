package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.TeamMatchData;

/**
 * Created by frc3824
 */
public class IndividualStart extends LinearLayout
{
    TeamMatchData mTeamMatchData;
    IndividualStartLocationView mIndividualStartLocationView = null;
    TextView mStartedWithCube;


    public IndividualStart(Context context,
                           @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_individual_start, this, true);

        mIndividualStartLocationView = findViewById(R.id.location);
        mStartedWithCube = findViewById(R.id.cube);

        if(mTeamMatchData != null)
        {
            new UpdateTask().execute();
        }
    }

    public void setTeamMatchData(TeamMatchData teamMatchData)
    {
        mTeamMatchData = teamMatchData;
        if(mIndividualStartLocationView != null)
        {
            new UpdateTask().execute();
        }
    }

    public class UpdateTask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects)
        {
            if(mTeamMatchData != null)
            {
                mIndividualStartLocationView.setData(mTeamMatchData);

                if(mTeamMatchData.getStartedWithCube())
                {
                    mStartedWithCube.setTextColor(Color.GREEN);
                }
                else
                {
                    mStartedWithCube.setTextColor(Color.RED);
                }

            }
            return null;
        }
    }

}
