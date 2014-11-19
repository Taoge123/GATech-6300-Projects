package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.Order;
import gatech.cs6300.project2.objects.VIPCustomer;
import gatech.cs6300.project2.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class VIPCustomerDetailFragment extends Fragment {

    VIPCustomer vip = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.vipcustomer_detail_fragment, container, false);
        Bundle bundle = this.getArguments();
        String customer_id = bundle.getString("vipCustomerObjectId");

        ParseQuery<VIPCustomer> vipQuery = ParseQuery.getQuery(VIPCustomer.class);

        try {
            vip = vipQuery.get(customer_id);

            final TextView nameText = (TextView) view.findViewById(R.id.vipCustomerDetails_Name);
            nameText.setText(vip.getName());

            if (vip.isGold()) {
                nameText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_howard_star, 0);
                nameText.setCompoundDrawablePadding(10);
            }

            final TextView phoneText = (TextView) view.findViewById(R.id.vipCustomerDetails_Phone);
            phoneText.setText(PhoneNumberUtils.formatNumber(vip.getPhone()));

            final TextView birthdateText = (TextView) view.findViewById(R.id.vipCustomerDetails_Birthdate);
            birthdateText.setText(CommonUtils.dateToString(vip.getBirthdate()));

            final TextView vipCustomerNumberText = (TextView) view
                    .findViewById(R.id.vipCustomerDetails_VIPCustomerNumber);
            vipCustomerNumberText.setText(vip.getVIPCustomerNumber());

            final TextView pointsText = (TextView) view.findViewById(R.id.vipCustomerDetails_Points);
            pointsText.setText(String.format("%d (%d)", vip.getPoints(30), vip.getPoints(0)));

        } catch (ParseException e1) {
            // Handle gracefully
            Toast.makeText(getActivity(), "Something unexpected happened. Please try again!", Toast.LENGTH_SHORT)
                    .show();
        }

        Button deleteButton = (Button) view.findViewById(R.id.vipCustomerDetails_DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Add the buttons
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        final ProgressDialog dialog = new ProgressDialog(view.getContext());
                        dialog.setMessage("Deleting...");
                        dialog.setIndeterminate(true);
                        dialog.setCancelable(false);
                        dialog.show();

                        ParseQuery<Order> vipOrderQuery = ParseQuery.getQuery(Order.class);
                        vipOrderQuery.whereEqualTo("VIPCustomer", vip);

                        vipOrderQuery.findInBackground(new FindCallback<Order>() {

                            @Override
                            public void done(List<Order> vipOrders, ParseException e) {
                                if (e == null) {
                                    List<ParseObject> orders = new ArrayList<ParseObject>();
                                    for (Order order : vipOrders) {
                                        orders.add(order);
                                    }
                                    try {
                                        ParseObject.deleteAll(orders);
                                    } catch (ParseException e1) {
                                        e1.printStackTrace();
                                    }

                                } else {
                                    dialog.dismiss();
                                }
                            }

                        });

                        vip.deleteInBackground(new DeleteCallback() {

                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "You deleted : " + vip.getName(), Toast.LENGTH_SHORT)
                                            .show();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.popBackStack("fragBack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    dialog.dismiss();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.container, VIPCustomerManagementFragment.newInstance())
                                            .commit();
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed to delete customer!", Toast.LENGTH_SHORT)
                                            .show();
                                }

                            }

                        });
                    }
                });
                builder.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Delete VIP Customer");
                alertDialog.setMessage("Are you sure you want to delete this customer?");
                alertDialog.show();

            }
        });

        Button editButton = (Button) view.findViewById(R.id.vipCustomerDetails_EditButton);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = EditVIPCustomerFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("vipCustomerObjectId", vip.getObjectId());

                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack("fragBack").commit();

            }
        });

        Button monthlyOrderButton = (Button) view.findViewById(R.id.vipCustomerDetails_MonthlyReportButton);
        monthlyOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Order> orders = vip.getOrders(30, true);
                List<String> displayOrders = new ArrayList<String>();
                for (Order order : orders) {
                    displayOrders.add(order.getDisplayString());
                }

                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.simple_list);

                ListView lv = (ListView) dialog.findViewById(R.id.simpleList_list);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, displayOrders);
                lv.setAdapter(adapter);

                dialog.setCancelable(true);
                dialog.setTitle("Purchases");
                dialog.show();

            }
        });

        Button lifetimeOrderButton = (Button) view.findViewById(R.id.vipCustomerDetails_LifetimeReportButton);
        lifetimeOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Order> orders = vip.getOrders(0, true);
                List<String> displayOrders = new ArrayList<String>();
                for (Order order : orders) {
                    displayOrders.add(order.getDisplayString());
                }

                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.simple_list);

                ListView lv = (ListView) dialog.findViewById(R.id.simpleList_list);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, displayOrders);
                lv.setAdapter(adapter);

                dialog.setCancelable(true);
                dialog.setTitle("Purchases");
                dialog.show();

            }
        });
        return view;
    }

    public static Fragment newInstance() {
        VIPCustomerDetailFragment fragment = new VIPCustomerDetailFragment();
        return fragment;
    }

}
