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
    }

    interface  PitScouting
    {
        String[] TABS = {"Robot Pic", "Dimensions", "Misc"};
    }

    interface SuperScouting
    {
        String[] TABS = {"Power Ups", "Misc"};
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

        interface CLIMB
        {

        }

    }
}
