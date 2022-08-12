package puzzles.clock;

import puzzles.common.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alex Jiang acj3276@rit.edu
 */
public class ClockConfig implements Configuration {


    //the total hours the clock has
    private final int totalHours;
    //the current hours the hand is on
    private final int currentHour;
    //the end goal of the hand
    private final int end;

    /**
     * Constructor for ClockConfig
     * @param hours (int) total hours
     * @param start (int) starting position
     * @param end (int) goal
     */
    public ClockConfig(int hours, int start, int end){
        this.totalHours = hours;
        this.currentHour = start;
        this.end = end;
    }



    /**
     * Checks if the other ClockConfig is equal
     *
     * only checks if the the current is the same
     * @param obj (Object)
     * @return (boolean)
     * true if its equal
     * false if its not equal
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof ClockConfig){
            return (((ClockConfig) obj).currentHour == this.currentHour);
        }
        return false;
    }

    /**
     * gets the hashCode of the config
     * @return int hashCode
     */
    @Override
    public int hashCode(){
        return (this.currentHour+this.end+this.totalHours);
    }


    /**
     * gets the Successors of the current config
     * @return (ArrayList<Configuration>) list of the successors
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        ArrayList<Configuration> Successors = new ArrayList<>();
        int arg1 = this.currentHour+1;
        int arg2 = this.currentHour-1;
        //loops back to 1
        if (arg1 > this.totalHours){
            arg1 = 1;
        }
        //loops back to max
        if (arg2 <= 0){
            arg2 = this.totalHours;
        }
        Configuration config1 = new ClockConfig(this.totalHours,arg1,this.end);
        Configuration config2 = new ClockConfig(this.totalHours,arg2,this.end);
        Successors.add(config1);
        Successors.add(config2);
        return Successors;
    }

    /**
     * checks if the current hour is the goal
     *
     * @return (boolean)
     */
    @Override
    public boolean isGoal() {
        return this.currentHour== this.end;
    }

    /**
     * converts the current hour to string
     *
     * @return (String) the current hour
     */
    @Override
    public String toString(){
        return String.valueOf(this.currentHour);


    }
}
