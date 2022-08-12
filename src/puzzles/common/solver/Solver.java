package puzzles.common.solver;

import puzzles.common.Configuration;

import java.util.*;

public class Solver {



    private long numConfigs;
    private long uniqueNumConfigs;
    private Configuration goal;
    private Configuration start;

    /**
     * Solver constructor
     * @param start (Configuration) initial config
     */
    public Solver(Configuration start){
        this.numConfigs=0;
        this.uniqueNumConfigs=0;
        this.start = start;
    }

    /**
     * does BFS to the goal
     * @return (List<Configuration>) the shortest path
     */
    public List<Configuration> solve(){

        Queue<Configuration> configQueue = new LinkedList<>();
        configQueue.add(this.start);

        HashMap<Configuration,Configuration> map = new HashMap<>();
        map.put(this.start,null);

        while(!configQueue.isEmpty()){
            Configuration config1 = configQueue.remove();
            if (config1.isGoal()){
                this.goal = config1;
                break;
            }
            for(Configuration config: config1.getSuccessors()){
                this.numConfigs++;
                if (!map.containsKey(config)){
                    this.uniqueNumConfigs++;
                    map.put(config,config1);
                    configQueue.add(config);
                }
            }
        }
        //makes the path
        List<Configuration> path = makePath(map,this.start,this.goal);
        return path;
    }

    /**
     * helper function for .solve()
     * @param predecessors (Map<Configuration,Configuration>) Hashmap of the configs
     * @param start (Configuration)
     * @param goal (Configuration)
     * @return (List<Configuration>) the shortest path
     */
    private List<Configuration> makePath(Map<Configuration,Configuration> predecessors, Configuration start, Configuration goal){
        List<Configuration> path = new ArrayList<>();
        if (predecessors.containsKey(goal)){
            Configuration current = goal;
            while(!current.equals(start)){
                path.add(0,current);
                current = predecessors.get(current);
            }
            path.add(0,start);
        }
        return path;
    }

    /**
     * get the number of configs
     * @return (long)
     */
    public long getNumConfigs(){
        return this.numConfigs;
    }

    /**
     * gets the number of unique configs
     * @return (long)
     */
    public long getUniqueNumConfigs(){
        return this.uniqueNumConfigs;
    }
}
