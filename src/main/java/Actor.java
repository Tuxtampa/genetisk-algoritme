public class Actor {
    float[] prio;
    String[] itemPrio;
    int score;
    Actor(float[] priority, String[] items){
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
