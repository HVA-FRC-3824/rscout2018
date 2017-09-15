package frc3824.rscout2018.data_models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;


public class TeamLogistics extends RealmObject
{
    int teamNumber;
    String nickname;
    RealmList<RealmInt> matchNumbers;

    public int getTeamNumber()
    {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public ArrayList<Integer> getMatchNumbers()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for(RealmInt i: matchNumbers)
        {
            list.add(i.get());
        }
        return list;
    }

    public void setMatchNumbers(ArrayList<Integer> matchNumbers)
    {
        assert(matchNumbers.size() == 6);
        for(int matchNumber: matchNumbers)
        {
            RealmInt i = new RealmInt();
            i.set(matchNumber);
            this.matchNumbers.add(i);
        }
    }
}
