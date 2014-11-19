package gatech.cs6300.project2.objects;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Product")
public class Product extends ParseObject {

    public static String COFFEE_TYPE = "coffee";
    public static String DESSERT_TYPE = "dessert";

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        if (COFFEE_TYPE.equals(type) || DESSERT_TYPE.equals(type)) {
            put("type", type);
        }
    }

    public Double getPrice() {
        return getDouble("price");
    }

    public void setPrice(Double price) {
        put("price", price);
    }

    public boolean getBestSeller() {
        return getBoolean("bestseller");
    }

    public void setBestSeller(boolean bestseller) {
        put("bestseller", bestseller);
    }

    public boolean getActive() {
        return getBoolean("active");
    }

    public void setActive(boolean active) {
        put("active", active);
    }

    // Returns the refill price for the product based on the status of the customer.
    public Double getRefillPrice(VIPCustomer customer) {

        if (customer.isGold()) {
            return 0.0;
        } else {

            return getPrice() / 2;
        }
    }

    // Returns the total number of pre-order slots for the product
    public int getTotalPreOrderSlots() {

        if (DESSERT_TYPE.equals(getType())) {

            if (getBestSeller()) {
                return 3;
            } else {
                return 5;
            }

        } else {
            return 0;
        }

    }

    // Returns the total number of pre-order slots remaining for the product
    public int getPreOrderSlotsAvailable(Date preOrderDate, CoffeeCartLocation location) {

        int numOfPreorders = 0;

        // verify the order history for product ParseQuery<Order>
        ParseQuery<Order> preOrderQuery = ParseQuery.getQuery(Order.class);
        preOrderQuery.whereEqualTo("type", Order.PREORDER_TYPE);
        preOrderQuery.whereEqualTo("Product", this);
        preOrderQuery.whereGreaterThanOrEqualTo("pickupDate", preOrderDate);
        preOrderQuery.whereEqualTo("CoffeeCartLocation", location);

        try {
            numOfPreorders = preOrderQuery.count();
        } catch (ParseException e) {
            numOfPreorders = getTotalPreOrderSlots();
        }

        // if preprder slots - number of preorders is positive return num else zero
        int numOfPreordersAvailable = getTotalPreOrderSlots() - numOfPreorders;
        if (numOfPreordersAvailable > 0) {
            return numOfPreordersAvailable;
        } else {
            return 0;
        }
    }

}