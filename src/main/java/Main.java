import processing.core.PApplet;

import java.util.*;

/* Planning List:
Create actors from either random or set values, priorities will be represented by a row of items it will want to get first
i.e. the "genes" i am dealing with is simply a priority list of how the actor is going to pack it's backpack.
Then the fitness will be determined, and the chances of the actor reproducing will depend on the fitness
Then each actor will be paired with a weighted random partner an amount of times relative to the initial actor's fitness
The priority list for the new actor will be made by either;
taking each of the parents favorite items alternating(the one i chose for this project),
randomising for each spot in the row which of the parents' genes will be taken for that slot by index,
or randomising which parent's gene will be the next one in the sequence.
Then each of the priorities in the row will be iterated over and given a 1-2% of mutatation(moving to a random spot in the row).
Lastly the actor's fitness is evaluated and cycle repeats.
(Later addition):
The High, Low and Avg points for each generation are stored and then displayed when drawGraphs() is called.
UI is very barebones, but it will have to do for now. Red dots represent the highest score of the generation, green the lowest, and blue the average.
A mutation rate of 2% seemed pretty good, a nice chance of mutation while still having a very low possibility that it will all go to shit.
500 actors seemed to be pretty efficient, plus it looks really nice with the graph. :)
I tried to do something with score to the power of 1.5 for the reproduction rates,
but then the program would run out of memory when doing more than just a hundred actors, so i stopped that.
 */

public class Main extends PApplet {
    PApplet p = this;
    public static String[] names = new String[]{"kort", "kompas", "vand", "sandwich", "sukker", "dåsemad", "banan", "æble", "ost", "øl", "solcreme", "kamera", "T-shirt", "bukser", "paraply", "vandtætte bukser", "vandtæt overtøj", "pung", "solbriller", "håndklæde", "sokker", "bog", "notesbog", "telt"};
    public static String[] weights = new String[]{"90", "130", "1530", "500", "150", "680", "270", "390", "230", "520", "110", "320", "240", "480", "730", "420", "430", "220", "70", "180", "40", "300", "900", "2000"};
    public static Integer[] prices = new Integer[]{150, 35, 200, 160, 60, 45, 60, 40, 30, 10, 70, 30, 15, 10, 40, 70, 75, 80, 20, 12, 50, 10, 1, 150};
    public ArrayList<Actor> actors = new ArrayList<Actor>();
    public ArrayList<Integer> highPoints = new ArrayList<>();
    public ArrayList<Integer> lowPoints = new ArrayList<>();
    public ArrayList<Integer> avgPoints = new ArrayList<>();
    int amountOfActors = 500;
    int iterations = 1;
    float mutationRate = 0.02f;
    ArrayList<ArrayList<Integer>> matingPool = new ArrayList<>();
    int n = 0;
    int totalHighScore = 0;
    int maxScore = 1130;
    int runCount = 0;
    int highScoreRunCount = 0;
    String[] highScoreBackpack = new String[200];
    String[] generationBackpack = new String[200];


    public void settings() {
        p.size(1000,1000);
        createActors();
    }

    @Override
    public void draw() {
        mouseClicked();
        p.background(255);
        p.frameRate(60);
        drawGraph();
        p.text("The items the best actor this generation brought were "+ Arrays.toString(generationBackpack),10,800);
        p.text("The items the best actor overall brought were "+ Arrays.toString(highScoreBackpack),10,820);
        if(totalHighScore > maxScore-1)p.text("THE MAX SCORE HAS BEEN REACHED AT RUN " + highScoreRunCount,10,840);
    }

    public void mouseClicked() {
        for (int i = 0; i < iterations; i++) {
            runCount++;
            int average = 0;
            int highScore = 0;
            int lowScore = 1500;
            for (Actor actor : actors) {
                System.out.println(actor.test() + "With the items:" + actor.backpack.toString());
                int score = actor.test();
                average = average + score;
                if(score > highScore){
                    highScore = score;
                    generationBackpack = actor.backpack.toArray(new String[0]);
                }
                if(score > totalHighScore){
                    totalHighScore = score;
                    highScoreRunCount = runCount;
                    highScoreBackpack = actor.backpack.toArray(new String[0]);
                } if(score < lowScore){
                    lowScore = score;
                }
            }
            lowPoints.add(lowScore);
            average = average / amountOfActors;
            avgPoints.add(average);
            highPoints.add(highScore);
            System.out.println("The average for this generation was " + average);
            System.out.println("The highscore for this generation was " + highScore);
            System.out.println("The highscore overall is " + totalHighScore);
            System.out.println("The items the best actor this generation brought were "+ Arrays.toString(generationBackpack));
            System.out.println("The items the best actor overall brought were "+ Arrays.toString(highScoreBackpack));
            if(totalHighScore == 1130){
                System.out.println("THE MAX SCORE HAS BEEN REACHED AT RUN " + highScoreRunCount);
            }
            setMatingPool();
            newGeneration();
            drawGraph();
        }
        p.text("The items the best actor this generation brought were "+ Arrays.toString(generationBackpack),10,800);
    }

    public static void main(String[] args) {
        Main.main("Main");
    }

    private void createActors() {
        for(int i = 0; i < amountOfActors; i++) {
            actors.add(new Actor());
        }
    }
    public void setMatingPool(){
        matingPool.clear();
        n = 0;
        for (Actor actor : actors) {
            int abomination = actor.score;
            n = n + abomination;
            for (int j = 0; j < abomination; j++) {
                matingPool.add(new ArrayList<>(actor.prioList));
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
            if(random(0,1) >= 1-mutationRate){
                int iValue = source3.get(i);
                int target = parseInt(random(0,source3.size()+1)-1);
                int temp = source3.get(target);
                source3.set(target, iValue);
                source3.set(i, temp);
            }
        }
        return source3;
    }
    public void drawGraph(){
        for(int i = 0; i < highPoints.size(); i++){
            fill(255,0,0);
            lines(i, highPoints);
        }
        for(int i = 0; i < lowPoints.size(); i++){
            fill(0,255,0);
            lines(i, lowPoints);
        }
        for(int i = 0; i < avgPoints.size(); i++){
            fill(0,0,255);
            lines(i, avgPoints);
        }
        if(highPoints.size() > width/10){
            for(int i = 0; i < 1; i++) {
                highPoints.remove(0);
                lowPoints.remove(0);
                avgPoints.remove(0);
            }
            p.background(255);
            drawGraph();
        }
    }

    private void lines(int i, ArrayList<Integer> points) {
        p.ellipse(i*10, p.height-(points.get(i)/(1200f/height)),5,5);
        if(i>0)p.line((i-1)*10, height-(points.get(i-1)/(1200f/height)),i*10, height-(points.get(i)/(1200f/height)));
    }
}