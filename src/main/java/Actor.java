import java.util.ArrayList;

public class Actor {
    float[] prio;
    String[] itemPrio;
    //ArrayList<Priority> prioList = new ArrayList<>();
    Priority[] prioList = new Priority[50];
    int score;
    Actor(float[] priority, String[] items){
        for(int i = 0; i < priority.length; i++){
            //prioList.set(i, new Priority(priority[i], items[i]));
            prioList[i] = new Priority(priority[i], items[i]);
            prioList[i].item = items[i];
            prioList[i].weight = priority[i];
        }
        prio = priority;
        itemPrio = items;
        score = 0;
    }

    int test() {
        int result = 0;
int space = 5000;
for(String item : itemPrio){
    int index = find(Main.names, item);
    if(Integer.parseInt(Main.weights[index]) < space){
        result = result + Main.prices[index];
        space = space - Integer.parseInt(Main.weights[index]);
    }
}
        score = result;
        return result;
    }
    private static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}
