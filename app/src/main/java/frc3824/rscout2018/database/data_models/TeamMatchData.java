package frc3824.rscout2018.database.data_models;

import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import frc3824.rscout2018.BR;
import frc3824.rscout2018.database.Database;
import frc3824.rscout2018.database.data_models.powered_up.CubeEvent;
import frc3824.rscout2018.database.data_models.powered_up.DropData;
import frc3824.rscout2018.views.SavableCounter;

/**
 * Data Model for a single team in a single match
 */
public class TeamMatchData extends DataModel
{
    //region Logistics
    //region Match Number
    int matchNumber;

    /**
     * Getter function for match number
     */
    @Bindable
    public int getMatchNumber()
    {
        return matchNumber;
    }

    /**
     * Setter function for match number
     *
     * @param matchNumber The match number
     */
    public void setMatchNumber(int matchNumber)
    {
        this.matchNumber = matchNumber;
        notifyChange();
    }

    //endregion
    //region Team Number
    int teamNumber;

    /**
     * Getter function for team number
     */
    @Bindable
    public int getTeamNumber()
    {
        return teamNumber;
    }

    /**
     * Setter function for team number
     *
     * @param teamNumber The team number
     */
    public void setTeamNumber(int teamNumber)
    {
        this.teamNumber = teamNumber;
        notifyChange();
    }

    //endregion
    //region Scout Name
    String scoutName;

    /**
     * Getter function for scout name
     */
    @Bindable
    public String getScoutName()
    {
        return scoutName;
    }

    /**
     * Setter function for scout name
     *
     * @param scoutName The name of the scout who recorded the information for {@link TeamMatchData#teamNumber} in {@link TeamMatchData#matchNumber}
     */
    public void setScoutName(String scoutName)
    {
        this.scoutName = scoutName;
        notifyChange();
    }

    @Bindable
    public TextWatcher getScoutNameWatcher()
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                setScoutName(s.toString());
            }
        };
    }
    //endregion
    //endregion

    //region Fouls
    //region Fouls
    int fouls;

    /**
     * Getter function for fouls
     */
    @Bindable
    public int getFouls()
    {
        return fouls;
    }

    /**
     * Setter function for fouls
     *
     * @param fouls The number of normal fouls caused by this team in this match
     */
    public void setFouls(int fouls)
    {
        this.fouls = fouls;
        notifyChange();
    }

    @Bindable
    public SavableCounter.CountListener getFoulsListener()
    {
        return new SavableCounter.CountListener()
        {
            @Override
            public void onChange(int value)
            {
                setFouls(value);
            }
        };
    }
    //endregion
    //region Tech Fouls
    int techFouls;

    /**
     * Getter function for tech fouls
     */
    @Bindable
    public int getTechFouls()
    {
        return techFouls;
    }

    /**
     * Setter function for tech fouls
     *
     * @param techFouls The number of tech fouls caused by this team in this match
     */
    public void setTechFouls(int techFouls)
    {
        this.techFouls = techFouls;
        notifyChange();
    }

    @Bindable
    public SavableCounter.CountListener getTechFoulsListener()
    {
        return new SavableCounter.CountListener()
        {
            @Override
            public void onChange(int value)
            {
                setTechFouls(value);
            }
        };
    }

    //endregion
    //region Yellow Card
    boolean yellowCard;

    /**
     * Getter function for yellow card
     *
     * @return Whether a yellow card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    @Bindable
    public boolean isYellowCard()
    {
        return yellowCard;
    }

    /**
     * Setter function for yellow card
     *
     * @param yellowCard Whether a yellow card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    public void setYellowCard(boolean yellowCard)
    {
        this.yellowCard = yellowCard;
        notifyChange();
    }

    @Bindable
    public CompoundButton.OnCheckedChangeListener getYellowCardListener()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setYellowCard(isChecked);
            }
        };
    }

    //endregion
    //region Red Card
    boolean redCard;

    /**
     * Getter function for red card
     *
     * @return Whether a red card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    @Bindable
    public boolean isRedCard()
    {
        return redCard;
    }

    /**
     * Setter function for red card
     *
     * @param redCard Whether a red card was received in match {@link TeamMatchData#matchNumber} by team {@link TeamMatchData#teamNumber}
     */
    public void setRedCard(boolean redCard)
    {
        this.redCard = redCard;
        notifyChange();
    }

    @Bindable
    public CompoundButton.OnCheckedChangeListener getRedCardListener()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setRedCard(isChecked);
            }
        };
    }

    //endregion
    //endregion

    //region Misc
    //region DQ
    boolean dq;

    /**
     * Getter function for whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     */
    public boolean isDq()
    {
        return dq;
    }

    /**
     * Setter function for whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     *
     * @param dq Whether team {@link TeamMatchData#teamNumber} was disqualified in match {@link TeamMatchData#matchNumber}
     */
    public void setDq(boolean dq)
    {
        this.dq = dq;
        notifyChange();
    }

    @Bindable
    public CompoundButton.OnCheckedChangeListener getDqListener()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setDq(isChecked);
            }
        };
    }

    //endregion
    //region No Show
    boolean noShow;

    /**
     * Getter function for whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     */
    public boolean isNoShow()
    {
        return noShow;
    }

    /**
     * Setter function for whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     *
     * @param noShow Whether team {@link TeamMatchData#teamNumber} did not show up to match {@link TeamMatchData#matchNumber}
     */
    public void setNoShow(boolean noShow)
    {
        this.noShow = noShow;
        notifyChange();
    }

    @Bindable
    public CompoundButton.OnCheckedChangeListener getNoShowListener()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setNoShow(isChecked);
            }
        };
    }
    //endregion
    //region Notes
    String notes;

    /**
     * Getter function for notes
     */
    @Bindable
    public String getNotes()
    {
        return notes;
    }

    /**
     * Setter function for notes
     *
     * @param notes The notes taken on team {@link TeamMatchData#teamNumber} in match {@link TeamMatchData#matchNumber}
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
        notifyChange();
    }

    @Bindable
    public TextWatcher getNotesWatcher()
    {
        return new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                setNotes(s.toString());
            }
        };
    }

    //endregion
    //endregion

    //region Game Specific
    //region Autonomous
    //region Crossed Auto Line
    boolean crossedAutoLine;

    /**
     * Returns whether the team crossed the auto line
     *
     * @return
     */
    @Bindable
    public boolean getCrossedAutoLine()
    {
        return crossedAutoLine;
    }

    /**
     * Sets whether the team crossed the auto line
     */
    public void setCrossedAutoLine(boolean crossedAutoLine)
    {
        this.crossedAutoLine = crossedAutoLine;
        notifyChange();
    }

    @Bindable
    public CompoundButton.OnCheckedChangeListener getCrossedAutoLineListener()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setCrossedAutoLine(isChecked);
            }
        };
    }

    //endregion
    //region Start Location X
    float startLocationX;

    /**
     * Returns the start location x as a percentage of the width of the field
     */
    @Bindable
    public float getStartLocationX()
    {
        return startLocationX;
    }

    /**
     * Sets the start location x as a percentage of the width of the field
     */
    public void setStartLocationX(float startLocationX)
    {
        this.startLocationX = startLocationX;
        notifyChange();
    }

    //endregion
    //region Start Location Y
    float startLocationY;

    /**
     * Returns the start location y as a percentage of the depth of the field
     */
    @Bindable
    public float getStartLocationY()
    {
        return startLocationY;
    }

    /**
     * Sets the start location y as a percentage of the depth of the field
     */
    public void setStartLocationY(float startLocationY)
    {
        this.startLocationY = startLocationY;
        notifyChange();
    }

    //endregion
    //region Cube Events
    ArrayList<CubeEvent> autoCubeEvents = new ArrayList<>();

    @Bindable
    public ArrayList<CubeEvent> getAutoCubeEvents()
    {
        return autoCubeEvents;
    }

    public void setAutoCubeEvents(ArrayList<CubeEvent> autoCubeEvents)
    {
        this.autoCubeEvents = autoCubeEvents;
        notifyChange();
    }

    //endregion
    //endregion
    //region Teleop
    //region Cube Events
    ArrayList<CubeEvent> teleopCubeEvents = new ArrayList<>();

    @Bindable
    public ArrayList<CubeEvent> getTeleopCubeEvents()
    {
        return teleopCubeEvents;
    }

    public void setTeleopCubeEvents(ArrayList<CubeEvent> teleopCubeEvents)
    {
        this.teleopCubeEvents = teleopCubeEvents;
        notifyChange();
    }

    //endregion
    //endregion
    //region Endgame
    //region Climb Status
    String climbStatus;

    /**
     * Returns the status of the climb (whether or not
     * it was successful and if not then why)
     */
    @Bindable
    public String getClimbStatus()
    {
        return climbStatus;
    }

    /**
     * Set the status of the climb (whether or not
     * it was successful and if not then why)
     */
    public void setClimbStatus(String climbStatus)
    {
        this.climbStatus = climbStatus;
        notifyChange();
    }

    //endregion
    //region Climb Method
    String climbMethod;

    /**
     * Returns the method of the climb upon a
     * successful climb
     */
    @Bindable
    public String getClimbMethod()
    {
        return climbMethod;
    }

    /**
     * Set the method of the climb upon a
     * successful climb
     */
    public void setClimbMethod(String climbMethod)
    {
        this.climbMethod = climbMethod;
        notifyChange();
    }
    //endregion
    //endregion
    //endregion

    //region Constructors
    public TeamMatchData(int teamNumber, int matchNumber)
    {
        this.teamNumber = teamNumber;
        this.matchNumber = matchNumber;
        load();
    }
    //endregion

    //region Database
    public void save()
    {
        super.save(String.format("tmd_%d_%d", teamNumber, matchNumber));
    }

    public void load()
    {
        super.load(String.format("tmd_%d_%d", teamNumber, matchNumber),
                   Arrays.asList("teamNumber", "matchNumber"));

    }
    //endregion
}
