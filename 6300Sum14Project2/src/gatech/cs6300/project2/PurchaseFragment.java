package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.Order;
import gatech.cs6300.project2.objects.Product;
import gatech.cs6300.project2.objects.VIPCustomer;
import gatech.cs6300.project2.util.CommonUtils;

import java.util.ArrayList;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class PurchaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.purchase_fragment, container, false);
        String currentLocation = ((MainActivity) getActivity()).getCoffeeCartLocation().getLocation();
        if (currentLocation == null) {
            Toast.makeText(view.getContext(), "Please Set Your Coffee Cart Location", Toast.LENGTH_LONG).show();

        } else {
            TextView loc = (TextView) view.findViewById(R.id.purchaseItem_Location);
            loc.setText(currentLocation);
        }
        final Spinner vipSpinner = (Spinner) view.findViewById(R.id.purchaseItem_VIPCustomerSpinner);
        final Spinner productSpinner = (Spinner) view.findViewById(R.id.purchaseItem_ProductSpinner);

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

        Button saveButton = (Button) view.findViewById(R.id.purchaseItem_SaveButton);
        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String selectedProductName = productSpinner.getSelectedItem().toString();
                selectedProductName = selectedProductName.substring(0, selectedProductName.indexOf(" - "));
                
                ParseQuery<Product> productQuery = ParseQuery.getQuery(Product.class);
                productQuery.whereEqualTo("name", selectedProductName);
                productQuery.whereEqualTo("active", true);

                dialog.setMessage("Loading Product...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();

                Product prod = null;
                try {
                    prod = productQuery.getFirst();
                } catch (ParseException e1) {
                    Log.v("Error occured while retrieving product", selectedProductName, e1);
                    e1.printStackTrace();
                }

                if (prod != null) {

                    ParseQuery<VIPCustomer> vipQuery = ParseQuery.getQuery(VIPCustomer.class);
                    vipQuery.whereEqualTo("name", vipSpinner.getSelectedItem().toString());

                    VIPCustomer selectedVIPCustomer = null;
                    try {
                        selectedVIPCustomer = vipQuery.getFirst();
                    } catch (ParseException e1) {
                        Log.v("Error occured while retrieving VIP Customer", vipSpinner.getSelectedItem().toString(),
                                e1);
                        e1.printStackTrace();
                    }

                    if (selectedVIPCustomer != null) {

                        Order purchaseOrder = new Order();
                        purchaseOrder.setVIPCustomer(selectedVIPCustomer);
                        purchaseOrder.setProduct(prod);
                        
                        if ( prod.getName().toUpperCase().contains("REFILL")){
                        	purchaseOrder.setAmountPaid(prod.getRefillPrice(selectedVIPCustomer));
                        }else{
                        	purchaseOrder.setAmountPaid(prod.getPrice());
                        }
                        
                        purchaseOrder.setType(Order.PURCHASE_TYPE);
                        purchaseOrder.setCoffeeCartLocation(((MainActivity) getActivity()).getCoffeeCartLocation());
                        purchaseOrder.saveInBackground(new SaveCallback() {

                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    dialog.dismiss();
                                    Toast.makeText(view.getContext(), "Purchase Saved", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(view.getContext(), "Purchase Not Saved", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }

                        });
                    } else {
                        Toast.makeText(view.getContext(), "Error occoured while saving purchase. Purchase Not Saved",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
        PurchaseFragment fragment = new PurchaseFragment();
        return fragment;
    }
}