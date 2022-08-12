package puzzles.clock;

import puzzles.common.Configuration;
import puzzles.common.solver.Solver;

import java.util.List;

/**
 * @author Alex Jiang acj3276@rit.edu
 * main clock
 * Initialize everything for the solver
 *
 * Prints the total configs
 * Prints the total unique configs
 * Prints steps to the goal
 *
 */
public class Clock {
    /**
     * Attempts to solve the clock puzzle
     * @param args (String[]) from console
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Clock hours start stop");
        }
        else{
            try{
                //convert args into int
                int hours = Integer.parseInt(args[0]);
                int start = Integer.parseInt(args[1]);
                int end = Integer.parseInt(args[2]);
                System.out.printf("Hours: %s, Start: %s, End: %s\n",hours,start,end);
                Configuration initialize = new ClockConfig(hours,start,end);
                Solver solver = new Solver(initialize);
                List<Configuration> path = solver.solve();
                System.out.printf("Total configs: %s\n",solver.getNumConfigs());
                System.out.printf("Unique configs: %s\n",solver.getUniqueNumConfigs());
                if(!path.isEmpty()){
                    int step = 0;
                    for (Configuration config: path){
                        System.out.printf("Step %s: %s\n",step,config);
                        step++;
                    }
                }
                else{
                    System.out.println("No solution");
                }
            }
            catch (NumberFormatException e){
                System.err.println("Args should be integers");
            }
        }
    }
}
