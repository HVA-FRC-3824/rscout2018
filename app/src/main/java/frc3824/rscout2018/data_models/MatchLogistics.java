package frc3824.rscout2018.data_models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Data model containing the logistics information for a specific match
 */
public class MatchLogistics extends RealmObject
{
    @PrimaryKey
    int matchNumber;
    RealmList<RealmInt> teamNumbers;

    public int getMatchNumber()
    {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
    }

    public int getTeamNumber(int position)
    {
        assert(position >= 0 && position < teamNumbers.size());
        return teamNumbers.get(position).get();
    }

    public ArrayList<Integer> getTeamNumbers()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for(RealmInt i: teamNumbers)
        {
            list.add(i.get());
        }
        return list;
    }

    public void setTeamNumbers(ArrayList<Integer> teamNumbers)
    {
        assert(teamNumbers.size() == 6);
        for(int teamNumber: teamNumbers)
        {
            RealmInt i = new RealmInt();
            i.set(teamNumber);
            this.teamNumbers.add(i);
        }
    }

    public boolean isRed(int teamNumber)
    {
        // todo
        return false;
    }

    public boolean isBlue(int teamNumber)
    {
        // todo
        return false;
    }
}
