package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import frc3824.rscout2018.R;

/**
 * A view that can be used to bind a decimal number to a data model
 */
public class SavableNumeric extends LinearLayout
{
    EditText mEditText;
    double mValue;
    double mMin;
    double mMax;

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public SavableNumeric(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.savable_edittext, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);

        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        // Set min and max
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableNumeric);
        mMin = typedArray.getFloat(R.styleable.SavableNumeric_min, Float.MIN_VALUE);
        mMax = typedArray.getFloat(R.styleable.SavableNumeric_max, Float.MAX_VALUE);

        mEditText = findViewById(R.id.edittext);
    }

    /**
     * Setter function for the data binding
     * @param value The value to set for the {@link EditText}
     */
    public void setNumber(double value)
    {
        if(value > mMax)
        {
            mValue = mMax;
        }
        else if(value < mMin)
        {
            mValue = mMin;
        }
        else
        {
            mValue = value;
        }
        mEditText.setText(String.valueOf(mValue));
    }

    /**
     * Getter function for the data binding
     * @returns The text currently in the {@link EditText}
     */
    public double getNumber()
    {
        return mValue;
    }
}
