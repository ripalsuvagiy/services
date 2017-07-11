package com.services.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.services.R;
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
import static com.services.activity.MainActivity.activity;
import static com.services.custom.ConstantData.generateDatePicker;

public class ClientDetailView extends AppCompatActivity {
    String client_id, user_id, name, email, mobile_no, landline_no, address_1, address_2, state_id, state_name, city_id, city_name, zipcode, contact_person, services_of_id, services_type_id, type_id, type_name, capacity_id, capacity_value, quantity, manufacture, refill_date, refill_due_date, rate, provider;
    EditText edtClentName, edtProviderName, edtCity, edtMobileNo, edtEmail, edtLandLineNo, edtAddress1, edtAddress2, edtZipcode, edtContactPerson, edtQuantity, edtManufacture, edtRate;
    TextView txtReilDate, txtRefilDueDate;
    Spinner spinnerType, spinnerCity, spinnerState, spinnerCapacity;
    RadioButton rbNew, rbRefil, rbOwn, rbOther;
    Button btnUpdate, btnDownlode;
    RadioGroup radioGrpServiceOf, radioGrpServiceTyp;
    ArrayList<String> spinnerClirntTypeList;
    ArrayList<String> spinnerStateList, spinnerCityList, spinnerCatagoryList;
    LinearLayout linearDatePickerRelifDate, linearDatePickerRelifDueDate;
    ArrayList<StateBean> stateList = new ArrayList<>();
    String[] namesArrState;
    String[] nameArrCity;
    ArrayList<CityBean> cityList = new ArrayList<>();
    ArrayList<TypeBean> typeList = new ArrayList<>();
    String[] namesArrType;
    String[] nameArrCapacity;
    ArrayList<CapacityBean> capacityList = new ArrayList<>();
    String a1, a2;

    String strSelectedStateId, strSelectedStateName, strSelectedCityId, strSelectedCityName;
    String strSelectedTypeId, strSelectedTypeName, strSelectedCapacityId, strSelectedCapacityName;
    String strClientname, strEmail, strMobile_no, strLandline_no, strAddress_1, strAddress_2, strState_id, strState_name, strCity_id, strCity_name, strZipcode, strContact_person, strServices_of_id, strServices_type_id, strType_id, strType_name, strCapacity_id, strCapacity_value, strQuantity, strManufacture, strRefill_date, strRefill_due_date, strRate, strProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail_view);


        client_id = getIntent().getStringExtra("client_id");
        name = getIntent().getStringExtra("client_name");
        email = getIntent().getStringExtra("email");
        mobile_no = getIntent().getStringExtra("mobile_no");
        landline_no = getIntent().getStringExtra("landline_no");
        address_1 = getIntent().getStringExtra("address_1");
        address_2 = getIntent().getStringExtra("address_2");
        state_id = getIntent().getStringExtra("state_id");
        state_name = getIntent().getStringExtra("state_name");
        city_id = getIntent().getStringExtra("city_id");
        city_name = getIntent().getStringExtra("city_name");
        zipcode = getIntent().getStringExtra("zipcode");
        contact_person = getIntent().getStringExtra("contact_person");
        services_of_id = getIntent().getStringExtra("services_of_id");
        services_type_id = getIntent().getStringExtra("services_type_id");
        type_id = getIntent().getStringExtra("type_id");
        type_name = getIntent().getStringExtra("type_name");
        capacity_id = getIntent().getStringExtra("capacity_id");
        capacity_value = getIntent().getStringExtra("capacity_value");
        quantity = getIntent().getStringExtra("quantity");
        manufacture = getIntent().getStringExtra("manufacture");
        refill_date = getIntent().getStringExtra("refill_date");
        refill_due_date = getIntent().getStringExtra("refill_due_date");
        rate = getIntent().getStringExtra("rate");
        provider = getIntent().getStringExtra("provider");


        edtClentName = (EditText) findViewById(R.id.edt_client_name);
        edtProviderName = (EditText) findViewById(R.id.edt_provider_name);
        edtMobileNo = (EditText) findViewById(R.id.edt_mobile_no);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtLandLineNo = (EditText) findViewById(R.id.edt_ld_no);
        edtAddress1 = (EditText) findViewById(R.id.edt_address1);
        edtAddress2 = (EditText) findViewById(R.id.edt_address2);
        edtZipcode = (EditText) findViewById(R.id.edt_zipcode);
        edtContactPerson = (EditText) findViewById(R.id.edt_cperson);
        edtQuantity = (EditText) findViewById(R.id.edt_quantity);
        edtManufacture = (EditText) findViewById(R.id.edt_manufacture);
        edtRate = (EditText) findViewById(R.id.edt_rate);

        edtClentName.setText(name);
        edtProviderName.setText(provider);
        edtMobileNo.setText(mobile_no);
        edtEmail.setText(email);
        edtLandLineNo.setText(landline_no);
        edtAddress1.setText(address_1);
        edtAddress2.setText(address_2);
        edtZipcode.setText(zipcode);
        edtContactPerson.setText(contact_person);
        edtQuantity.setText(quantity);
        edtManufacture.setText(manufacture);
        edtRate.setText(rate);


        txtReilDate = (TextView) findViewById(R.id.edt_refil_date);
        txtRefilDueDate = (TextView) findViewById(R.id.edt_refil_due_date);


        txtReilDate.setText(refill_date);
        txtRefilDueDate.setText(refill_due_date);

        spinnerType = (Spinner) findViewById(R.id.spinner_client_type);
        spinnerCity = (Spinner) findViewById(R.id.spinner_city_name);
        spinnerState = (Spinner) findViewById(R.id.spinner_state);
        spinnerCapacity = (Spinner) findViewById(R.id.spinner_capacity);

        getState();
        getType();


        radioGrpServiceTyp = (RadioGroup) findViewById(R.id.radioGroup2);
        rbNew = (RadioButton) findViewById(R.id.rb_new);
        rbRefil = (RadioButton) findViewById(R.id.rb_relif);
        radioGrpServiceOf = (RadioGroup) findViewById(R.id.radioGroup);
        rbOwn = (RadioButton) findViewById(R.id.rb_own);
        rbOther = (RadioButton) findViewById(R.id.rb_other);

        if (services_of_id.equals("0")) {
            rbOwn.setChecked(true);
            rbOther.setChecked(false);
        } else if (services_of_id.equals("1")) {
            rbOwn.setChecked(false);
            rbOther.setChecked(true);
        }


        if (services_type_id.equals("0")) {
            rbNew.setChecked(true);
            rbRefil.setChecked(false);
        } else if (services_type_id.equals("1")) {
            rbNew.setChecked(false);
            rbRefil.setChecked(true);
        }

        linearDatePickerRelifDate = (LinearLayout) findViewById(R.id.linear_relif_date);
        linearDatePickerRelifDueDate = (LinearLayout) findViewById(R.id.linear_relif_due_date);

        btnUpdate = (Button) findViewById(R.id.btn_update);
//        btnDownlode=(Button) findViewById(R.id.btn_download);

//        spinnerClirntTypeList=new ArrayList<>();
//        spinnerClirntTypeList.add("1");
//        spinnerClirntTypeList.add("2");
//        spinnerClirntTypeList.add("3");
//        spinnerClirntTypeList.add("4");
//        spinnerClirntTypeList.add("5");
//
//        ArrayAdapter  dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerClirntTypeList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinneClientType.setAdapter(dataAdapter);
//
//        spinnerStateList=new ArrayList<>();
//        spinnerStateList.add("Gujarat");
//        spinnerStateList.add("Maharastr");
//        spinnerStateList.add("Panjab");
//        spinnerStateList.add("Hariyana");
//        spinnerStateList.add("Rajasthan");

//        ArrayAdapter  dataAdapterstate = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerStateList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerState.setAdapter(dataAdapterstate);
//
//        spinnerCityList=new ArrayList<>();
//        spinnerCityList.add("1");
//        spinnerCityList.add("2");
//        spinnerCityList.add("3");
//        spinnerCityList.add("4");
//        spinnerCityList.add("5");
//
//        ArrayAdapter  dataAdaptercity = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerCityList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCity.setAdapter(dataAdaptercity);
//
//
//        spinnerCatagoryList=new ArrayList<>();
//        spinnerCatagoryList.add("1");
//        spinnerCatagoryList.add("2");
//        spinnerCatagoryList.add("3");
//        spinnerCatagoryList.add("4");
//        spinnerCatagoryList.add("5");
//
//        ArrayAdapter  dataAdaptercitycatagory = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerCatagoryList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCatagory.setAdapter(dataAdaptercitycatagory);

        linearDatePickerRelifDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDatePicker(ClientDetailView.this, txtReilDate);
            }
        });


        linearDatePickerRelifDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDatePicker(ClientDetailView.this, txtRefilDueDate);
            }
        });


        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strSelectedStateId = stateList.get(i).getStateID();
                strSelectedStateName = stateList.get(i).getStateName();
                getCity(strSelectedStateId, strSelectedStateName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strSelectedCityId = cityList.get(i).getId();
                strSelectedCityName = cityList.get(i).getCityName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strSelectedTypeId = typeList.get(i).getTypeId();
                strSelectedTypeName = typeList.get(i).getTypeName();
                getCapacity(strSelectedTypeId, strSelectedTypeName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerCapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strSelectedCapacityId = capacityList.get(i).getCapacityId();
                strSelectedCapacityName = capacityList.get(i).getCapacityName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateClient();
            }
        });

        rbNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    a1 = "0";
            }
        });
        rbOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    a1 = "1";
            }
        });
        rbNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    a2 = "0";
            }
        });
        rbRefil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                    a2 = "1";
            }
        });

//        radioGrpServiceOf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                serviceOf=String.valueOf(i);
//            }
//        });
//        radioGrpServiceTyp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                serviceValue=String.valueOf(i);
//            }
//        });


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


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, namesArrState) {
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


                                    for (int i = 0; i < stateList.size(); i++) {
                                        if (stateList.get(i).getStateID().equals(state_id)) {
                                            spinnerState.setSelection(i);
                                        } else {

                                        }
                                    }


                                }
                            });


                        } else {
                            ConstantData.showAlertDialog(ClientDetailView.this, "message");
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
                String param = "function=get_city&state_id=" + StateId;
                String responce = ServerConnection.sendRequest(ServerConnection.users_url, param);
                Log.d("responce", "" + responce);
                if (responce != null) {
                    try {
                        JSONObject ob = new JSONObject(responce);
                        if (ob.getString("success").equals("yes")) {
                            JSONArray obCityArray = ob.getJSONArray("data");
                            nameArrCity = new String[obCityArray.length()];
                            for (int i = 0; i < obCityArray.length(); i++) {
                                JSONObject obCity = obCityArray.getJSONObject(i);
                                String cityID = obCity.getString("id");
                                String stateID = obCity.getString("state_id");
                                String city = obCity.getString("city_name");
                                cityList.add(new CityBean(cityID, stateID, city));
                                nameArrCity[i] = city;
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nameArrCity) {
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
                                    for (int i = 0; i < cityList.size(); i++) {
                                        if (cityList.get(i).getId().equals(city_id)) {
                                            spinnerCity.setSelection(i);
                                        } else {

                                        }
                                    }


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


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, namesArrType) {
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

                                    for (int i = 0; i < typeList.size(); i++) {
                                        if (typeList.get(i).getTypeId().equals(type_id)) {
                                            spinnerType.setSelection(i);
                                        } else {

                                        }
                                    }

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
                String param = "function=get_capacity&type_id=" + TypeId;
                String responce = ServerConnection.sendRequest(ServerConnection.users_url, param);
                Log.d("responce", "" + responce);
                if (responce != null) {
                    try {
                        JSONObject ob = new JSONObject(responce);
                        if (ob.getString("success").equals("yes")) {
                            JSONArray obCapacityArray = ob.getJSONArray("data");
                            nameArrCapacity = new String[obCapacityArray.length()];
                            for (int i = 0; i < obCapacityArray.length(); i++) {
                                JSONObject obCapacity = obCapacityArray.getJSONObject(i);
                                String capacityID = obCapacity.getString("id");
                                String typeID = obCapacity.getString("type_id");
                                String capacity = obCapacity.getString("capacity_value");
                                capacityList.add(new CapacityBean(capacityID, typeID, capacity));
                                nameArrCapacity[i] = capacity;
                            }

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    ArrayAdapter<String> adapterCapacity = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, nameArrCapacity) {
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
                                    spinnerCapacity.setAdapter(adapterCapacity);


                                    for (int i = 0; i < capacityList.size(); i++) {
                                        if (capacityList.get(i).getCapacityId().equals(capacity_id)) {
                                            spinnerCapacity.setSelection(i);
                                        } else {

                                        }
                                    }
                                }
                            });

                        } else {
                            // ConstantData.showAlertDialog(getContext(), "message");
                            activity.runOnUiThread(new Runnable() {
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

    private void updateClient() {

        strClientname = edtClentName.getText().toString().trim();
        strEmail = edtEmail.getText().toString().trim();
        strMobile_no = edtMobileNo.getText().toString().trim();
        strLandline_no = edtLandLineNo.getText().toString().trim();
        strAddress_1 = edtAddress1.getText().toString().trim();
        strAddress_2 = edtAddress1.getText().toString().trim();
        strState_id = strSelectedStateId;
        strState_name = strSelectedStateName;
        strCity_id = strSelectedCityId;
        strCity_name = strSelectedCityName;
        strZipcode = edtZipcode.getText().toString();
        strContact_person = edtContactPerson.getText().toString().trim();
//        strServices_of_id=a1;
//        strServices_type_id=a2;
        strType_id = strServices_type_id;
        strType_name = strSelectedTypeName;
        strCapacity_id = strSelectedCapacityId;
        strCapacity_value = strSelectedCapacityName;
        strQuantity = edtQuantity.getText().toString().trim();
        strProvider = edtProviderName.getText().toString().trim();
        strRate = edtRate.getText().toString().trim();
        strManufacture = edtManufacture.getText().toString().trim();
        strRefill_date = txtReilDate.getText().toString();
        strRefill_due_date = txtRefilDueDate.getText().toString();


        if (strClientname.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Client Name");
        else if (strEmail.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Client Email");
        else if (strMobile_no.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Client Mobile No");
        else if (strAddress_1.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Client Address");
        else if (strZipcode.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Client ZipCode");
        else if (strContact_person.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Client ContactPerson");
        else if (strQuantity.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Quentity");
        else if (strProvider.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Provider");
        else if (strRate.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Rate");
        else if (strManufacture.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Enter Manufacture");
        else if (strType_name.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Select Type");
        else if (strState_name.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Select State");
        else if (strCity_name.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Select City");
        else if (strCapacity_value.equals(""))
            ConstantData.showAlertDialog(ClientDetailView.this, "Select Capacity");
        else {
            final ProgressDialog pd = ProgressDialog.show(ClientDetailView.this, "", "Please wait..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String params = "function=update_client_info&client_id=" + client_id +
                            "&name=" + strClientname +
                            "&email=" + strEmail +
                            "&mobile_no=" + strMobile_no +
                            "&landline_no=" + strLandline_no +
                            "&address_1=" + strAddress_1 +
                            "address_2=" + strAddress_2 +
                            "&state_id=" + strState_id +
                            "&state_name=" + strState_name +
                            "&city_id=" + strCity_id +
                            "&city_name=" + strCity_name +
                            "&zipcode=" + strZipcode +
                            "&contact_person=" + strContact_person +
                            "&services_of_id=" + a1 +
                            "&services_type_id=" + a2 +
                            "&type_id" + strType_id +
                            "&type_name=" + strType_name +
                            "&capacity_id=" + strCapacity_id +
                            "capacity_value=" + strCapacity_value +
                            "&quantity=" + strQuantity +
                            "&manufacture=" + strManufacture +
                            "&refill_date=" + strRefill_date +
                            "&refill_due_date=" + strRefill_due_date +
                            "&rate=" + strRate +
                            "&provider=" + strProvider;
                    String responce = ServerConnection.sendRequest(ServerConnection.users_url, params);

                    Log.d("responce", "" + responce);

                    if (responce != null) {
                        try {
                            JSONObject jobj = new JSONObject(responce);
                            if (jobj.getString("success").equals("yes")) {

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(), "Client Info Updated Successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(ClientDetailView.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            } else {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        ConstantData.showAlertDialog(ClientDetailView.this, "Sorry! Please try again");
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.d("exception", "" + e.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    ConstantData.showAlertDialog(ClientDetailView.this, "Sorry! Something went wrong");
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                ConstantData.showAlertDialog(ClientDetailView.this, "Sorry!! Please try after some time.");
                            }
                        });
                    }

                }
            }).start();
        }

    }


}
