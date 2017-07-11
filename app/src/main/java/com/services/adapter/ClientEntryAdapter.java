package com.services.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.services.R;
import com.services.activity.ClientDetailView;
import com.services.activity.MainActivity;
import com.services.bean.ClientEntryBean;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.services.activity.MainActivity.activity;

/**
 * Created by ripal on 4/26/2017.
 */

public class ClientEntryAdapter extends ArrayAdapter<ClientEntryBean> {
    Context context;
    ArrayList<ClientEntryBean> list=new ArrayList<>();
    public ClientEntryAdapter(Context context, int resource, ArrayList<ClientEntryBean> list) {
        super(context, resource, list);
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater infalInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = infalInflater.inflate(R.layout.layout_entry, parent, false);
        TextView txtClientName=(TextView)view.findViewById(R.id.txt_client);
        TextView txtProviderName=(TextView)view.findViewById(R.id.txt_provider_name);
        TextView txtRelifDueDate=(TextView)view.findViewById(R.id.txt_due_date);
        ImageView imgEdit=(ImageView)view.findViewById(R.id.img_edit);
        ImageView imgDelete=(ImageView)view.findViewById(R.id.edt_delete);
        final ClientEntryBean bean=list.get(position);

        txtClientName.setText(bean.getClientName());
        txtProviderName.setText(bean.getProvider());
        txtRelifDueDate.setText(bean.getRefillDueDate());
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ClientEntryBean bean=list.get(position);
                Intent i=new Intent(MainActivity.activity, ClientDetailView.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String client_id=bean.getId();
                String client_name=bean.getClientName();
                String email=bean.getEmail();
                String mobile_no=bean.getMobileNo();
                String landline_no=bean.getLandlineNo();
                String address_1=bean.getAddress1();
                String address_2=bean.getAddress2();
                String state_id=bean.getStateId();
                String state_name=bean.getStateName();
                String city_id=bean.getCityId();
                String city_name=bean.getCityName();
                String zipcode=bean.getZipcode();
                //String zipcode=bean.getZipcode();
                String contact_person=bean.getContactPerson();
                String services_of_id=bean.getServicesOfId();
                String services_type_id=bean.getServicesTypeId();
                String type_id=bean.getTypeId();
                String type_name=bean.getTypeName();
                String capacity_id=bean.getCapacityId();
                String capacity_value=bean.getCapacityValue();
                String quantity=bean.getQuantity();
                String manufacture=bean.getManufacture();
                String refill_date=bean.getRefillDate();
                String refill_due_date=bean.getRefillDueDate();
                String rate=bean.getRate();
                String provider=bean.getProvider();

                i.putExtra("client_id",client_id);
                i.putExtra("client_name",client_name);
                i.putExtra("email",email);
                i.putExtra("mobile_no",mobile_no);
                i.putExtra("landline_no",landline_no);
                i.putExtra("address_1",address_1);
                i.putExtra("address_2",address_2);
                i.putExtra("state_id",state_id);
                i.putExtra("state_name",state_name);
                i.putExtra("city_id",city_id);
                i.putExtra("city_name",city_name);
                i.putExtra("zipcode",zipcode);
                i.putExtra("contact_person",contact_person);
                i.putExtra("services_of_id",services_of_id);
                i.putExtra("services_type_id",services_type_id);
                i.putExtra("type_id",type_id);
                i.putExtra("type_name",type_name);
                i.putExtra("capacity_id",capacity_id);
                i.putExtra("capacity_value",capacity_value);
                i.putExtra("quantity",quantity);
                i.putExtra("manufacture",manufacture);
                i.putExtra("refill_date",refill_date);
                i.putExtra("refill_due_date",refill_due_date);
                i.putExtra("rate",rate);
                i.putExtra("provider",provider);
                context.getApplicationContext().startActivity(i);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doDelete(position);
            }
        });


        return view;
    }






    private void doDelete(final int position) {
        final MaterialDialog dialog = new MaterialDialog.Builder(activity)
                .title("Delete")
                .content("Do you want to Delete Client?")
                .positiveText("Ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                          final ProgressDialog pd=ProgressDialog.show(MainActivity.activity,"","Please wait...");
                          new Thread(new Runnable() {
                              @Override
                              public void run() {

                                  String params="function=delete_client&client_id="+list.get(position).getId();
                                  String response= ServerConnection.sendRequest(ServerConnection.users_url,params);
                                  if(response!=null){

                                      try {
                                          JSONObject ob=new JSONObject(response);

                                          if(ob.getString("success").equals("yes")){
                                              MainActivity.activity.runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      pd.dismiss();
                                                      list.remove(position);
                                                      notifyDataSetChanged();
                                                  }
                                              });

                                          }else{
                                              MainActivity.activity.runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      pd.dismiss();
                                                      ConstantData.showAlertDialog(MainActivity.activity,"Sorry!! Please try again");
                                                  }
                                              });
                                          }



                                      } catch (JSONException e) {
                                          e.printStackTrace();

                                          MainActivity.activity.runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  pd.dismiss();
                                                  ConstantData.showAlertDialog(MainActivity.activity,"Sorry!! Something went wrong");
                                              }
                                          });
                                      }

                                  }else{
                                      MainActivity.activity.runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              pd.dismiss();
                                              ConstantData.showAlertDialog(MainActivity.activity,"Sorry!! Please try again.");
                                          }
                                      });
                                  }
                              }
                          }).start();

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








}
