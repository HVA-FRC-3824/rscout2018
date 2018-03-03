package frc3824.rscout2018.utilities;

import android.graphics.Color;

/**
 * @author frc3824
 */
public interface Constants
{

    String APP_DATA = "appData";

    int OUR_TEAM_NUMBER = 3824;

    interface Settings
    {
        String ENABLE_MATCH_SCOUT = "enable_match_scout";
        String MATCH_SCOUT_POSITION = "match_scout_position";
        String BLUE_LEFT = "blue_left";

        String ENABLE_PIT_SCOUT = "enable_pit_scout";
        String PIT_SCOUT_POSITION = "pit_scout_position";

        String ENABLE_SUPER_SCOUT = "enable_super_scout";

        String ENABLE_STRATEGIST = "enable_strategist";

        String ENABLE_SERVER = "enable_server";
        String SERVER_IP = "server_ip";
        String SERVER_PORT = "server_port";

        String ENABLE_ADMIN = "enable_admin";

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
        String DOWNLOAD_FULL_UPDATE = "load_data";
        String DOWNLOAD_SCHEDULE = "pull_matches";
        String DOWNLOAD_TEAMS = "pull_teams";
        String DOWNLOAD_PIT_DATA = "pull_pit_data";
        String DOWNLOAD_MATCH_DATA = "pull_match_data";
        String UPLOAD_PIT_DATA = "upload_pit_data";
        String UPLOAD_MATCH_DATA = "upload_match_data";
        String UPLOAD_SUPER_DATA = "upload_super_data";
        String DOWNLOAD_PICTURES = "download_pictures";
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

        interface Climb
        {
            interface Status
            {
                String NO_CLIMB_ATTEMPT = "No climb attempt";
                String PARKED_ON_PLATFORM = "Parked on platform";
                String DID_NOT_FINISH_IN_TIME = "Did not finish in time";
                String ROBOT_FELL = "Robot fell";
                String CLIMB = "Climb";

                String[] OPTIONS = {NO_CLIMB_ATTEMPT,
                                    PARKED_ON_PLATFORM,
                                    DID_NOT_FINISH_IN_TIME,
                                    ROBOT_FELL,
                                    CLIMB};
            }

            interface Method
            {
                String CLIMB_RUNG = "Climbed on rung, not supporting another robot";
                String CLIMB_RUNG_ONE = "Climbed on rung, supporting another robot";
                String CLIMB_RUNG_TWO = "Climbed on rung, supporting 2 other robots";
                String CLIMB_ON_OTHER_ROBOT_RUNG = "Climbed on a rung on another robot";
                String CLIMB_ON_OTHER_ROBOT_PLATFORM = "Climbed on platform of another robot";
                String SUPPORT_ONE = "Supported another robot on platform";
                String SUPPORT_TWO = "Supported 2 other robots on platform";
                String FOUL = "Credited through foul";
                String LEVITATE = "Credited through levitate, but not supporting other robots";
                String[] OPTIONS = {
                    CLIMB_RUNG,
                    CLIMB_RUNG_ONE,
                    CLIMB_RUNG_TWO,
                    CLIMB_ON_OTHER_ROBOT_RUNG,
                    CLIMB_ON_OTHER_ROBOT_PLATFORM,
                    SUPPORT_ONE,
                    SUPPORT_TWO,
                    FOUL,
                    LEVITATE
                };
            }

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

        interface Cubes
        {
            double SHORT_DISTANCE = 0.3;
            double MEDIUM_DISTANCE = 0.7;

            double EXCHANGE_THESHOLD = 0.10;
            double SWITCH_THRESHOlD = 0.35;
        }

        interface Climb
        {
            int[] STATUS_COLORS = new int[]{
                    Color.BLACK,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.RED,
                    Color.GREEN
            };

            int[] METHOD_COLORS = new int[]{
                    Color.YELLOW,
                    Color.GRAY,
                    Color.BLUE,
                    Color.GREEN,
                    Color.RED,
                    Color.BLACK,
                    Color.CYAN,
                    Color.WHITE,
                    Color.MAGENTA
            };
        }
    }

    interface MatchPreview
    {
        String[] TABS = {"Blue", "Red"};
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
