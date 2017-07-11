package com.services.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.services.R;
import com.services.activity.EntryTab;
import com.services.activity.LoginScreen;
import com.services.activity.MainActivity;
import com.services.activity.Registration;
import com.services.bean.CityBean;
import com.services.bean.StateBean;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.services.activity.EntryTab.activityTabs;
import static com.services.activity.EntryTab.changeFragment;

public class PersonalDetail extends Fragment {
    View view;
    EditText edtClientName, edtCEmail, edtCMobileNo, edtLandLineNo, edtAddress1, edtAddress2, edtZipCode, edtContactPerson;
    public static String id,cName="", cEmail="", cMobileNo="", cLandlineNo="", cAddress1="", cAddress2="", cZipcode="", cContactPerson="", statSpinner="",citySpinner="",cityID="",selectedStateId="",selectedStateName="",selectedCityId="",selectedCityName="";
    Spinner spinnerState, spinnerCity, spinnerType, spinnerCapacity;
    ArrayList<StateBean> stateList = new ArrayList<>();
    String[] namesArrState;
    String[] nameArrCity;
    ArrayList<CityBean> cityList = new ArrayList<>();
    Button btnNext;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_detail, container, false);
        edtClientName = (EditText) view.findViewById(R.id.edt_cname);
        edtCEmail = (EditText) view.findViewById(R.id.edt_cemail);
        edtCMobileNo = (EditText) view.findViewById(R.id.edt_cmobileno);
        edtLandLineNo = (EditText) view.findViewById(R.id.edt_ldno);
        edtAddress1 = (EditText) view.findViewById(R.id.edt_address1);
        edtAddress2 = (EditText) view.findViewById(R.id.edt_address2);
        spinnerState = (Spinner) view.findViewById(R.id.spinner_state);
        spinnerCity = (Spinner) view.findViewById(R.id.spinner_city);
        edtZipCode = (EditText) view.findViewById(R.id.edt_zipcode);
        edtContactPerson = (EditText) view.findViewById(R.id.edt_contactperson);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        getState();


        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 selectedStateId = stateList.get(i).getStateID();
                 selectedStateName = stateList.get(i).getStateName();
                getCity(selectedStateId,selectedStateName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedCityId=cityList.get(i).getId();
                selectedCityName=cityList.get(i).getCityName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClient();
            }
        });
        return view;
    }

    private void getState() {
        stateList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String param = "function=get_state";
                String responce = ServerConnection.sendRequest(ServerConnection.users_url, param);
                Log.d("responce", "" + responce);
                if (responce != null) {
                    try {
                        JSONObject ob = new JSONObject(responce);
                        if (ob.getString("success").equals("yes")) {
                            JSONArray jArray = ob.getJSONArray("data");
                             namesArrState = new String[jArray.length()];

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject obState = jArray.getJSONObject(i);
                                String stateID = obState.getString("id");
                                String state = obState.getString("state_name");
                                stateList.add(new StateBean(stateID, state));
                                namesArrState[i] = state;
                            }




                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,namesArrState ) {
                                        @NonNull
                                        @Override
                                        public View getView(int position, View convertView, ViewGroup parent) {

                                            View v = super.getView(position, convertView, parent);
                                            // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                            ((TextView) v).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                            return v;
                                        }


                                        @Override
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View v = super.getView(position, convertView, parent);
                                            ((TextView) v).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                            return v;
                                        }
                                    };
                                    spinnerState.setAdapter(adapterState);

                                }
                            });


                        } else {
                           // ConstantData.showAlertDialog(getContext(), "message");
                            activityTabs.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // spinnerState.setText("");
                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.d("exception", "" + e.toString());
                    }
                }

            }
        }).start();
    }

    public void getCity(final String StateId, String selectedStateName) {

       cityList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String param = "function=get_city&state_id="+StateId;
                    String responce = ServerConnection.sendRequest(ServerConnection.users_url, param);
                Log.d("responce", "" + responce);
                if (responce != null) {
                    try {
                        JSONObject ob = new JSONObject(responce);
                        if (ob.getString("success").equals("yes")) {
                            JSONArray obCityArray=ob. getJSONArray("data");
                            nameArrCity = new String[obCityArray.length()];
                            for (int i=0;i<obCityArray.length();i++){
                               JSONObject obCity=obCityArray.getJSONObject(i);
                                String cityID=obCity.getString("id");
                                String stateID=obCity.getString("state_id");
                                String city=obCity.getString("city_name");
                                cityList.add(new CityBean(cityID,stateID, city));
                                nameArrCity[i] = city;
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, nameArrCity) {
                                        @NonNull
                                         @Override
                                        public View getView(int position, View convertView, ViewGroup parent) {

                                            View v = super.getView(position, convertView, parent);
                                            // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                            ((TextView) v).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                            return v;
                                        }


                                        @Override
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            View v = super.getView(position, convertView, parent);
                                            ((TextView) v).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                            return v;
                                        }
                                    };
                                    spinnerCity.setAdapter(adapterCity);


                                }
                            });

                        } else {
                           // ConstantData.showAlertDialog(getContext(), "message");
                            activityTabs.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                  //  spinnerCity.setText("");
                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.d("exception", "" + e.toString());
                    }
                }


            }
        }).start();
    }


    private void addClient() {
        cName = edtClientName.getText().toString().trim();
        cEmail = edtCEmail.getText().toString().trim();
        cMobileNo = edtCMobileNo.getText().toString().trim();
        cLandlineNo = edtLandLineNo.getText().toString().trim();
        cAddress1 = edtAddress1.getText().toString().trim();
        cAddress2 = edtAddress2.getText().toString().trim();
        cZipcode = edtZipCode.getText().toString().trim();
        cContactPerson = edtContactPerson.getText().toString().trim();
        statSpinner=selectedStateName;
        citySpinner=selectedCityName;

        cContactPerson=edtContactPerson.getText().toString().trim();

        if(cName.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Name");
        else if(cEmail.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Email");
        else if (ConstantData.emailValidation(cEmail).equals("false"))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Please enter valid email");
        else if(cMobileNo.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Mobile No");
        else if(cMobileNo.length()<10||cMobileNo.length()>10)
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Valid Mobile No");
        else if(cAddress1.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Address");
        else if(statSpinner.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Select State");
        else if(citySpinner.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Select City");
        else if(cZipcode.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client ZipCode");
        else if(cContactPerson.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client ContactPerson");

        else {
                changeFragment(new FireExtinguishersDetails());
          //  EntryTab.viewPager.setCurrentItem(1,true);
        }
}
}
