package frc3824.rscout2018.utilities;

import java.util.ArrayList;

import frc3824.rscout2018.R;

/**
 * @author frc3824
 */
public interface Constants
{

    String APP_DATA = "appData";

    /**
     * Version number changing rules:
     * - Right most number get changed for major changes
     * - Middle number gets changed after events
     * - Left most number is changed after the season
     */
    String VERSION = "3.0.0";
    int OUR_TEAM_NUMBER = 3824;

    interface Settings
    {
        String ENABLE_MATCH_SCOUT = "enable_match_scout";
        String MATCH_SCOUT_POSITION = "match_scout_position";

        String ENABLE_PIT_SCOUT = "enable_pit_scout";
        String PIT_SCOUT_POSITION = "pit_scout_position";

        String ENABLE_SUPER_SCOUT = "enable_super_scout";

        String ENABLE_STRATEGIST = "enable_strategist";

        String SERVER_TYPE = "server";
        String ENABLE_SERVER = "enable_server";
        String SERVER_IP = "server_ip";
        String SERVER_PORT = "server_port";
        String SERVER_USB = "USB";
        String SERVER_BLUETOOTH = "Bluetooth";

        String EVENT_KEY = "event_key";

        String[] SUPER_SCOUTS_LIST = {
                "Evan Boswell",
                "Abigail Bradfield",
                "Steven Busby"
        };
    }

    interface IntentExtras
    {
        interface NextPageOptions
        {
            String MATCH_SCOUTING = "match_scouting";
            String PIT_SCOUTING = "pit_scouting";
            String SUPER_SCOUTING = "super_scouting";
            String TEAM_STATS = "team_stats";
            String MATCH_PREVIEW = "match_preview";
        }
        String TEAM_NUMBER = "team_number";
        String MATCH_NUMBER = "match_number";
        String LAST_MODIFIED = "last_modified";
        String NEXT_PAGE = "next_page";
        String DRIVE_TEAM_FEEDBACK = "drive_team_feedback";
        String PIT_SCOUTING = "pit_scouting";
        String MATCH_VIEWING = "match_viewing";
        String TEAM_VIEWING = "team_viewing";
        String MATCH_PLAN_NAME = "match_plan_name";
        String SCOUTER = "scouter";
    }

    interface Database
    {
        interface PrimaryKeys
        {
            String MATCH_LOGISTICS = "match_number";
            String TEAM_LOGISTICS = "team_number";
            String TEAM_MATCH_DATA = "id";
            String TEAM_PIT_DATA = "team_number";
            String SUPER_MATCH_DATA = "match_number";
        }
    }


    interface MatchScouting
    {
        String[] TABS = {"Auto", "Teleop", "Endgame", "Fouls", "Misc"};

        interface EndGame
        {
            String NO_CLIMB_ATTEMPT = "No climb attempt";
            String PARKED_ON_PLATFORM = "Parked on platform";
            String DID_NOT_FINISH_IN_TIME = "Did not finish in time";
            String ROBOT_FELL = "Robot fell";
            String SUCCESSFUL = "Successful";

            String[] CLIMB_STATE_OPTIONS = {NO_CLIMB_ATTEMPT, PARKED_ON_PLATFORM, DID_NOT_FINISH_IN_TIME, ROBOT_FELL, SUCCESSFUL};
        }
    }

    interface  PitScouting
    {
        String[] TABS = {"Robot Pic", "Dimensions", "Misc"};
    }

    interface SuperScouting
    {
        String[] TABS = {""};

        String SCOUT_NAME = "scout_name";

        // GAME SPECIFIC
        interface Qualitative {
            String BLUE_SPEED = "blue_speed";
            String RED_SPEED = "red_speed";

            String BLUE_TORQUE = "blue_torque";
            String RED_TORQUE = "red_torque";

            String BLUE_CONTROL = "blue_control";
            String RED_CONTROL = "red_control";

            String BLUE_DEFENSE = "blue_defense";
            String RED_DEFENSE = "red_defense";
        }

        String NOTES = "super_notes";
    }

    interface Notifications {
        int MATCH_RECIEVED = 1;
        int SUPER_RECIEVED = 2;
        int UPLOAD_STRATEGIES = 3;
        int DOWNLOAD_STRATEGIES = 4;
        int UPLOAD_ROBOT_PICTURES = 5;
        int DOWNLOAD_ROBOT_PICTURES = 6;
        int DATA_TRANSFER_SUCCESS = 7;
        int DATA_TRANSFER_FAILURE = 8;
    }
}
