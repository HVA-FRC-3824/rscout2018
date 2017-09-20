package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * @class SavableSwitch
 * @brief A savable widget that contains a switch
 */
public class SavableSwitch extends RelativeLayout
{
    Switch mSwitch;

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public SavableSwitch(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.savable_edittext, this, false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);

        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        mSwitch = findViewById(R.id.switch_);
    }

    /**
     * Setter function for the binding
     * @param value
     */
    public void setBool(boolean value)
    {
        mSwitch.setChecked(value);
    }

    /**
     * Getter function for the binding
     * @returns
     */
    public boolean getBool()
    {
        return mSwitch.isChecked();
    }
}
