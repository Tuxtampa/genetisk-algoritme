import processing.core.PApplet;

import java.util.*;

public class Main extends PApplet {
    PApplet p = this;
    public static String[] names = new String[]{"kort", "kompas", "vand", "sandwich", "sukker", "dåsemad", "banan", "æble", "ost", "øl", "solcreme", "kamera", "T-shirt", "bukser", "paraply", "vandtætte bukser", "vandtæt overtøj", "pung", "solbriller", "håndklæde", "sokker", "bog", "notesbog", "telt"};
    public static String[] weights = new String[]{"90", "130", "1530", "500", "150", "680", "270", "390", "230", "520", "110", "320", "240", "480", "730", "420", "430", "220", "70", "180", "40", "300", "900", "2000"};
    public static Integer[] prices = new Integer[]{150, 35, 200, 160, 60, 45, 60, 40, 30, 10, 70, 30, 15, 10, 40, 70, 75, 80, 20, 12, 50, 10, 1, 150};
    public static float[] startPrio = new float[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
    public ArrayList<Actor> actors = new ArrayList<Actor>();
    int amountOfActors = 200;
    int iterations = 1;
    ArrayList<ArrayList<Integer>> matingPool = new ArrayList<>();
    int n = 0;


    public void settings() {
        createActors();
    }

    @Override
    public void draw() {
        p.background(255);
    }

    public void mouseClicked() {
        for (int i = 0; i < iterations; i++) {
            int average = 0;
            for (Actor actor : actors) {
                System.out.println(actor.test() + "With the items:" + actor.backpack.toString());
                average = average + actor.test();
            }
            System.out.println("The average for this generation was " + average / amountOfActors);
            setMatingPool();
            newGeneration();
        }
    }

    public static void main(String[] args) {
        Main.main("Main");
    }

    private void createActors() {
        for(int i = 0; i < amountOfActors; i++) {
            actors.add(new Actor(startPrio, names));
        }
        System.out.println("Actors created");
    }
    // Start with an empty mating pool.
    public void setMatingPool(){
        matingPool.clear();
        for (Actor actor : actors) {
            n =+ actor.score;
            for (int j = 0; j < actor.score; j++) {
                matingPool.add(new ArrayList<Integer>(actor.prioList));
            }
        }
    }
    public void newGeneration(){
        for(Actor actor : actors){
            actor.prioList = mergeDNA1();
        }
    }
    public ArrayList<Integer> mergeDNA1(){
        ArrayList<Integer> source1 = new ArrayList<Integer>(matingPool.get(parseInt(random(0,n))));
        ArrayList<Integer> source2 = new ArrayList<Integer>(matingPool.get(parseInt(random(0,n))));
        ArrayList<Integer> source3 = new ArrayList<>();
        for(int i = 0; i < names.length; i++){
            if(i%2==0) {
                source3.add(source1.get(0));
                source2.remove(source1.get(0));
                source1.remove(source1.get(0));
            }
            if(i%2==1) {
                source3.add(source2.get(0));
                source1.remove(source2.get(0));
                source2.remove(source2.get(0));
            }
        }
        for(int i = 0; i < source3.size(); i++){
            if(random(0,1) >= 0.95){
                int iValue = source3.get(i);
                int target = parseInt(random(0,source3.size()+1)-1);
                int temp = source3.get(target);
                source3.set(target, iValue);
                source3.set(i, temp);
            }
        }
        return source3;
    }
}

/* Planning List:
Create actors from either random or set values, priorities will be represented by a row of items it will want to get first
Then the fitness will be determined, and the chances of the actor reproducing will depend on the fitness
Then each actor will be paired with a weighted random partner an amount of times relative to the initial actor's fitness
The priority list for the new actor will be made by either;
taking each of the parents favorite items alternating,
randomising for each spot in the row which of the parents' genes will be taken for that slot by index,
or randomising which parent's gene will be the next one in the sequence.
Then each of the priorities in the row will be iterated over and given a 1-2% of mutatation(moving to a random spot in the row).
Lastly the actor's fitness is evaluated and cycle repeats.
 */