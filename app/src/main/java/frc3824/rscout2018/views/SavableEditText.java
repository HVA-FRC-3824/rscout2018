package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
// import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import frc3824.rscout2018.R;
import frc3824.rscout2018.databinding.SavableEdittextBinding;

/**
 * @class SavableEditText
 * @brief A savable widget that has a label and an EditText
 */
public class SavableEditText extends LinearLayout
{
    SavableEdittextBinding mBinding;
    EditText mEditText;

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public SavableEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = SavableEdittextBinding.inflate(inflater, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);

        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        mEditText = findViewById(R.id.edittext);
    }

    /**
     * Setter function for the data binding
     * @param text The text to set for the {@link EditText}
     */
    public void setText(String text)
    {
        mEditText.setText(text);
    }

    /**
     * Getter function for the data binding
     * @returns The text currently in the {@link EditText}
     */
    public String getText()
    {
        return mEditText.getText().toString();
    }
}
