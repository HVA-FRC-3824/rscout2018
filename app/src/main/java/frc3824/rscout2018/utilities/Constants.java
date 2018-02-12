package frc3824.rscout2018.utilities;

import android.app.LauncherActivity;

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
                "Abigail Bradfield",
                "Steven Busby"
        };

        String[] MATCH_SCOUTS_LIST = {};

        String[] PIT_SCOUTS_LIST = {};
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

        String IP_MODIFIED = "ip_modified";
        String LOAD_DATA = "load_data";
        String PING = "ping";
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
        String[] TABS = {"Start", "Auto", "Teleop", "Endgame", "Fouls", "Misc"};

        interface CubeEvents
        {
            String PICK_UP = "Picked Up";
            String PLACED = "Placed";
            String DROPPED = "Dropped";
            String LAUNCH_SUCCESS = "Launch Success";
            String LAUNCH_FAILURE = "Launch Failure";

            String[] EVENT_OPTIONS = {
                    // PICK_UP,
                    PLACED,
                    DROPPED,
                    LAUNCH_SUCCESS,
                    LAUNCH_FAILURE
            };

            float NEAR_SWITCH_X = 0.0f;
            float NEAR_SWITCH_Y = 0.0f;
            float SCALE_X = 0.0f;
            float SCALE_Y = 0.0f;
            float FAR_SWITCH_X = 0.0f;
            float FAR_SWITCH_Y = 0.0f;
            float EXCHANGE_STATION_X = 0.0f;
            float EXCHANGE_STATION_Y = 0.0f;

        }

        interface EndGame
        {
            String NO_CLIMB_ATTEMPT = "No climb attempt";
            String PARKED_ON_PLATFORM = "Parked on platform";
            String DID_NOT_FINISH_IN_TIME = "Did not finish in time";
            String ROBOT_FELL = "Robot fell";
            String SUCCESSFUL = "Successful";

            String[] CLIMB_STATE_OPTIONS = {NO_CLIMB_ATTEMPT,
                                            PARKED_ON_PLATFORM,
                                            DID_NOT_FINISH_IN_TIME,
                                            ROBOT_FELL,
                                            SUCCESSFUL};
        }
    }

    interface PitScouting
    {
        String[] TABS = {"Robot Pic", "Dimensions", "Misc"};
    }

    interface SuperScouting
    {
        String[] TABS = {"Power Ups", "Notes"};
    }

    interface TeamStats
    {
        String[] TABS = {"Charts", "Match Data", "Pit Data", "Notes", "Schedule"};
    }

    interface Notifications
    {

    }

    interface PickList
    {
        String POWER_CUBES = "Power Cubes";
        String CLIMB = "Climb";
        String FOULS = "Fouls";
        String[] MAIN_SORTING = {
                POWER_CUBES,
                CLIMB,
                FOULS
        };

        interface PowerCubes
        {
            String ALL = "All";
            String NEAR_SWITCH = "Near Switch";
            String SCALE = "Scale";
            String FAR_SWITCH = "Far Switch";
            String EXCHANGE_STATION = "Exchange Station";
            String DROP = "Drop";
            String INCORRECT_SIDE = "Incorrect Side";
            String[] OPTIONS = {
                    ALL,
                    NEAR_SWITCH,
                    SCALE,
                    FAR_SWITCH,
                    EXCHANGE_STATION,
                    DROP,
                    INCORRECT_SIDE
            };
        }

        interface Climb
        {

        }
    }
}
