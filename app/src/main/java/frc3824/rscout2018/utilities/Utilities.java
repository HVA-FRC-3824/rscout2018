package frc3824.rscout2018.utilities;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import frc3824.rscout2018.views.SavableEditText;

/**
 * @class Utilities
 */
public class Utilities {
    /**
     *
     * @param activity
     * @param view
     */
    public static void setupUi(final Activity activity, View view){
        // Setup touch listener for non-textbox views to hide the keyboard
        if(!(view instanceof SavableEditText) /*&& !(view instanceof  SavableNumeric)*/ && !(view instanceof EditText)){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        // If layout is a container, iterate over children and seed recursion
        if(view instanceof ViewGroup){
            int childCount = ((ViewGroup)view).getChildCount();
            for(int i = 0; i < childCount; i++){
                View innerView = ((ViewGroup)view).getChildAt(i);
                setupUi(activity, innerView);
            }
        }
    }

    /**
     *
     * @param activity
     */
    private static void hideSoftKeyboard(Activity activity){
        if(activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
