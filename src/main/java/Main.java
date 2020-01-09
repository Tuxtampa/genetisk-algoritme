import processing.core.PApplet;

import java.util.*;

public class Main extends PApplet {
    PApplet p = this;
    private static Item item;
    public static String[] names = new String[]{"kort", "kompas", "vand", "sandwich", "sukker", "dåsemad", "banan", "æble", "ost", "øl", "solcreme", "kamera", "T-shirt", "bukser", "paraply", "vandtætte bukser", "vandtæt overtøj", "pung", "solbriller", "håndklæde", "sokker", "bog", "notesbog", "telt"};
    public static String[] weights = new String[]{"90", "130", "1530", "500", "150", "680", "270", "390", "230", "520", "110", "320", "240", "480", "730", "420", "430", "220", "70", "180", "40", "300", "900", "2000"};
    public static Integer[] prices = new Integer[]{150, 35, 200, 160, 60, 45, 60, 40, 30, 10, 70, 30, 15, 10, 40, 70, 75, 80, 20, 12, 50, 10, 1, 150};
    public static float[] startPrio = new float[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
    ArrayList<Item> items = new ArrayList<Item>();
    public ArrayList<Actor> actors = new ArrayList<Actor>();
    double average = 0;
    double scoreSum = 0;
    public ArrayList<Actor> newActors;

    public void settings() {
        makeData(names, weights, prices);
        for(int i = 0; i < 20; i++){
            createActors();
        }
        System.out.println("actors created");
    }

    @Override
    public void draw() {
        p.background(255);
    }

    public void mouseClicked(){
        updateActorPrio();
        newGeneration();
        System.out.println("Average for that run was: " + average + " and it should have been " + scoreSum/actors.size());
        average = 0;
        scoreSum = 0;
        actors.clear();
        actors.addAll(newActors);
        newActors.clear();
System.out.println(actors.size());
    }

    public static void main(String[] args) {
        Main.main("Main");
    }


    private void makeData(String[] names, String[] weights, Integer[] prices) {
        for (int i = 0; i < names.length; i++) {
            items.add(i, new Item(names[i], Integer.parseInt(weights[i]), prices[i]));
        }
        System.out.println("items added");
    }
    private void newGeneration(){
        newActors = new ArrayList<Actor>(actors);
        for(int i = 0; i < actors.size(); i++){
            average = average + ((actors.get(i).score)/actors.size());
            scoreSum = scoreSum + actors.get(i).score;
        }
        for(int i = 0; i < actors.size();i++){
            Actor actor = actors.get(i);
            System.out.println(average);
            System.out.println(actor.score);
            System.out.println(Arrays.toString(actor.itemPrio));
            if(actor.score > average){
                newActors.add(actor);
                System.out.println(actors.size());
                System.out.println("score was more than average");
            }
            if (actor.score <= average) {
                newActors.remove(actor);
                System.out.println("score was less than average");
            }
        }
    }
    private void createActors() {
        actors.add(new Actor(startPrio, names));
        System.out.println("Actor created");
    }

    private void updateActorPrio() {
        for (Actor actor : actors) {
            for (int i = 0; i < actor.prio.length; i++) {
                actor.prio[i] = random( 0.95f, 1.05f) * actor.prio[i];
            }
            Comparator<Priority> priorityWeightComparator = Comparator.comparingInt(Priority::getWeight);
            Arrays.sort(actor.prioList, priorityWeightComparator);
                //final List<String> stringListCopy = Arrays.asList(names);
                //ArrayList sortedList = new ArrayList(stringListCopy);
                //sortedList.sort(Comparator.comparing(s -> actor.prio[stringListCopy.indexOf(s)]));
                //for (int i = 0; i < sortedList.size(); i++){
                //    actor.itemPrio[i] = (String) sortedList.get(i);
                //}
                //actor.itemPrio = sortedList.toArray(new String[0]);
                actor.test();
        }
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