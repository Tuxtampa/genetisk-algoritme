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
    ArrayList<Actor> actors = new ArrayList<Actor>();
    double average = 0;
    double scoreSum = 0;
    private ArrayList<Actor> newActors;

    public void settings() {
        makeData(names, weights, prices);
        createActors();
        createActors();
        createActors();
        createActors();
        createActors();
        createActors();
        createActors();
        createActors();        createActors();
        createActors();
        createActors();
        createActors();        createActors();
        createActors();
        createActors();
        createActors();        createActors();
        createActors();
        createActors();
        createActors();        createActors();
        createActors();
        createActors();
        createActors();
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
        Actor[] intermediaryActors = (Actor[]) newActors.toArray();
        actors.clear();
        Collections.addAll(actors, intermediaryActors);
        newActors.clear();
        System.out.println("Mouse CLICKED");
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
                actor.prio[i] = random(parseFloat((int) 0.95), parseFloat((int) 1.05)) * actor.prio[i];
            }
                final List<String> stringListCopy = Arrays.asList(names);
                ArrayList<String> sortedList = new ArrayList(stringListCopy);
                Collections.sort(sortedList, Comparator.comparing(s -> actor.prio[stringListCopy.indexOf(s)]));
                actor.itemPrio = sortedList.toArray(new String[0]);
                actor.test();
        }
    }
}
