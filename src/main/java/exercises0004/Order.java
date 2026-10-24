package exercises0004;

import java.util.List;

public class Order {

    private List<Item> itemList;

    public Order(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "itemList=" + itemList +
                '}';
    }
}
