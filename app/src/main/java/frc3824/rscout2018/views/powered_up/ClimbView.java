package frc3824.rscout2018.views.powered_up;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc3824.rscout2018.R;
import frc3824.rscout2018.database.data_models.Team;
import frc3824.rscout2018.database.data_models.TeamMatchData;
import frc3824.rscout2018.utilities.Constants;

/**
 * Created by frc3824.
 */
public class ClimbView extends LinearLayout
{
    LineChart mTimeChart;
    PieChart mStatusChart;
    PieChart mMethodChart;

    Team mTeam;
    ArrayList<TeamMatchData> mMatches;

    public ClimbView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_climb, this, true);

        mTimeChart = findViewById(R.id.time);
        mStatusChart = findViewById(R.id.status);
        mMethodChart = findViewById(R.id.method);

        setup();

        if(mTeam != null)
        {
            new UpdateTask().execute();
        }
    }

    public void setTeamNumber(Team team)
    {
        mTeam = team;
        mMatches = new ArrayList<>(mTeam.getMatches().values());
        if(mTimeChart != null)
        {
            new UpdateTask().execute();
        }
    }

    public void setup()
    {
        mStatusChart.setUsePercentValues(true);
        mStatusChart.setDescription("");
        mStatusChart.setDrawHoleEnabled(false);
        mStatusChart.setRotationEnabled(false);
        mStatusChart.setHighlightPerTapEnabled(true);

        mMethodChart.setUsePercentValues(true);
        mMethodChart.setDescription("");
        mMethodChart.setDrawHoleEnabled(false);
        mMethodChart.setRotationEnabled(false);
        mMethodChart.setHighlightPerTapEnabled(true);
    }

    private class UpdateTask extends AsyncTask
    {

        @Override
        protected Object doInBackground(Object[] objects)
        {

            ArrayList<Entry> statusEntries = new ArrayList<>();

            ArrayList<Entry> methodEntries = new ArrayList<>();

            List statusOptions = Arrays.asList(Constants.MatchScouting.Climb.Status.OPTIONS);
            List methodOptions = Arrays.asList(Constants.MatchScouting.Climb.Method.OPTIONS);

            int[] statusOptionsFrequency = new int[statusOptions.size()];
            int[] methodOptionsFrequency = new int[methodOptions.size()];

            for (TeamMatchData tmd : mMatches)
            {
                statusOptionsFrequency[statusOptions.indexOf(tmd.getClimbStatus())]++;
                int methodIndex = methodOptions.indexOf(tmd.getClimbMethod());
                if (methodIndex > -1)
                {
                    methodOptionsFrequency[methodIndex]++;
                }
            }

            for (int i = 0; i < statusOptions.size(); i++)
            {
                statusEntries.add(new Entry(statusOptionsFrequency[i], i));
            }
            PieDataSet statusDataSet = new PieDataSet(statusEntries, "Status");
            statusDataSet.setColors(Constants.TeamStats.Climb.STATUS_COLORS);
            mStatusChart.setData(new PieData(Constants.MatchScouting.Climb.Status.OPTIONS, statusDataSet));


            for (int i = 0; i < methodOptions.size(); i++)
            {
                methodEntries.add(new Entry(methodOptionsFrequency[i], i));
            }
            PieDataSet methodDataSet = new PieDataSet(methodEntries, "Methods");
            methodDataSet.setColors(Constants.TeamStats.Climb.METHOD_COLORS);
            mMethodChart.setData(new PieData(Constants.MatchScouting.Climb.Method.OPTIONS, methodDataSet));

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects)
        {
            mTimeChart.notifyDataSetChanged();
            mTimeChart.invalidate();

            mStatusChart.notifyDataSetChanged();
            mStatusChart.invalidate();

            mMethodChart.notifyDataSetChanged();
            mMethodChart.invalidate();
        }
    }
}
