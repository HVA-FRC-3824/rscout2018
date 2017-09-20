package frc3824.rscout2018.data_models;

import io.realm.RealmObject;

/**
 * @class RealmInt
 * @brief Realm wrapped integer
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
