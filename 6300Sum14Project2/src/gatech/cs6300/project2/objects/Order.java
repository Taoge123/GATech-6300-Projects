package gatech.cs6300.project2.objects;

import gatech.cs6300.project2.util.CommonUtils;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Order")
public class Order extends ParseObject {

    public static String PREORDER_TYPE = "preorder";
    public static String PURCHASE_TYPE = "purchase";

    public VIPCustomer getVIPCustomer() {
        return (VIPCustomer) getParseObject("VIPCustomer");
    }

    public void setVIPCustomer(VIPCustomer vip) {
        put("VIPCustomer", vip);
    }

    public Product getProduct() {
        return (Product) getParseObject("Product");
    }

    public void setProduct(Product product) {
        put("Product", product);
    }

    public CoffeeCartLocation getCoffeeCartLocation() {
        return (CoffeeCartLocation) getParseObject("CoffeeCartLocation");
    }

    public void setCoffeeCartLocation(CoffeeCartLocation location) {
        put("CoffeeCartLocation", location);
    }

    public Date getPickupDate() {
        return getDate("pickupDate");
    }

    public void setPickupDate(Date pickupDate) {
        put("pickupDate", pickupDate);
    }

    public Double getAmountPaid() {
        return Double.parseDouble(getNumber("amountPaid").toString());
    }

    public void setAmountPaid(Double amountPaid) {
        put("amountPaid", amountPaid);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        if (PREORDER_TYPE.equals(type) || PURCHASE_TYPE.equals(type)) {
            put("type", type);
        }
    }

    public int getOrderPoints() {
        // Return number of points earned from this transaction

        return (int) Math.round(getAmountPaid());
    }

    public String getDisplayString() {
        String orderDate = CommonUtils.dateToString(this.getCreatedAt());
        String paidAmount = CommonUtils.formatCurrency(this.getAmountPaid());
        return String.format("%s %s (%s)", orderDate, this.getProduct().getName(), paidAmount);
    }

}
