package gatech.cs6300.project2.objects;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("VIPCustomer")
public class VIPCustomer extends ParseObject {

    public static int MONTHLY_REQUIRED_GOLD_POINTS = 500;
    public static int TOTAL_REQUIRED_GOLD_POINTS = 5000;

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        String strippedName = name.trim();
        if (!strippedName.isEmpty()) {
            put("name", strippedName);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        phone = phone.trim();
        if (phone.length() >= 10) {
            put("phone", phone);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Date getBirthdate() {
        return getDate("birthdate");
    }

    public void setBirthdate(Date birthdate) {
        put("birthdate", birthdate);
    }

    public String getVIPCustomerNumber() {
        return getString("VIPCustomerNumber");
    }

    public void setVIPCusterNumber(String vipCustomerNumber) {
        // Check if number is 10 digit alpha-numeric
        String pattern = "^[a-zA-Z0-9]*$";
        if (vipCustomerNumber.matches(pattern) && vipCustomerNumber.length() == 10) {
            put("VIPCustomerNumber", vipCustomerNumber);
        } else {
            throw new IllegalArgumentException();
        }

    }

    public boolean isGold() {
        // Return if the customer has gold status
        return getPoints(30) > MONTHLY_REQUIRED_GOLD_POINTS || getPoints(0) >= TOTAL_REQUIRED_GOLD_POINTS;
    }

    public int getPoints(int days) {
        /*
         * Return points earned by the customer in X days, days=0 means total lifetime points
         */

        List<Order> orders = getOrders(days);
        int points = 0;
        for (Order order : orders) {
            points += order.getOrderPoints();
        }
        Log.v("Points", points + "");
        return points;
    }

    public List<Order> getOrders(int days) {
        /*
         * Return list of orders placed by the customer, days=0 means total lifetime
         */
        return getOrders(days, false);

    }

    public List<Order> getOrders(int days, boolean includeProduct) {
        /*
         * Return list of orders placed by the customer, days=0 means total lifetime
         */

        List<Order> orders = null;
        ParseQuery<Order> query = ParseQuery.getQuery(Order.class);
        if (includeProduct) {
            query.include("Product");
        }

        query.whereEqualTo("VIPCustomer", this);

        if (days > 0) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -days);
            query.whereGreaterThan("createdAt", cal.getTime());
        }

        try {
            orders = query.find();
        } catch (ParseException e) {
            Log.v("ParseException", "Error getting orders for customer");
        }
        return orders;
    }

}
