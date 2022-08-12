package puzzles.water;

import puzzles.common.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Alex Jiang acj3276@rit.edu
 *
 */

public class WaterConfig implements Configuration {

    //max amount each bucket can hold
    private final List<Integer> buckets;
    //current amount each bucket has
    private final List<Integer> current;
    //the goal amount
    private final int goal;

    /**
     * Water config constructor
     * @param Amount (int) the goal amount
     * @param buckets (List<Integer>) max amount each bucket can hold
     * @param current (List<Integer>) the current amount each bucket has
     */
    public WaterConfig(int Amount, List<Integer> buckets, List<Integer> current){
        this.goal = Amount;
        this.buckets = buckets;
        this.current = current;
    }

    /**
     * gets the hash code
     * @return (int) hashcode
     */
    @Override
    public int hashCode(){
        return this.buckets.hashCode()+this.goal+this.current.hashCode();
    }

    /**
     * checks if the current config is = to the other config
     * checks only the current list with the other current list
     *
     * @param obj (Object)
     * @return (boolean)
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof WaterConfig){
            List<Integer> otherBuckets = ((WaterConfig) obj).current;
            for (int i =0;i<this.current.size();i++){
                if (!(otherBuckets.get(i).equals(this.current.get(i)))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    /**
     * gets successors
     * fill-fill the bucket
     * dump-empty the bucket
     * pour the bucket to another bucket
     *
     * @return (Collection<Configuration>) list of successors
     */
    @Override
    public Collection<Configuration> getSuccessors() {

        List<Configuration> Successors = new ArrayList<>();
        //loop each bucket
        for (int i =0;i<this.current.size();i++){
            List<Integer> temp = new ArrayList<>();
            List<Integer> temp2 = new ArrayList<>();
            //makes temp vars lists for change
            for (int x=0;x<this.current.size();x++){
                temp.add(x,this.current.get(x));
                temp2.add(x,this.current.get(x));
            }
            int max= this.buckets.get(i);
            //filling
            if (this.current.get(i)!=max){
                temp.remove(i);
                temp.add(i,max);
                Successors.add(new WaterConfig(this.goal,this.buckets,temp));

            }
            //Dumping
            if (this.current.get(i)!=0){
                temp2.remove(i);
                temp2.add(i,0);
                Successors.add(new WaterConfig(this.goal,this.buckets,temp2));

            }
            //pouring
            for (int j=0;j<this.current.size();j++){
                List<Integer> temp3 = new ArrayList<>();
                for (int x=0;x<this.current.size();x++){
                    temp3.add(x,this.current.get(x));
                }
                //loop through the other buckets to pour into
                if (i!=j){
                    //does math
                    int maxFluidReceiving = this.buckets.get(j);
                    int mainBucket=this.current.get(i);
                    int receivingBucket=this.current.get(j);
                    receivingBucket += mainBucket;
                    mainBucket=0;
                    if (receivingBucket> maxFluidReceiving){
                        mainBucket = receivingBucket-maxFluidReceiving;
                        receivingBucket = maxFluidReceiving;
                    }
                    temp3.remove(i);
                    temp3.add(i,mainBucket);
                    temp3.remove(j);
                    temp3.add(j,receivingBucket);
                    Successors.add(new WaterConfig(this.goal,this.buckets,temp3));
                }
            }
        }
        return Successors;
    }

    /**
     * checks if any amounts in the buckets is a goal
     * @return (boolean)
     */
    @Override
    public boolean isGoal() {
        for (Integer i:this.current){
            if (i==goal){
                return true;
            }
        }
        return false;
    }

    /**
     * converts the current list into a string
     * @return (String)
     */
    @Override
    public String toString(){
        return this.current.toString();
    }
}
