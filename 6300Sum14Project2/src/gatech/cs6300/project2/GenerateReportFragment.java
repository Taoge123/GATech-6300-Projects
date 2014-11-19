package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.Order;
import gatech.cs6300.project2.objects.Product;
import gatech.cs6300.project2.objects.VIPCustomer;
import gatech.cs6300.project2.util.CommonUtils;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class GenerateReportFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.generatereport_fragment, container, false);
        Button refreshReportButton = (Button) view.findViewById(R.id.generateDailyReportButton);
        refreshReportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container, GenerateReportFragment.newInstance())
                        .addToBackStack("fragBack").commit();
            }
        });

        final ListView listView = (ListView) view.findViewById(R.id.generateReportListView);
        ParseQuery<Order> orderQuery = ParseQuery.getQuery(Order.class);
        // Only selects orders from today's date or in this case, dates greater
        // than yesterday.
        orderQuery.whereGreaterThan("createdAt", CommonUtils.getYesterday());
        // Filters values in the query that are only equal to the current
        // selected coffee cart location.
        orderQuery.whereEqualTo("CoffeeCartLocation", ((MainActivity) getActivity()).getCoffeeCartLocation());

        final ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        orderQuery.include("Product");
        orderQuery.include("VIPCustomer");
        orderQuery.findInBackground(new FindCallback<Order>() {

            @Override
            public void done(final List<Order> orders, ParseException e) {
                if (e == null) {
                    ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_2,
                            android.R.id.text1, orders) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                            Product product = orders.get(position).getProduct();
                            Order order = orders.get(position);
                            VIPCustomer customer = orders.get(position).getVIPCustomer();
                            if (customer != null) {
                                text1.setText(customer.getName() + " - " + product.getName());
                                text2.setText("Order Type: " + order.getType() + " - Total: " 
                                + CommonUtils.formatCurrency(order.getAmountPaid()));
                            }
                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                    dialog.dismiss();

                }
            }

        });

        return view;
    }

    public static Fragment newInstance() {
        GenerateReportFragment fragment = new GenerateReportFragment();
        return fragment;
    }

}
