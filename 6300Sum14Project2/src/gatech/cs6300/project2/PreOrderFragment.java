package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.Order;
import gatech.cs6300.project2.objects.Product;
import gatech.cs6300.project2.objects.VIPCustomer;
import gatech.cs6300.project2.util.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class PreOrderFragment extends Fragment {
    boolean validDate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.preorder_fragment, container, false);
        final Date pickupDate = new Date();
        String currentLocation = ((MainActivity) getActivity()).getCoffeeCartLocation().getLocation();

        if (currentLocation == null) {
            Toast.makeText(view.getContext(), "Please Set Your Coffee Cart Location", Toast.LENGTH_LONG).show();

        } else {
            TextView loc = (TextView) view.findViewById(R.id.preorderItem_Location);
            loc.setText(currentLocation);
        }

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.preorderItem_Pickup);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Calendar pickedDate = Calendar.getInstance();
                pickedDate.set(Calendar.YEAR, year);
                pickedDate.set(Calendar.MONDAY, month);
                pickedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                int daysBetween = CommonUtils.getDaysBetween(pickedDate.getTime(), CommonUtils.getToday());

                if (daysBetween >= 1 && daysBetween <= 30) {
                    pickupDate.setTime(pickedDate.getTimeInMillis());
                    validDate = true;
                } else {
                    daysBetween = 0;
                    validDate = false;
                    Toast.makeText(view.getContext(),
                            "Please select a pickup date between tomorrow and 30 days from now.", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        final Spinner vipSpinner = (Spinner) view.findViewById(R.id.preorderItem_VIPCustomerSpinner);
        final Spinner productSpinner = (Spinner) view.findViewById(R.id.preorderItem_ProductSpinner);

        ParseQuery<VIPCustomer> vipQuery = ParseQuery.getQuery(VIPCustomer.class);
        final ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("Loading VIP Customers...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        vipQuery.findInBackground(new FindCallback<VIPCustomer>() {

            public void done(List<VIPCustomer> vips, ParseException e) {
                if (e == null) {

                    List<String> vipNameList = new ArrayList<String>();
                    List<String> vipNumberList = new ArrayList<String>();
                    for (VIPCustomer vip : vips) {
                        vipNameList.add(vip.getName());
                        vipNumberList.add(vip.getVIPCustomerNumber());
                    }

                    ArrayAdapter<String> vipSpinnerAdapter = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item, vipNameList);
                    vipSpinner.setAdapter(vipSpinnerAdapter);

                    dialog.dismiss();

                } else {
                    dialog.dismiss();
                }
            }

        });

        ParseQuery<Product> productQuery = ParseQuery.getQuery(Product.class);
        productQuery.whereEqualTo("type", Product.DESSERT_TYPE);
        productQuery.whereEqualTo("active", true);

        dialog.setMessage("Loading Products...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        productQuery.findInBackground(new FindCallback<Product>() {

            public void done(List<Product> products, ParseException e) {
                if (e == null) {
                    final List<String> productNameList = new ArrayList<String>();
                    String productName;
                    for (Product prod : products) {
                    	productName = prod.getName() + " - " + CommonUtils.formatCurrency(prod.getPrice());
                        productNameList.add(productName);
                    }

                    ArrayAdapter<String> productSpinnerAdapter = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_item, productNameList);
                    productSpinner.setAdapter(productSpinnerAdapter);

                    dialog.dismiss();

                } else {
                    dialog.dismiss();
                }
            }

        });

        Button saveButton = (Button) view.findViewById(R.id.preorderItem_SaveButton);
        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String productName = productSpinner.getSelectedItem().toString();
                productName = productName.substring(0, productName.indexOf(" - "));
                
                ParseQuery<Product> productQuery = ParseQuery.getQuery(Product.class);
                productQuery.whereEqualTo("name", productName);
                productQuery.whereEqualTo("type", Product.DESSERT_TYPE);
                productQuery.whereEqualTo("active", true);

                Product prod = null;
                try {
                    prod = productQuery.getFirst();
                } catch (ParseException e1) {
                    Log.v("Error occured while retrieving product", productName, e1);
                    e1.printStackTrace();
                }

                if (prod != null) {

                    // Check if the pickup date selection is valid.
                    if (!validDate) {
                        Toast.makeText(view.getContext(),
                                "Please select a pickup date between tomorrow and 30 days from now.", Toast.LENGTH_LONG)
                                .show();
                        dialog.dismiss();
                    } else if (prod.getPreOrderSlotsAvailable(pickupDate,
                            ((MainActivity) getActivity()).getCoffeeCartLocation()) == 0) {

                        // Slots are not available.
                        Toast.makeText(view.getContext(), "Product sold out", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {

                        // Slots are available and ready for purchase.
                        dialog.setMessage("Saving Preorder...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();

                        ParseQuery<VIPCustomer> vipQuery = ParseQuery.getQuery(VIPCustomer.class);
                        vipQuery.whereEqualTo("name", vipSpinner.getSelectedItem().toString());

                        VIPCustomer selectedVIPCustomer = null;
                        try {
                            selectedVIPCustomer = vipQuery.getFirst();
                        } catch (ParseException e1) {
                            Log.v("Error occured while retrieving VIP Customer", vipSpinner.getSelectedItem()
                                    .toString(), e1);
                            e1.printStackTrace();
                        }

                        if (selectedVIPCustomer != null) {
                            Order preorder = new Order();
                            preorder.setPickupDate(pickupDate);
                            preorder.setVIPCustomer(selectedVIPCustomer);
                            preorder.setProduct(prod);
                            preorder.setType(Order.PREORDER_TYPE);
                            preorder.setAmountPaid(prod.getPrice());
                            preorder.setCoffeeCartLocation(((MainActivity) getActivity()).getCoffeeCartLocation());
                            preorder.saveInBackground(new SaveCallback() {

                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        dialog.dismiss();
                                        Toast.makeText(view.getContext(), "Purchase Saved", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(), "Purchase Not Saved", Toast.LENGTH_SHORT)
                                                .show();
                                        dialog.dismiss();
                                    }
                                }

                            });
                        } else {
                            Toast.makeText(view.getContext(),
                                    "Error occoured while saving purchase. Purchase Not Saved", Toast.LENGTH_SHORT)
                                    .show();
                            dialog.dismiss();
                        }
                    }

                } else {
                    Toast.makeText(view.getContext(), "Error occoured while saving purchase. Purchase Not Saved",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        });
        return view;
    }

    public static Fragment newInstance() {
        PreOrderFragment fragment = new PreOrderFragment();
        return fragment;
    }
}
