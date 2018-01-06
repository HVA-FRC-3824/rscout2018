package frc3824.rscout2018;

import android.databinding.Bindable;

import java.util.ArrayList;

import frc3824.rscout2018.database.data_models.DataModel;

/**
 * Created by andrew on 11/10/17.
 */

public class DataModelTest extends DataModel
{
    //region String
    String testString;

    @Bindable
    public String getTestString()
    {
        return testString;
    }

    public void setTestString(String testString)
    {
        this.testString = testString;
        notifyChange();
    }
    //endregion

    //region Char
    char testChar;

    @Bindable
    public char getTestChar()
    {
        return testChar;
    }

    public void setTestChar(char testChar)
    {
        this.testChar = testChar;
        notifyChange();
    }
    //endregion

    //region Integer
    int testInt;

    @Bindable
    public int getTestInt()
    {
        return testInt;
    }

    public void setTestInt(int testInt)
    {
        this.testInt = testInt;
        notifyChange();
    }
    //endregion

    //region Double
    double testDouble;

    @Bindable
    public double getTestDouble()
    {
        return testDouble;
    }

    public void setTestDouble(double testDouble)
    {
        this.testDouble = testDouble;
        notifyChange();
    }
    //endregion

    //region Float
    float testFloat;

    @Bindable
    public float getTestFloat()
    {
        return testFloat;
    }

    public void setTestFloat(float testFloat)
    {
        this.testFloat = testFloat;
        notifyChange();
    }
    //endregion

    //region List
    ArrayList<String> testList;

    @Bindable
    public ArrayList<String> getTestList()
    {
        return testList;
    }

    public void setTestList(ArrayList<String> testList)
    {
        this.testList = testList;
        notifyChange();
    }
    //endregion

    public DataModelTest()
    {
    }
}
