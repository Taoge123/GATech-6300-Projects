package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.Product;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;

public class LameDuckProjectActivity extends Activity {
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ParseQuery<Product> query = ParseQuery.getQuery("Product");
        List<Product> list = null;
        try {
            list = query.find();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
            for (Product p : list) {
                TextView productView = new TextView(this);
                productView.setText(p.getName());
                Log.w("LameDucCoffee2: ", p.getName());
                linearLayout.addView(productView);
            }

        } catch (ParseException e) {
            // TODO Handle gracefully
            e.printStackTrace();
        }

    }
}
