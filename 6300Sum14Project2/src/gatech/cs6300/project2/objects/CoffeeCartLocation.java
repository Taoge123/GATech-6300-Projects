package gatech.cs6300.project2.objects;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("CoffeeCartLocation")
public class CoffeeCartLocation extends ParseObject {

    public String getId() {
        return getObjectId();
    }

    public String getLocation() {
        return getString("location");
    }

    public void setLocation(String location) {
        put("location", location);
    }

    public String toString() {
        return getLocation();
    }

    @Override
    public boolean equals(Object o) {
        Log.w("CoffeeCartLocation: ", ((CoffeeCartLocation) o).getObjectId() + " - Equals called " + this.getObjectId());
        Log.w("CoffeeCartLocation: ", "AreEqual: " + ((CoffeeCartLocation) o).getObjectId().equals(this.getObjectId()));
        if (o == this)
            return true;
        if (o instanceof CoffeeCartLocation) {
            if (((CoffeeCartLocation) o).getObjectId().equals(this.getObjectId()))
                return true;
        }
        return false;
    }
}
