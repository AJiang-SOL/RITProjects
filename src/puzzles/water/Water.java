package puzzles.water;

import puzzles.common.Configuration;
import puzzles.common.solver.Solver;

import java.util.ArrayList;
import java.util.List;

public class Water {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Water amount bucket1 bucket2 ..."));
        }
        else{
            //try block to check if the args can be converted to int
            try{
                //list of current bucket amounts
                ArrayList<Integer> init = new ArrayList<>();
                int amountFinal = Integer.parseInt(args[0]);
                ArrayList<Integer> bucket = new ArrayList<>();
                for (int i = 1;i<args.length;i++){
                    bucket.add(Integer.parseInt(args[i]));
                    init.add(0,0);
                }
                System.out.printf("Amount: %s, Buckets: %s\n",amountFinal,bucket);
                Configuration waterConfig = new WaterConfig(amountFinal,bucket,init);
                Solver solver = new Solver(waterConfig);
                List<Configuration> path = solver.solve();
                System.out.printf("Total configs: %s\n",solver.getNumConfigs());
                System.out.printf("Unique configs: %s\n",solver.getUniqueNumConfigs());
                //display path
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
