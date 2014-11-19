package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.CoffeeCartLocation;

import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class SelectCoffeeCartLocationFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.selectcoffeecartlocation_fragment, container, false);
        final Spinner spinner = (Spinner) view.findViewById(R.id.setCoffeeCartLocation_Spinner);

        if ((boolean) ((MainActivity) getActivity()).isConnectedToNetwork()) {
            ParseQuery<CoffeeCartLocation> locationQuery = ParseQuery.getQuery(CoffeeCartLocation.class);
            final ProgressDialog dialog = new ProgressDialog(view.getContext());
            dialog.setMessage("Loading Coffee Cart Locations...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            locationQuery.findInBackground(new FindCallback<CoffeeCartLocation>() {

                public void done(List<CoffeeCartLocation> locations, ParseException e) {
                    if (e == null) {

                        ArrayAdapter<CoffeeCartLocation> spinnerAdapter = new ArrayAdapter<CoffeeCartLocation>(view
                                .getContext(), android.R.layout.simple_spinner_item, locations);

                        spinner.setAdapter(spinnerAdapter);

                        CoffeeCartLocation location = (CoffeeCartLocation) ((MainActivity) getActivity())
                                .getCoffeeCartLocation();

                        int spinnerPosition = spinnerAdapter.getPosition(location);
                        spinner.setSelection(spinnerPosition);

                        dialog.dismiss();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "Database is currently unavailable", Toast.LENGTH_LONG)
                                .show();
                    }
                }

            });

        } else {
            Toast.makeText(
                    view.getContext(),
                    "Please Connected to Network - Can not change Coffee Cart Location until there is a Network Connection.",
                    Toast.LENGTH_SHORT).show();
        }

        Button saveButton = (Button) view.findViewById(R.id.setCoffeeCartLocation_SaveButton);
        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CoffeeCartLocation currentLocation = (CoffeeCartLocation) spinner.getSelectedItem();
                ((MainActivity) getActivity()).setCoffeeCartLocation(currentLocation);
            }

        });

        return view;
    }

    public static Fragment newInstance() {
        SelectCoffeeCartLocationFragment fragment = new SelectCoffeeCartLocationFragment();
        return fragment;
    }

}
