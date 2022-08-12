package puzzles.hoppers.solver;

import puzzles.common.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.List;

/**
 * @author Alex Jiang acj3276@rit.edu
 * Hopper solver main
 * used to solve hopper puzzles
 */
public class Hoppers {
    /**
     * main
     * @param args (filename)
     * @throws IOException file reading
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        //solve the puzzle
        else{
            String filename = args[0];
            System.out.printf("File: %s\n",filename);
            Configuration initialize = new HoppersConfig(filename);
            System.out.println(initialize);
            Solver solver = new Solver(initialize);
            //gets the path of the solution
            List<Configuration> path = solver.solve();
            System.out.printf("Total configs: %s\n",solver.getNumConfigs());
            System.out.printf("Unique configs: %s\n",solver.getUniqueNumConfigs());
            if(!path.isEmpty()){
                int step = 0;
                for (Configuration config: path){
                    System.out.printf("Step %s:\n",step);
                    System.out.println(config);
                    step++;
                }
            }
            //if the path is empty then there is no solution
            else{
                System.out.println("No solution");
            }
        }
    }
}
