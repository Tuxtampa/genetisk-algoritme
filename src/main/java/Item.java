public class Item {
    String itemName;
    int itemWeight;
    int itemPrice;
    float itemValue;
    Item(String name, int weight, int price){
        itemName = name;
        itemWeight = weight;
        itemPrice = price;
        itemValue = weight/price;
    }

}
