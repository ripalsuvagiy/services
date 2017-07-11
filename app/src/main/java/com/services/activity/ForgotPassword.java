package com.services.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;


import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.services.R;
import com.services.custom.ConnectionDetector;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ForgotPassword extends AppCompatActivity {

    Button btnSubmit;
//    ConnectionDetector cd;
    EditText edtEmail;
    String bj;
      AlertDialog dialog;
    public  static  int minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

//        cd = new ConnectionDetector(getApplicationContext());
       // checkConnection();
        edtEmail=(EditText)findViewById(R.id.edt_email);
           btnSubmit=(Button)findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForForgotPassword();
            }
        });



    }


    private void goForForgotPassword() {


        final String strEmail = edtEmail.getText().toString().trim();
      //  final String strMobileNo = edtMobileNo.getText().toString().trim();

       if (strEmail.equals("")) {
            ConstantData.showAlertDialog(ForgotPassword.this, "Enter Email");
        }else if(ConstantData.emailValidation(strEmail).equals("false")){
            ConstantData.showAlertDialog(ForgotPassword.this, "Enter Valid Email");
        }
        else {
            final ProgressDialog pd = ProgressDialog.show(ForgotPassword.this, "", "Please wait...");
            new Thread(new Runnable() {
                @Override
                public void run() {

                    String params = "function=forgot_password&email="+strEmail;
                    String response = ServerConnection.sendRequest(ServerConnection.users_url, params);
                    Log.d("Response", "" + response);
                    if (response != null) {
                        try {
                            final JSONObject obj = new JSONObject(response);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        bj = obj.getString("success");
                                        if(bj.equals("yes")){
                                            final  String user_id=obj.getString("data");
//                                           String password=obj.getString("data");

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pd.dismiss();
                                                    generateDialog(user_id,strEmail);
                                                }
                                            });



                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pd.dismiss();
                                                    ConstantData.showAlertDialog(ForgotPassword.this, "Oops!! Something went wrong.Please try again.");
                                                }
                                            });
                                        }


                                    } catch (JSONException e) {
                                        pd.dismiss();
                                        e.printStackTrace();
                                    }
                                }
                            });


                        } catch (JSONException e) {
                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            pd.dismiss();
//                            edtEmail.setText("");
                        }
                    });
                }
            }).start();
        }
    }



    public void generateDialog(final String userId,final String email){
        Calendar c = Calendar.getInstance();
        minute = c.get(Calendar.MINUTE);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ForgotPassword.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_digit_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText edtDigit=(EditText)dialogView.findViewById(R.id.edt_digit);
        final Button btnSubmit=(Button)dialogView.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strDigit=edtDigit.getText().toString().trim();
                if(strDigit.equals("")){
                    edtDigit.setError("Please enter digit code.");
                }else{
                    dialog.dismiss();
                    final ProgressDialog pd=ProgressDialog.show(ForgotPassword.this,"","Please wait...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String params="function=do_verification&user_id="+userId+"&email="+email+"&digit_code="+strDigit;
                            String response=ServerConnection.sendRequest(ServerConnection.users_url,params);
                            Log.d("response1",response);
                            if(response!=null){
                                try {
                                    JSONObject jsonObj=new JSONObject(response);
                                    if(jsonObj.getString("success").equals("yes")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                pd.dismiss();
                                                ConstantData.t.cancel();


                                            }
                                        });
                                        Intent i=new Intent(ForgotPassword.this,RecoverPassword.class);
                                        i.putExtra("user_id",userId);
                                        startActivity(i);
                                        finish();

                                    }else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConstantData.showAlertDialog(ForgotPassword.this,"Sorry Please try again!!");

                                            }
                                        });

                                    }



                                } catch (JSONException e) {
                                    pd.dismiss();
                                    e.printStackTrace();
                                }


                            }else{
                                pd.dismiss();
                                ConstantData.showAlertDialog(ForgotPassword.this,"Sorry Please try again!!");
                            }

                        }
                    }).start();

                }




            }
        });


//        ConstantData.handler=new Handler();
//        ConstantData.handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(minute>=minute+1){
//
//                }
//
//            }
//        }, 60000);


        ConstantData.handler = new Handler();
        ConstantData.t = new Timer();
        ConstantData.t.schedule(new TimerTask() {
            public void run() {
                ConstantData.handler.post(new Runnable() {
                    public void run() {
                        doDelete(userId,email);
                        //DO SOME ACTIONS HERE , THIS ACTIONS WILL WILL EXECUTE AFTER 5 SECONDS...
                    }
                });
            }
        }, 300000);



        dialog= dialogBuilder.create();
        dialog.show();

    }



    public void doDelete(final String userId,final String email ){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String params="function=delete_info&user_id="+userId+"&email="+email;
                String response=ServerConnection.sendRequest(ServerConnection.users_url,params);
                if(response!=null){
                    try {
                        JSONObject jo=new JSONObject(response);
                        if(jo.getString("success").equals("yes")){
                            ConstantData.t.cancel();
                        }else{

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    ConstantData.t.cancel();
                }

            }
        }).start();

    }


//    private void checkConnection() {
//        // Check if Internet present
//        if (!cd.isConnectingToInternet()) {
//            // Internet Connection is not present
//            Snackbar snackbar = Snackbar
//                    .make(findViewById(R.id.activity_forgot_password), "No internet connection!", Snackbar.LENGTH_LONG)
//                    .setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            checkConnection();
//                        }
//                    });
//
//            // Changing message text color
//
//            snackbar.setActionTextColor(Color.WHITE);
//
//            // Changing action button text color
//            View sbView = snackbar.getView();
//            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//            textView.setTextColor(Color.RED);
//          //  ConstantData.connection = "not_connected";
//            snackbar.show();
//
//            // stop executing code by return
//            return;
//        } else {
//           // ConstantData.connection = "connected";
//        }
//    }
}
