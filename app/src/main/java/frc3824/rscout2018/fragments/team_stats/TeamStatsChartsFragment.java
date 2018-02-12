package frc3824.rscout2018.fragments.team_stats;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import activitystarter.Arg;
import frc3824.rscout2018.R;

/**
 * A fragment for displaying chart showing the performance of a team
 */
public class TeamStatsChartsFragment extends Fragment
{
    @Arg
    int mTeamNumber;

    //region Charts
    //region Radar Charts
    //region Radar Chart for Cubes Pick Up
    private RadarChart mCubesPickUpChart;
    private YAxis mCubesPickUpYAxis;
    private RadarDataSet mCubesPickUpDataset;
    //endregion
    //region Radar Chart for Cubes Placement
    private RadarChart mCubesPlacementChart;
    private YAxis mCubesPlacementYAxis;
    private RadarDataSet mCubesPlacementDataset;
    //endregion
    //endregion
    //region Line Charts
    //region Cubes
    private LineChart mCubesLineChart;
    private YAxis mCubesLineYAxis;
    private LineDataSet mCubesAutoPlaceDataset;
    private LineDataSet mCubesAutoSwitchPlaceDataset;
    private LineDataSet mCubesAutoScalePlaceDataset;
    private LineDataSet mCubesTeleopPlaceDataset;
    private LineDataSet mCubesTeleopNearSwitchPlaceDataset;
    private LineDataSet mCubesTeleopScalePlaceDataset;
    private LineDataSet mCubesTeleopFarSwitchPlaceDataset;
    //endregion
    //region Climb
    private LineChart mClimbLineChart;
    private YAxis mClimbYAxis;
    //endregion
    //endregion
    //endregion

    //region RadioGroups
    private RadioGroup mCubesRadioGroup;
    private RadioGroup mClimbRadioGroup;
    //endregion

    //region Value Formatters
    private ValueFormatter intVF;
    private ValueFormatter floatVF;
    private ValueFormatter percentVF;
    private YAxisValueFormatter intYVF;
    private YAxisValueFormatter floatYVF;
    private YAxisValueFormatter percentYVF;
    //endregion

    public TeamStatsChartsFragment()
    {
        intVF = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int)value);
            }
        };

        floatVF = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%02.2f", value);
            }
        };

        percentVF = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%01.1f%%",value);
            }
        };

        intYVF = new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return String.valueOf((int)value);
            }
        };

        floatYVF = new YAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return String.format("%01.1f", value);
            }
        };

        percentYVF = new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return String.format("%01.1f%%",value);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team_stats_charts, container);

        mCubesPickUpChart = view.findViewById(R.id.cube_pickup_radar_chart);
        mCubesPlacementChart = view.findViewById(R.id.cube_placement_radar_chart);
        mCubesLineChart = view.findViewById(R.id.cube_line_chart);

        return view;
    }

    private void setupChartsFormat()
    {
        //region Radar Charts
        //region Radar Charts for Cubes Pickup
        mCubesPickUpChart.getLegend().setEnabled(false);
        mCubesPickUpChart.setDescription("");
        mCubesPickUpYAxis = mCubesPickUpChart.getYAxis();
        mCubesPickUpYAxis.setShowOnlyMinMax(true);
        mCubesPickUpYAxis.setValueFormatter(intYVF);
        //endregion
        //region Radar Charts for Cubes Placement
        mCubesPlacementChart.getLegend().setEnabled(false);
        mCubesPlacementChart.setDescription("");
        mCubesPlacementYAxis = mCubesPlacementChart.getYAxis();
        mCubesPlacementYAxis.setShowOnlyMinMax(true);
        mCubesPlacementYAxis.setValueFormatter(intYVF);
        //endregion
        //endregion
        //region Line Charts
        //endregion
    }

    private void reload()
    {

    }

}
