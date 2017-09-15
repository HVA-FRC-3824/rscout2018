package frc3824.rscout2018.data_models;

import io.realm.RealmObject;

/**
 * Created by andrew on 9/14/17.
 */

public class RealmInt extends RealmObject
{
    private int i;

    public int get()
    {
        return i;
    }

    public void set(int i)
    {
        this.i = i;
    }
}
