package frc3824.rscout2018.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.ObservableInt;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import frc3824.rscout2018.R;

/**
 * @class SavableCounter
 * @brief
 */
public class SavableCounter extends LinearLayout implements View.OnClickListener, View.OnLongClickListener
{
    Button mButton;
    Integer mCount;
    int mMax;
    int mMin;

    public SavableCounter(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.savable_counter, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableView);

        // Set label
        TextView label = findViewById(R.id.label);
        label.setText(typedArray.getString(R.styleable.SavableView_label));

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.SavableCounter);
        mMin = typedArray.getInt(R.styleable.SavableCounter_min, Integer.MIN_VALUE);
        mMax = typedArray.getInt(R.styleable.SavableCounter_max, Integer.MAX_VALUE);

        mButton = findViewById(R.id.button);

        mCount = 0;
        mButton.setText(mCount.toString());
    }

    public void setCount(int count)
    {
        mCount = count;
        mButton.setText(mCount.toString());
    }

    public int getCount()
    {
        return mCount;
    }

    @Override
    public void onClick(View view)
    {
        if (mCount < mMax)
        {
            mCount++;
            mButton.setText(mCount.toString());
        }
    }

    @Override
    public boolean onLongClick(View view)
    {
        if(mCount > mMin)
        {
            mCount--;
            mButton.setText(mCount.toString());
        }
        return false;
    }
}
