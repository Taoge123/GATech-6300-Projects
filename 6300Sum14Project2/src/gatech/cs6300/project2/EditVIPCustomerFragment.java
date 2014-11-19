package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.VIPCustomer;
import gatech.cs6300.project2.util.CommonUtils;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class EditVIPCustomerFragment extends Fragment {

    VIPCustomer vip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.editvipcustomer_fragment, container, false);
        Bundle bundle = this.getArguments();
        String customer_id = bundle.getString("vipCustomerObjectId");
        ParseQuery<VIPCustomer> vipQuery = ParseQuery.getQuery(VIPCustomer.class);

        try {
            vip = vipQuery.get(customer_id);
            EditText vipName = (EditText) view.findViewById(R.id.editVIPCustomer_Name);
            vipName.setText(vip.getName());
            EditText vipPhone = (EditText) view.findViewById(R.id.editVIPCustomer_Phone);
            vipPhone.setText(vip.getPhone());
            EditText vipBirthdate = (EditText) view.findViewById(R.id.editVIPCustomer_Birthdate);
            vipBirthdate.setText(CommonUtils.dateToString(vip.getBirthdate()));
            EditText vipCustomerNumber = (EditText) view.findViewById(R.id.editVIPCustomer_VIPCustomerNumber);
            vipCustomerNumber.setText(vip.getVIPCustomerNumber());

        } catch (ParseException e1) {
            // Handle gracefully
            Toast.makeText(getActivity(), "Something unexpected happened. Please try again!", Toast.LENGTH_SHORT)
                    .show();
        }

        Button saveButton = (Button) view.findViewById(R.id.editVIPCustomer_SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText vipName = (EditText) view.findViewById(R.id.editVIPCustomer_Name);
                try {
                    vip.setName(vipName.getText().toString());
                } catch (IllegalArgumentException e1) {
                    Toast.makeText(view.getContext(), "Customer Name is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText vipPhone = (EditText) view.findViewById(R.id.editVIPCustomer_Phone);
                try {
                    vip.setPhone(vipPhone.getText().toString());
                } catch (IllegalArgumentException e3) {
                    Toast.makeText(view.getContext(), "Phone number must be at least 10 digit long", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                EditText vipBirthdate = (EditText) view.findViewById(R.id.editVIPCustomer_Birthdate);
                try {
                    vip.setBirthdate(CommonUtils.stringToDate(vipBirthdate.getText().toString()));
                } catch (java.text.ParseException e2) {
                    String message = String.format("Please specify date in %s format!", CommonUtils.DATE_FORMAT);
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText vipCustomerNumber = (EditText) view.findViewById(R.id.editVIPCustomer_VIPCustomerNumber);
                try {
                    vip.setVIPCusterNumber(vipCustomerNumber.getText().toString());
                } catch (IllegalArgumentException e1) {
                    Toast.makeText(view.getContext(), "Customer Number must be alphanumeric 10 digit number!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setMessage("Saving...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
                vip.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "You updated : " + vip.getName(), Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.popBackStack("fragBack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            dialog.dismiss();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, VIPCustomerManagementFragment.newInstance()).commit();
                        } else {

                        }
                    }

                });
            }
        });

        return view;
    }

    public static Fragment newInstance() {
        EditVIPCustomerFragment fragment = new EditVIPCustomerFragment();
        return fragment;
    }
}
