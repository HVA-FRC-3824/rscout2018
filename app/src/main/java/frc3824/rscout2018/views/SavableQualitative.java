package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frc3824.rscout2018.R;

/**
 * @author frc3824
 * Created: 8/24/16
 *
 *
 */
public class SavableQualitative extends LinearLayout {

    private final static String TAG = "SavableQualitative";

    private ArrayList<TextView> mTeamLabels;
    private ArrayList<Spinner> mSpinners;
    private ArrayList<String> mTeamNumbers;

    public SavableQualitative(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.savable_qualitative, this);

        // Setup label and get key
        TextView label = (TextView) findViewById(R.id.label);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);
        label.setText(typedArray.getString(R.styleable.SavableView_label));
        typedArray.recycle();

        mTeamLabels = new ArrayList<>();
        mTeamLabels.add((TextView)findViewById(R.id.team1_label));
        mTeamLabels.add((TextView)findViewById(R.id.team2_label));
        mTeamLabels.add((TextView)findViewById(R.id.team3_label));

        mSpinners = new ArrayList<>();
        mSpinners.add((Spinner)findViewById(R.id.team1));
        mSpinners.add((Spinner)findViewById(R.id.team2));
        mSpinners.add((Spinner)findViewById(R.id.team3));

        String values[] = new String[]{"3", "2", "1"};

        for(Spinner s: mSpinners){
            s.setAdapter(new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, values));
        }
    }

    public void setTeams(ArrayList<Integer> teams) {
        ArrayList<String> mTeamNumbers = new ArrayList<>();
        for(int i = 0; i < teams.size(); i++){
            mTeamLabels.get(i).setText(String.valueOf(teams.get(i)));
            mTeamNumbers.add(String.valueOf(teams.get(i)));
        }
    }

    public void setQualitative(ArrayList<String> values) {
        for (int i = 0; i < mTeamNumbers.size(); i++) {
            mSpinners.get(i).setSelection(values.indexOf(mTeamNumbers.get(i)) - 1);
        }
    }

    public ArrayList<String> getQualitative() {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < mTeamNumbers.size(); i++) {
            values.add(String.valueOf(mSpinners.get(i).getSelectedItem()));
        }
        return values;
    }

}
