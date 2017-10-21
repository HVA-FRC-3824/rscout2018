package frc3824.rscout2018.data_models;

import io.realm.RealmObject;

/**
 * @class RealmString
 * @brief Realm wrapped string
 */
public class RealmString extends RealmObject
{
    String str;

    RealmString() {}

    RealmString(String str)
    {
        this.str = str;
    }

    public String get()
    {
        return str;
    }

    public void set(String str)
    {
        this.str = str;
    }
}
