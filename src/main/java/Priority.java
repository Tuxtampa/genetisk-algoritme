public class Priority {
    String item;
    float weight;
    Priority(float weight1, String item1){
        item = item1;
        weight = weight1;
    }
public String getName(){
        return item;
}
public int getWeight(){
        int w1 = (int) weight;
        return w1;
}
}
