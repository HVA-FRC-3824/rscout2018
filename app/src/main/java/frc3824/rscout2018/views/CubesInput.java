package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc3824.rscout2018.R;
import frc3824.rscout2018.utilities.Constants;
import frc3824.rscout2018.database.data_models.powered_up.Cube;

/**
 * Created by Steven on 1/20/2018.
 */
public class CubesInput extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private RadioGroup numCubes;
    private Spinner mLocation;
    private CheckBox mPlaced;
    private CheckBox mDropped;
    private Button add, edit, delete;
    private ArrayList<Cube> mCubes;
    private Context mContext;
    private List<String> mLocationList;

    public CubesInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.savable_cubes_input, this);

        // Setup label and get key
        TextView label = (TextView)findViewById(R.id.label);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);
        label.setText(typedArray.getString(R.styleable.SavableView_label));
        typedArray.recycle();

        mLocation = (Spinner)findViewById(R.id.location);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, Constants.MatchScouting.Custom.Cubes.LOCATIONS);
        mLocation.setAdapter(arrayAdapter);
        mLocation.setOnItemSelectedListener(this);
        mLocationList = Arrays.asList(Constants.MatchScouting.Custom.Cubes.LOCATIONS);

        mCubes = new ArrayList<>();

        numCubes = (RadioGroup)findViewById(R.id.radio_group);
        mPlaced = (CheckBox)findViewById(R.id.placed);
        mDropped = (CheckBox)findViewById(R.id.dropped);

        RadioButton radioButton = new RadioButton(context, attrs);
        radioButton.setId(0);
        numCubes.addView(radioButton);
        numCubes.check(0);

        numCubes.setOnCheckedChangeListener(this);


        add = (Button)findViewById(R.id.add_button);
        add.setOnClickListener(this);
        edit = (Button)findViewById(R.id.edit_button);
        edit.setOnClickListener(this);
        delete = (Button)findViewById(R.id.delete_button);
        delete.setOnClickListener(this);

        mContext = context;
    }

    public void setCubes( ArrayList<Cube> cubeArray)
    {
        if(cubeArray.size() > 0){
            Cube cube = cubeArray.get(0);

            mLocation.setSelection(mLocationList.indexOf(cube.location));
            mPlaced.setChecked(cube.placed);
            mDropped.setChecked(cube.dropped);

            add.setVisibility(GONE);
            edit.setVisibility(VISIBLE);
            delete.setVisibility(VISIBLE);
        }
    }

    public ArrayList<Cube> getCubes()
    {
        return mCubes;
    }


    @Override
    public void onClick(View v) {
        int whichCube = numCubes.getCheckedRadioButtonId();
        Cube cube;
        switch(v.getId()){
            case R.id.edit_button:
                cube = mCubes.get(whichCube);

                cube.location = mLocationList.get(mLocation.getSelectedItemPosition());
                cube.placed = mPlaced.isChecked();
                cube.dropped = mDropped.isChecked();
                if(!(cube.placed || cube.dropped) || cube.placed && cube.dropped){
                    return;
                }
                mCubes.set(whichCube, cube);
                if(whichCube + 1 < mCubes.size()) {
                    cube = mCubes.get(whichCube + 1);

                    mLocation.setSelection(mLocationList.indexOf(cube.location));
                    mPlaced.setChecked(cube.placed);
                    mDropped.setChecked(cube.dropped);
                    numCubes.check(whichCube + 1);
                } else {
                    mLocation.setSelection(0);
                    mPlaced.setChecked(false);
                    mDropped.setChecked(false);
                    numCubes.check(whichCube + 1);
                }

                break;

            case R.id.add_button:
                cube = new Cube();
                cube.location = mLocationList.get(mLocation.getSelectedItemPosition());
                cube.placed = mPlaced.isChecked();
                cube.dropped = mDropped.isChecked();
                if(!(cube.placed || cube.dropped) || cube.placed && cube.dropped){
                    return;
                }
                mCubes.add(cube);

                mLocation.setSelection(0);
                mPlaced.setChecked(false);
                mDropped.setChecked(false);
                RadioButton radioButton = new RadioButton(mContext);
                radioButton.setId(whichCube + 1);
                numCubes.addView(radioButton);
                numCubes.check(whichCube + 1);
                break;
            case R.id.delete_button:
                int lastRadio = mCubes.size();
                mCubes.remove(whichCube);
                numCubes.removeViewAt(lastRadio);
                numCubes.check(whichCube);
                if (whichCube < mCubes.size()) {
                    cube = mCubes.get(whichCube);

                    mLocation.setSelection(mLocationList.indexOf(cube.location));
                    mPlaced.setChecked(cube.placed);
                    mDropped.setChecked(cube.dropped);
                } else {
                    mLocation.setSelection(0);
                    mPlaced.setChecked(false);
                    mDropped.setChecked(false);
                    add.setVisibility(VISIBLE);
                    edit.setVisibility(GONE);
                    if(lastRadio == 1){
                        delete.setVisibility(GONE);
                    }
                }
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == mCubes.size()){
            mLocation.setSelection(0);
            mPlaced.setChecked(false);
            mDropped.setChecked(false);
            add.setVisibility(VISIBLE);
            edit.setVisibility(GONE);
            delete.setVisibility(GONE);
        } else {
            Cube cube = mCubes.get(checkedId);
            mLocation.setSelection(mLocationList.indexOf(cube.location));
            mPlaced.setChecked(cube.placed);
            mDropped.setChecked(cube.dropped);

            add.setVisibility(GONE);
            edit.setVisibility(VISIBLE);
            delete.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPlaced.setEnabled(true);
        mDropped.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
