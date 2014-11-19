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
import com.parse.SaveCallback;

public class AddVIPCustomerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.addvipcustomer_fragment, container, false);

        Button cancelButton = (Button) view.findViewById(R.id.addVIPCustomer_CancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container, VIPCustomerManagementFragment.newInstance())
                        .addToBackStack("fragBack").commit();
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.addVIPCustomer_SubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) view.findViewById(R.id.addVIPCustomer_NameText);
                EditText phone = (EditText) view.findViewById(R.id.addVIPCustomer_PhoneText);
                EditText birthdate = (EditText) view.findViewById(R.id.addVIPCustomer_BirthdateText);
                EditText VIPCustomerNumber = (EditText) view.findViewById(R.id.addVIPCustomer_VIPCustomerNumberText);

                VIPCustomer newVIP = new VIPCustomer();
                try {
                    newVIP.setName(name.getText().toString());
                } catch (IllegalArgumentException e1) {
                    Toast.makeText(view.getContext(), "Customer Name is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    newVIP.setPhone(phone.getText().toString());
                } catch (IllegalArgumentException e) {
                    Toast.makeText(view.getContext(), "Phone number must be at least 10 digit long", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                try {
                    newVIP.setBirthdate(CommonUtils.stringToDate(birthdate.getText().toString()));
                } catch (java.text.ParseException e2) {
                    String message = String.format("Please specify date in %s format!", CommonUtils.DATE_FORMAT);
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    newVIP.setVIPCusterNumber(VIPCustomerNumber.getText().toString());
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
                newVIP.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(view.getContext(), "VIP Customer Is Saved!", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.popBackStack("fragBack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            dialog.dismiss();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, VIPCustomerManagementFragment.newInstance()).commit();

                        } else {
                            Toast.makeText(view.getContext(), "VIP Customer Did Not Save!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    public static Fragment newInstance() {
        AddVIPCustomerFragment fragment = new AddVIPCustomerFragment();
        return fragment;
    }
}