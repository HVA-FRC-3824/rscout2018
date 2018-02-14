package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    String mText;

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public SavableEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater.inflate(R.layout.savable_edittext, this, true);
        mBinding = SavableEdittextBinding.inflate(inflater, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);
        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        mEditText = findViewById(R.id.edittext);
    }

    public void setText(String text)
    {
        if(mText != text)
        {
            mText = text;
            mEditText.setText(text);
        }
    }

    public String getText()
    {
        mText = mEditText.toString();
        return mText;
    }

    @InverseBindingMethods({
            @InverseBindingMethod(type = String.class,
            attribute = "text",
            method = "getText")
    })
    public class SavableEditTextBindingAdapters {}

}
