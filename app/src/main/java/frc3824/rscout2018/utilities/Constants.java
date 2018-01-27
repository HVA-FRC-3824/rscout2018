package frc3824.rscout2018.utilities;

import java.util.ArrayList;

import frc3824.rscout2018.R;

/**
 * @author frc3824
 */
public interface Constants
{

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

        String ENABLE_SERVER = "enable_server";
        String SERVER_IP = "server_ip";
        String SERVER_PORT = "server_port";

        String EVENT_KEY = "event_key";
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

        interface Auto
        {
            String  AUTO_START_POSITION = "auto_start_position";
            String AUTO_BASELINE = "auto_baseline";
        }

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

        String SCOUT_NAME = "scout_name";
        String ROBOT_PICTURE_DEFAULT = "robot_picture_default";
        String ROBOT_PICTURES = "robot_pictures";

        interface Dimensions {
            String WIDTH = "width";
            String LENGTH = "length";
            String HEIGHT = "height";
            String WEIGHT = "weight";
        }

        interface Miscellaneous {
            String PROGRAMMING_LANGUAGE = "programming_language";
            interface Programming_Languages {
                String CPP = "C++";
                String JAVA = "Java";
                String LABVIEW = "Labview";
                String PYTHON = "Python";
                String CSHARP = "C#";

                String [] PROGRAMMING_LANGUAGES = {CPP, JAVA, LABVIEW, PYTHON, CSHARP};
            }
            String DRIVE_TRAIN = "drive_train";
            interface Drive_Trains {
                String TANK_4_WHEEL = "Tank (4 wheels)";
                String TANK_6_WHEEL = "Tank (6 wheels)";
                String TANK_8_WHEEL = "Tank (8 wheels)";
                String TANK_TREAD = "Tank (tread)";
                String MECANUM = "Mecanum";
                String SWERVE = "Swerve";

                String [] DRIVE_TRAINS = {TANK_4_WHEEL, TANK_6_WHEEL, TANK_8_WHEEL, TANK_TREAD, MECANUM, SWERVE};
                }
                String CIMS = "cims";
            }
        }

        String NOTES = "pit_notes";
    }

    interface SuperScouting
    {
        String[] TABS = {""};
    }

