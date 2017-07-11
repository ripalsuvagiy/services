package com.services.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.services.R;
import com.services.activity.ClientDetailView;
import com.services.activity.EntryTab;
import com.services.activity.MainActivity;
import com.services.bean.CapacityBean;
import com.services.bean.CityBean;
import com.services.bean.StateBean;
import com.services.bean.TypeBean;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.services.activity.EntryTab.activityTabs;
import static com.services.custom.ConstantData.generateDatePicker;
import static com.services.fragment.PersonalDetail.cName;
import static com.services.fragment.PersonalDetail.id;

public class FireExtinguishersDetails extends Fragment {
    View view;
    LinearLayout linearRefilDate, linearRefilDueDate;
    EditText edtQuantity, edtManufacture, edtRate, edtProvider;
    TextView txtRelifDate, txtRelifDueDate;
    RadioButton radioButtonOWN, radioButtonNEW,radioButtonOther,radioButtonRefil;
   Spinner spinnerType, spinnCapacity;
    String[] spinnerTYPELIST = {"Type 1", "Type 2", "Type 3", "Type 4"};
    String[] spinnerCAPACITYLIST = {"Type 1", "Type 2", "Type 3", "Type 4"};
    ArrayList<TypeBean> typeList = new ArrayList<>();
    String[] namesArrType;
    String[] nameArrCapacity;
    ArrayList<CapacityBean> capacityList = new ArrayList<>();
    RadioGroup radioGroupServiceOf,radioGroupServiceOfType;
    String a1,a2,selectedTypeId,selectedCapacityId,selectedTypeName,selectedCapacityName,quentity,manufacture,rate,provider,typeSpinner,capacitySpinner,serviceOf,serviceValue
            ,refilDate,refilDueDate;
    Button btnFinish;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fire_extinguishers_details, container, false);

        linearRefilDate = (LinearLayout) view.findViewById(R.id.liner_refil_date);
        linearRefilDueDate = (LinearLayout) view.findViewById(R.id.linear_relif_due_date);
        txtRelifDate = (TextView) view.findViewById(R.id.edt_refil_date);
        txtRelifDueDate = (TextView) view.findViewById(R.id.edt_refil_due_date);
        radioGroupServiceOf=(RadioGroup)view.findViewById(R.id.radioGroup);
        radioGroupServiceOfType=(RadioGroup)view.findViewById(R.id.radioGroup2);

        radioButtonOWN = (RadioButton) view.findViewById(R.id.rb_own);
        radioButtonOther = (RadioButton) view.findViewById(R.id.rb_other);
        radioButtonNEW = (RadioButton) view.findViewById(R.id.rb_new);
        radioButtonRefil = (RadioButton) view.findViewById(R.id.rb_relif);
        edtQuantity = (EditText) view.findViewById(R.id.edt_quantity);
        edtManufacture = (EditText) view.findViewById(R.id.edt_manufacture);
        edtRate = (EditText) view.findViewById(R.id.edt_rate);
        edtProvider = (EditText) view.findViewById(R.id.edt_provider);
        btnFinish=(Button)view.findViewById(R.id.btn_finish);

        spinnerType = (Spinner) view.findViewById(R.id.spinner_type);
        spinnCapacity = (Spinner) view.findViewById(R.id.spinner_capacity);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClient();
            }
        });
        getType();
        linearRefilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDatePicker(getContext(), txtRelifDate);
            }
        });


        linearRefilDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDatePicker(getContext(), txtRelifDueDate);
            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedTypeId = typeList.get(i).getTypeId();
                selectedTypeName = typeList.get(i).getTypeName();
                getCapacity(selectedTypeId,selectedTypeName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedCapacityId = capacityList.get(i).getCapacityId();
                selectedCapacityName = capacityList.get(i).getCapacityName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioButtonOWN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                a1="0";
            }
        });
        radioButtonOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                    a1="1";
            }
        });
        radioButtonNEW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                    a2="0";
            }
        });
        radioButtonRefil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                    a2="1";
            }
        });

        return view;
    }




    private void getType() {
        typeList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String param = "function=get_type";
                String responce = ServerConnection.sendRequest(ServerConnection.users_url, param);
                Log.d("responce", "" + responce);
                if (responce != null) {
                    try {
                        JSONObject ob = new JSONObject(responce);
                        if (ob.getString("success").equals("yes")) {
                            JSONArray jArray = ob.getJSONArray("data");
                            namesArrType = new String[jArray.length()];

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject obState = jArray.getJSONObject(i);
                                String typeID = obState.getString("id");
                                String type = obState.getString("type_name");
                                typeList.add(new TypeBean(typeID, type));
                                namesArrType[i] = type;
                            }




                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,namesArrType ) {
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
                                    spinnerType.setAdapter(adapterState);

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
    private void getCapacity(final String TypeId, String selectedTypeName) {
                capacityList.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String param = "function=get_capacity&type_id="+TypeId;
                String responce = ServerConnection.sendRequest(ServerConnection.users_url, param);
                Log.d("responce", "" + responce);
                if (responce != null) {
                    try {
                        JSONObject ob = new JSONObject(responce);
                        if (ob.getString("success").equals("yes")) {
                            JSONArray obCapacityArray=ob. getJSONArray("data");
                            nameArrCapacity = new String[obCapacityArray.length()];
                            for (int i=0;i<obCapacityArray.length();i++){
                                JSONObject obCapacity=obCapacityArray.getJSONObject(i);
                                String capacityID=obCapacity.getString("id");
                                String typeID=obCapacity.getString("type_id");
                                String capacity=obCapacity.getString("capacity_value");
                                capacityList.add(new CapacityBean(capacityID,typeID, capacity));
                                nameArrCapacity[i] = capacity;
                            }

                            activityTabs.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ArrayAdapter<String> adapterCapacity = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, nameArrCapacity) {
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
                                    spinnCapacity.setAdapter(adapterCapacity);


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
        quentity = edtQuantity.getText().toString().trim();
        provider = edtProvider.getText().toString().trim();
        rate = edtRate.getText().toString().trim();
        manufacture = edtManufacture.getText().toString().trim();
        refilDate=txtRelifDate.getText().toString();
        refilDueDate=txtRelifDueDate.getText().toString();
        typeSpinner=selectedTypeName;
        capacitySpinner=selectedCapacityName;


        if(PersonalDetail.cName.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Name");
        else if(PersonalDetail.cEmail.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Email");
        else if(PersonalDetail.cMobileNo.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Mobile No");
        else if(PersonalDetail.cAddress1.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client Address");
        else if(PersonalDetail.cZipcode.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client ZipCode");
        else if(PersonalDetail.cContactPerson.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Client ContactPerson");
        else if(quentity.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Quentity");
        else if(provider.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Provider");
        else if(rate.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Rate");
        else if(manufacture.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Enter Manufacture");
        else if(typeSpinner.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Select Type");
        else if(PersonalDetail.statSpinner.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Select State");
        else if(PersonalDetail.citySpinner.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Select City");
        else if(capacitySpinner.equals(""))
            ConstantData.showAlertDialog(EntryTab.activityTabs,"Select Capacity");

        else {
            final ProgressDialog pd=ProgressDialog.show(EntryTab.activityTabs,"","Please wait..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String params="function=add_client&user_id="+ConstantData.getString(getContext(),"user_id","")+
                            "&name="+PersonalDetail.cName +
                            "&email="+PersonalDetail.cEmail+
                            "&mobile_no="+PersonalDetail.cMobileNo+
                            "&landline_no="+PersonalDetail.cLandlineNo+
                            "&address_1=" +PersonalDetail.cAddress1+
                            "&address_2="+PersonalDetail.cAddress2+
                            "&state_id="+PersonalDetail.selectedStateId+
                            "&state_name="+PersonalDetail.selectedStateName+
                            "&city_id="+PersonalDetail.selectedCityId+
                            "&city_name="+PersonalDetail.selectedCityName +
                            "&zipcode="+PersonalDetail.cZipcode+
                            "&contact_person="+PersonalDetail.cContactPerson+
                           "&services_of_id="+a1+
                            "&services_type_id="+a2 +
                            "&type_id="+selectedTypeId+
                            "&type_name="+selectedTypeName+
                            "&capacity_id="+selectedCapacityId+
                           "&capacity_value="+selectedCapacityName+
                            "&quantity="+quentity+
                            "&manufacture="+manufacture+
                            "&refill_date="+refilDate+
                            "&refill_due_date="+refilDueDate+
                            "&rate="+rate+
                            "&provider="+provider;
                    String responce= ServerConnection.sendRequest(ServerConnection.users_url,params);

                    Log.d("responce",""+responce);

                    if(responce!=null){
                        try {
                            JSONObject jobj=new JSONObject(responce);
                            if(jobj.getString("success").equals("yes"))
                            {

                                activityTabs.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Intent i=new Intent(EntryTab.activityTabs,MainActivity.class);
                                        startActivity(i);
                                        getActivity().finish();
                                    }
                                });
                            }
                            else {
                                activityTabs.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        ConstantData.showAlertDialog(getContext(),"Sorry Plese try again");
                                    }
                                });
                            }
                        }
                        catch (Exception e){
                            Log.d("exception",""+e.toString());
                        }
                    }
                }
            }).start();
        }

}



}