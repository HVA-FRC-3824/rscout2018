package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Arrays;
import frc3824.rscout2018.R;
import android.widget.LinearLayout;

/**
 * Created by Steven on 1/13/2018.
 */

public class SavableRadioButtons extends LinearLayout {

    private final static String TAG = "CustomRadioButtons";

    private String[] mResourceStrings;
    private RadioGroup mRadioButtons;

    public SavableRadioButtons(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.savable_radio_buttons, this);

        // Setup label and get key
        TextView label = (TextView) findViewById(R.id.label);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);
        label.setText(typedArray.getString(R.styleable.SavableView_label));
        typedArray.recycle();

        // Get RadioButtons
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableRadioButtons);
        int radioValuesId = typedArray.getResourceId(R.styleable.SavableRadioButtons_radio_values, 0);
        mResourceStrings = context.getResources().getStringArray(radioValuesId);

        mRadioButtons = (RadioGroup) findViewById(R.id.radiobuttons);

        for(int i = 0; i < mResourceStrings.length; i++)
        {
            RadioButton radioButton = new RadioButton(context, attrs);
            radioButton.setText(mResourceStrings[i]);
            radioButton.setId(i);
            mRadioButtons.addView(radioButton);
        }
        mRadioButtons.check(0);
    }

    public void setRadioValues(String radio_values)
    {
        int mRadioSelected = Arrays.asList(mResourceStrings).indexOf(radio_values);
        mRadioButtons.check(mRadioSelected);
    }

    public String getRadioValues()
    {
        return mResourceStrings[mRadioButtons.getCheckedRadioButtonId()];
    }
}
