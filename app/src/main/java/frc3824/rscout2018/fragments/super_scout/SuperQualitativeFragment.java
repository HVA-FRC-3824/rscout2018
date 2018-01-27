package frc3824.rscout2018.fragments.super_scout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import frc3824.rscout2018.database.data_models.MatchLogistics;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.R;
import frc3824.rscout2018.views.SavableQualitative;

/**
 * @author frc3824
 * Created: 8/16/16
 *
 *
 */
public class SuperQualitativeFragment extends Fragment {

    private final static String TAG = "QualitativeFragment";
    
    private int mMatchNumber;
    
    public void setMatchNumber(int match_number)
    {
        mMatchNumber = match_number;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super_qualitative, container, false);

        Database database = Database.getInstance();
        ArrayList<Integer> blue = new ArrayList<>();
        ArrayList<Integer> red = new ArrayList<>();

        if(mMatchNumber > 0) {
            MatchLogistics match = new MatchLogistics(mMatchNumber);
            for (int i = 0; i < 3; i++) {
                blue.add(match.getTeamNumber(i));
            }
            for (int i = 3; i < 6; i++) {
                red.add(match.getTeamNumber(i));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                blue.add(i + 1);
            }
            for (int i = 3; i < 6; i++) {
                red.add(i + 1);
            }
        }
        SavableQualitative blue_speed = view.findViewById(R.id.blue_speed);
        blue_speed.setTeams(blue);

        SavableQualitative blue_intake_ability = view.findViewById(R.id.blue_torque);
        blue_intake_ability.setTeams(blue);
        SavableQualitative blue_control = view.findViewById(R.id.blue_control);
        blue_control.setTeams(blue);
        SavableQualitative blue_defense = view.findViewById(R.id.blue_defense);
        blue_defense.setTeams(blue);

        SavableQualitative red_speed = view.findViewById(R.id.red_speed);
        red_speed.setTeams(red);
        SavableQualitative red_intake_ability = view.findViewById(R.id.red_torque);
        red_intake_ability.setTeams(red);
        SavableQualitative red_control = view.findViewById(R.id.red_control);
        red_control.setTeams(red);
        SavableQualitative red_defense = view.findViewById(R.id.red_defense);
        red_defense.setTeams(red);

        //Utilities.setupUi(getActivity(), view);

        return view;
    }

}
