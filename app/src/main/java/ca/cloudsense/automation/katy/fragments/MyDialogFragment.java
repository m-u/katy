package ca.cloudsense.automation.katy.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.InputType;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ca.cloudsense.automation.katy.MainActivity;
import ca.cloudsense.automation.katy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MyDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private WifiManager wm;
    private WifiScanReceiver wscan;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDialogFragment newInstance(String param1, String param2) {
        MyDialogFragment fragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public MyDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wm = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        if (wm.isWifiEnabled() == false)
        {
            // If wifi disabled then enable it
            Toast.makeText(getActivity().getApplicationContext(), "wifi is disabled..making it enabled",
                    Toast.LENGTH_LONG).show();
            wm.setWifiEnabled(true);
        }

        wscan =new WifiScanReceiver();
        getActivity().registerReceiver(wscan, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wm.startScan();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getDialog().setTitle(R.string.dialogfragment_title);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //return super.onCreateDialog(savedInstanceState);
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog, null);

        //Set up the listener for checkbox within this fragment
        CheckBox cb = (CheckBox)v.findViewById(R.id.checkBox);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });

        //Fill the spinner with the appropriate array of available networks

        //Spinner sp = (Spinner)v.findViewById(R.id.spinner);
       // String networks[] = {"wsand", "wsand-ext", "amp24", "amp40", "wvlan"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, networks);
        //sp.setAdapter(adapter);

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher)
                .setTitle(R.string.dialogfragment_title)
                .setView(v)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                               ((MainActivity)getActivity()).doPositiveClick();

                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox)view).isChecked();
        EditText password = (EditText) getDialog().findViewById(R.id.editText);
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    //Password is visible
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password.setSelection(password.getText().length());
                }
                else {
                    //Password is hidden
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.getText().length());
                }
                break;
            // TODO: Veggie sandwich
        }
    }


    class WifiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            List<ScanResult> wifiScanList = wm.getScanResults();
            String wifis[] = new String[wifiScanList.size()];
            for (int i = 0; i < wifiScanList.size(); i++)
            {
                wifis[i] = ((wifiScanList.get(i)).SSID.toString());
            }

            Spinner sp = (Spinner)getDialog().findViewById(R.id.spinner);
            //String networks[] = {"wsand", "wsand-ext", "amp24", "amp40", "wvlan"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, wifis);
            sp.setAdapter(adapter);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
