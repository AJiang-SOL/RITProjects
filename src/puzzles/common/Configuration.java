package puzzles.common;

import java.util.Collection;

/**
 * @author Alex Jiang acj3276@rit.edu
 */

/**
 * Interface for both WaterConfig and ClockConfig
 *
 * used to pass configs into solver
 */
public interface Configuration {

    /**
     * Get the collection of successors from the current one.
     *
     * @return All successors, valid and invalid
     */
    public Collection<Configuration> getSuccessors();

    /**
     * Is the current configuration a goal?
     * @return true if goal; false otherwise
     */
    public boolean isGoal();

    /**
     * checks if the current is = to the object
     * @param obj (Object)
     * @return (boolean)
     */
    public boolean equals(Object obj);

    /**
     * gets the hash code
     * @return (int) hash code
     */
    public int hashCode();
}
