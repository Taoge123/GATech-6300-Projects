package gatech.cs6300.project2;

import gatech.cs6300.project2.objects.VIPCustomer;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class VIPCustomerManagementFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.vipcustomer_mangement_fragment, container, false);
        Button addButton = (Button) view.findViewById(R.id.addVIPCustomerButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("AddButton: ", "Clicked");
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.container, AddVIPCustomerFragment.newInstance())
                        .addToBackStack("fragBack").commit();
            }
        });

        final ListView listView = (ListView) view.findViewById(R.id.vipCustomerListView);
        ParseQuery<VIPCustomer> vipQuery = ParseQuery.getQuery(VIPCustomer.class);
        final ProgressDialog dialog = new ProgressDialog(view.getContext());
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        vipQuery.findInBackground(new FindCallback<VIPCustomer>() {

            @Override
            public void done(final List<VIPCustomer> objects, ParseException e) {
                if (e == null) {
                    ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_2,
                            android.R.id.text1, objects) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                            text1.setText(objects.get(position).getName());
                            text2.setText(objects.get(position).getVIPCustomerNumber());
                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            VIPCustomer vip = (VIPCustomer) listView.getItemAtPosition(position);
                            Toast.makeText(getActivity(), "You selected : " + vip.getName(), Toast.LENGTH_SHORT).show();
                            Fragment fragment = VIPCustomerDetailFragment.newInstance();
                            Bundle bundle = new Bundle();
                            bundle.putString("vipCustomerObjectId", vip.getObjectId());

                            fragment.setArguments(bundle);

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack("fragBack")
                                    .commit();

                        }
                    });
                    dialog.dismiss();

                }
            }

        });

        return view;
    }

    public static Fragment newInstance() {
        VIPCustomerManagementFragment fragment = new VIPCustomerManagementFragment();
        return fragment;
    }
}
