package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import frc3824.rscout2018.R;
import frc3824.rscout2018.databinding.SavableAutocompletetextviewBinding;

/**
 * @class SavableAutoCompleteTextView
 * @brief A savable widget that has a label and an AutoCompleteTextView
 */
public class SavableAutoCompleteTextView extends LinearLayout
{
    SavableAutocompletetextviewBinding mBinding;
    AutoCompleteTextView mAutoCompleteTextView;

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public SavableAutoCompleteTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = SavableAutocompletetextviewBinding.inflate(inflater);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);

        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        mAutoCompleteTextView = findViewById(R.id.autocompletetextview);
    }

    /**
     * Setter function for the data binding
     * @param text The text to set for the {@link AutoCompleteTextView}
     */
    public void setText(String text)
    {
        mAutoCompleteTextView.setText(text);
    }

    /**
     * Getter function for the data binding
     * @returns The text currently in the {@link AutoCompleteTextView}
     */
    public String getText()
    {
        return mAutoCompleteTextView.getText().toString();
    }
}
