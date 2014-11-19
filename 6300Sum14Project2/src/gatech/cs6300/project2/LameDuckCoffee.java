package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.CoffeeCartLocation;
import gatech.cs6300.project2.objects.Order;
import gatech.cs6300.project2.objects.Product;
import gatech.cs6300.project2.objects.VIPCustomer;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class LameDuckCoffee extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        // ParseObject.registerSubclass(VIPCustomer.class);
        ParseObject.registerSubclass(Product.class);
        ParseObject.registerSubclass(VIPCustomer.class);
        ParseObject.registerSubclass(CoffeeCartLocation.class);
        ParseObject.registerSubclass(Order.class);

        Parse.initialize(this, "dv8p8ogiguBdIXybeElAXsyZEi4Nv0EetKGOgwor", "CtZv6oI9Z34w1Dg6bBCKMqrPmn4I7JHOV1gNhWYv");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicWriteAccess(true);
        
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

    }

}
