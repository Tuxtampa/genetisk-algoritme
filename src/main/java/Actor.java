import java.util.ArrayList;
import java.util.Collections;

public class Actor {
    ArrayList<Integer> prioList = new ArrayList<>();
    int score;
    ArrayList<String> backpack = new ArrayList<>();

    Actor(){
        for(int i = 0; i < Main.names.length; i++){
            prioList.add(i, i);
        }
        Collections.shuffle(prioList);
        score = 0;
    }


    int test() {
        int result = 0;
        int space = 5000;
        backpack.clear();
        for(int i = 0; i < prioList.size(); i++){
            int index = prioList.get(i);
            if(Integer.parseInt(Main.weights[index]) < space){
                result = result + Main.prices[index];
                space = space - Integer.parseInt(Main.weights[index]);
                backpack.add(Main.names[index]);
            }
        }
        score = result;
        return result;
    }
}
