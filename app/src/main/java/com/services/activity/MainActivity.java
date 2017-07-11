package com.services.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.services.R;
import com.services.adapter.ClientEntryAdapter;
import com.services.bean.ClientEntryBean;
import com.services.bean.TypeBean;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.R.id.list;
import static com.services.custom.ConstantData.generateDatePicker;

public class MainActivity extends AppCompatActivity {
    ArrayList<ClientEntryBean> clientList = new ArrayList<>();
    LinearLayout linearClineNaneSearch;
    LinearLayout linearRefilDateSearch;
    public static AppCompatActivity activity;
    String client_id, user_id, name, email, mobile_no, landline_no, address_1, address_2, state_id, state_name, city_id, city_name, zipcode, contact_person, services_of_id, services_type_id, type_id, type_name, capacity_id, capacity_value, quantity, manufacture, refill_date, refill_due_date, rate, provider;
    ListView lvClientEntry;
    TextView txtNoRecords, txtRefill;
    EditText edtProviderOrClientNm;
    ImageView imgSearchByRefillDate, imgSearchByProviderName,imgGo;
    String[] searchArray;
    int mYear;
    int mMonth;
    int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity = MainActivity.this;
        linearClineNaneSearch = (LinearLayout) findViewById(R.id.linear_client_name_serch);
        linearRefilDateSearch = (LinearLayout) findViewById(R.id.linear_refil_search);
        lvClientEntry = (ListView) findViewById(R.id.lv_entry_record);
        txtNoRecords = (TextView) findViewById(R.id.textNoRecordsFound);
        imgSearchByRefillDate = (ImageView) findViewById(R.id.imageView_refill_date);
        imgSearchByProviderName = (ImageView) findViewById(R.id.imageView_client);
        txtRefill = (TextView) findViewById(R.id.edt_refil_due_search);
        imgGo=(ImageView)findViewById(R.id.imgGo);
        edtProviderOrClientNm = (EditText) findViewById(R.id.edt_client_name);
        LinearLayout linearNewEntry = (LinearLayout) findViewById(R.id.linear_new_entry);

        linearNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EntryTab.class);
                startActivity(i);
            }
        });
        getClientList();


        imgGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_by = "1";
                String queryListener = edtProviderOrClientNm.getText().toString().trim();
                if (queryListener.equals("")) {
                    ConstantData.showAlertDialog(MainActivity.this, "Please enter client name or provider name");
                } else {
                    getSearchData(search_by, queryListener);
                }


            }
        });
        linearRefilDateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txtRefill.setText(year + "-" + ConstantData.getDateFormat("M", "MM", String.valueOf((monthOfYear + 1))) + "-" + ConstantData.getDateFormat("d", "dd", String.valueOf(dayOfMonth)));
                                String search_by = "0";
                                String queryListener = txtRefill.getText().toString().trim();
                                getSearchData(search_by, queryListener);
//                        final String change_date = edtDate.getText().toString();


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

//        imgSearchByRefillDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String search_by = "0";
//                String queryListener = txtRefill.getText().toString().trim();
//                if (queryListener.equals("")) {
//                    ConstantData.showAlertDialog(MainActivity.this, "Please enter date");
//                } else {
//                    getSearchData(search_by, queryListener);
//                }
//            }
//        });

    }

    private void getSearchData(final String search_by, final String queryListener) {
        clientList.clear();
        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Please wait...");
        new Thread(new Runnable() {
            @Override
            public void run() {

                String params = "function=get_search_data&search_by=" + search_by + "&queryListener=" + queryListener + "&user_id=" + ConstantData.getString(getApplicationContext(), "user_id", "");

                String response = ServerConnection.sendRequest(ServerConnection.users_url, params);
                if (response != null) {

                    try {
//                          for (int i = 0; i < jArray.length(); i++) {
//                                JSONObject obState = jArray.getJSONObject(i);
//                                String typeID = obState.getString("id");
//                                String type = obState.getString("type_name");
//                                typeList.add(new TypeBean(typeID, type));
//                                namesArrType[i] = type;
//                            }

                        JSONObject ob = new JSONObject(response);

                        if (ob.getString("success").equals("yes")) {
                            JSONArray arrayJson = ob.getJSONArray("data");

                            searchArray = new String[arrayJson.length()];

                            for (int i = 0; i < arrayJson.length(); i++) {

                                JSONObject kj = arrayJson.getJSONObject(i);


                                client_id = kj.getString("id");
                                user_id = kj.getString("user_id");
                                name = kj.getString("name");
                                email = kj.getString("email");
                                mobile_no = kj.getString("mobile_no");
                                landline_no = kj.getString("landline_no");
                                address_1 = kj.getString("address_1");
                                address_2 = kj.getString("address_2");
                                state_id = kj.getString("state_id");
                                state_name = kj.getString("state_name");
                                city_id = kj.getString("city_id");
                                city_name = kj.getString("city_name");
                                zipcode = kj.getString("zipcode");
                                contact_person = kj.getString("contact_person");
                                services_of_id = kj.getString("services_of_id");
                                services_type_id = kj.getString("service_type_id");
                                type_id = kj.getString("type_id");
                                type_name = kj.getString("type_name");
                                capacity_id = kj.getString("capacity_id");
                                capacity_value = kj.getString("capacity_value");
                                quantity = kj.getString("quantity");
                                manufacture = kj.getString("manufacture");
                                refill_date = kj.getString("refill_date");
                                refill_due_date = kj.getString("refill_due_date");
                                rate = kj.getString("rate");
                                provider = kj.getString("provider");
                                clientList.clear();
                                clientList.add(new ClientEntryBean(client_id, user_id, name, email, mobile_no, landline_no, address_1, address_2, state_id, state_name, city_id, city_name, zipcode, contact_person, services_of_id, services_type_id, type_id, type_name, capacity_id, capacity_value, quantity, manufacture, refill_date, refill_due_date, rate, provider));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    txtNoRecords.setVisibility(View.GONE);
                                    ClientEntryAdapter adaper = new ClientEntryAdapter((getApplicationContext()), R.layout.layout_entry, clientList);
                                    lvClientEntry.setAdapter(adaper);

                                }
                            });

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    lvClientEntry.setVisibility(View.GONE);
                                    txtNoRecords.setVisibility(View.VISIBLE);
                                }
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                ConstantData.showAlertDialog(MainActivity.this, "Sorry! Please try again");
                            }
                        });
                    }


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            ConstantData.showAlertDialog(MainActivity.this, "Sorry! No records found");
                        }
                    });
                }
            }
        }).start();
    }

    private void getClientList() {
        clientList.clear();
        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Please wait...");
        new Thread(new Runnable() {
            @Override
            public void run() {

                String params = "function=get_client_of_user&user_id=" + ConstantData.getString(getApplicationContext(), "user_id", "");
                //
                String response = ServerConnection.sendRequest(ServerConnection.users_url, params);
                if (response != null) {

                    try {
                        JSONObject ob = new JSONObject(response);

                        if (ob.getString("success").equals("yes")) {
                            JSONArray arrayJson = ob.getJSONArray("data");

                            for (int i = 0; i < arrayJson.length(); i++) {

                                JSONObject kj = arrayJson.getJSONObject(i);


                                client_id = kj.getString("id");
                                user_id = kj.getString("user_id");
                                name = kj.getString("name");
                                email = kj.getString("email");
                                mobile_no = kj.getString("mobile_no");
                                landline_no = kj.getString("landline_no");
                                address_1 = kj.getString("address_1");
                                address_2 = kj.getString("address_2");
                                state_id = kj.getString("state_id");
                                state_name = kj.getString("state_name");
                                city_id = kj.getString("city_id");
                                city_name = kj.getString("city_name");
                                zipcode = kj.getString("zipcode");
                                contact_person = kj.getString("contact_person");
                                services_of_id = kj.getString("services_of_id");
                                services_type_id = kj.getString("service_type_id");
                                type_id = kj.getString("type_id");
                                type_name = kj.getString("type_name");
                                capacity_id = kj.getString("capacity_id");
                                capacity_value = kj.getString("capacity_value");
                                quantity = kj.getString("quantity");
                                manufacture = kj.getString("manufacture");
                                refill_date = kj.getString("refill_date");
                                refill_due_date = kj.getString("refill_due_date");
                                rate = kj.getString("rate");
                                provider = kj.getString("provider");

                                clientList.add(new ClientEntryBean(client_id, user_id, name, email, mobile_no, landline_no, address_1, address_2, state_id, state_name, city_id, city_name, zipcode, contact_person, services_of_id, services_type_id, type_id, type_name, capacity_id, capacity_value, quantity, manufacture, refill_date, refill_due_date, rate, provider));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    txtNoRecords.setVisibility(View.GONE);
                                    ClientEntryAdapter adaper = new ClientEntryAdapter((getApplicationContext()), R.layout.layout_entry, clientList);
                                    lvClientEntry.setAdapter(adaper);

                                }
                            });

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    lvClientEntry.setVisibility(View.GONE);
                                    txtNoRecords.setVisibility(View.VISIBLE);
                                }
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                ConstantData.showAlertDialog(MainActivity.this, "Sorry! Please try again");
                            }
                        });
                    }


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            ConstantData.showAlertDialog(MainActivity.this, "Sorry! No records found");
                        }
                    });
                }
            }
        }).start();
    }

    private void doLogout() {

        final MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                .title("Really Logout?")
                .content("Do you want to logout?")
                .positiveText("Ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SharedPreferences.Editor sh = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                        sh.clear();
                        sh.commit();

                        System.exit(0);
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);

                    }
                })
                .negativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);

        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.search_by_name:
                // search action

                linearRefilDateSearch.setVisibility(View.GONE);
                linearClineNaneSearch.setVisibility(View.VISIBLE);
                return true;
            case R.id.search_by_date:

                linearClineNaneSearch.setVisibility(View.GONE);
                linearRefilDateSearch.setVisibility(View.VISIBLE);
                return true;
            case R.id.search_by_all:

                linearClineNaneSearch.setVisibility(View.GONE);
                linearRefilDateSearch.setVisibility(View.GONE);
                getClientList();
                return true;
            case R.id.action_logout:
                doLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
