package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import frc3824.rscout2018.R;
import frc3824.rscout2018.databinding.SavableCheckboxBinding;

/**
 * @class SavableCheckbox
 * @brief Savable widget with a label and a checkbox
 */
public class SavableCheckbox extends LinearLayout
{
    SavableCheckboxBinding mBinding;
    CheckBox mCheckBox;

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public SavableCheckbox(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = SavableCheckboxBinding.inflate(inflater, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);

        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        mCheckBox = findViewById(R.id.checkbox);
    }

    /**
     * Setter function for data binding
     * @param value The value of the checkbox
     */
    public void setBool(boolean value)
    {
        mCheckBox.setChecked(value);
    }

    /**
     * Getter function for data binding
     * @returns The value of the checkbox
     */
    public boolean getBool()
    {
        return mCheckBox.isChecked();
    }
}
