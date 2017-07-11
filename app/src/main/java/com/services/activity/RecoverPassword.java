package com.services.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.services.R;
import com.services.custom.ConnectionDetector;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bhakti on 3/24/2017.
 */

public class RecoverPassword extends AppCompatActivity {
    EditText edtNewPass,edtCnfmPass;
    Button btnSubmit;
   // ConnectionDetector cd;

    String strNewPass,strCnfmPass,userID;


    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
      //  cd = new ConnectionDetector(getApplicationContext());
       // checkConnection();
         userID=getIntent().getStringExtra("user_id");

        edtNewPass=(EditText)findViewById(R.id.edt_new_password);
        edtCnfmPass=(EditText)findViewById(R.id.edt_confirm_password);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doChangePassword();
            }
        });

    }

    public void doChangePassword() {
        strNewPass=edtNewPass.getText().toString().trim();
        strCnfmPass=edtCnfmPass.getText().toString().trim();
        if(strNewPass.equals("")){
            edtNewPass.setError("Please enter new password");
        }else if(strCnfmPass.equals("")){
            edtCnfmPass.setError("Please enter confirm password");
        }else if(!strCnfmPass.equals(strNewPass)){
            edtCnfmPass.setError("Confirm password does not match");
        }else{

            final ProgressDialog pd=ProgressDialog.show(RecoverPassword.this,"","Please wait..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String params="function=recover_password_one&new_password="+strNewPass+"&user_id="+userID;
                    String response= ServerConnection.sendRequest(ServerConnection.users_url,params);
                    if(response!=null){

                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            if(jsonObject.getString("success").equals("yes")){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Password changed successfully",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });

                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        ConstantData.showAlertDialog(RecoverPassword.this,"Something went wrong!! Please try again.");
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }).start();
        }

    }

//    private void checkConnection() {
//        // Check if Internet present
//        if (!cd.isConnectingToInternet()) {
//            // Internet Connection is not present
//            Snackbar snackbar = Snackbar
//                    .make(findViewById(R.id.recover_password), "No internet connection!", Snackbar.LENGTH_LONG)
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
//            //ConstantData.connection = "not_connected";
//            snackbar.show();
//
//            // stop executing code by return
//            return;
//        } else {
//           // ConstantData.connection = "connected";
//        }
//    }
}

