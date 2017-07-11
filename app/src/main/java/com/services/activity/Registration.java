package com.services.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.services.R;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;

import org.json.JSONObject;

public class Registration extends AppCompatActivity {
    EditText username,pass,email,mobileNo,edtCnfmPass;
    Button btnRegistration;
    String uname,upass,uemail,umobileNO,ucpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username=(EditText)findViewById(R.id.edt_name);
        mobileNo=(EditText)findViewById(R.id.edt_mobile_no);
        pass=(EditText)findViewById(R.id.edt_password);
        edtCnfmPass=(EditText)findViewById(R.id.edt_cpassword);
        email=(EditText)findViewById(R.id.edt_email);
        btnRegistration=(Button)findViewById(R.id.btn_registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registeration();
            }
        });

    }

    private void Registeration() {
        uname=username.getText().toString().trim();
        upass=pass.getText().toString().trim();
        ucpassword=edtCnfmPass.getText().toString().trim();
        uemail=email.getText().toString().trim();
        umobileNO=mobileNo.getText().toString().trim();

        if(uname.equals(""))
            ConstantData.showAlertDialog(Registration.this,"Enter Name");
        else if(uemail.equals(""))
            ConstantData.showAlertDialog(Registration.this,"Enter Email");
        else if (ConstantData.emailValidation(uemail).equals("false"))
            ConstantData.showAlertDialog(Registration.this,"Please enter valid email");
        else if(umobileNO.equals(""))
            ConstantData.showAlertDialog(Registration.this,"Enter Mobile No");
        else if(umobileNO.length()<10||umobileNO.length()>10)
            ConstantData.showAlertDialog(Registration.this,"Enter Valid Mobile No");
        else if(upass.equals(""))
            ConstantData.showAlertDialog(Registration.this,"Enter Password");
        else if(ucpassword.equals(""))
            ConstantData.showAlertDialog(Registration.this,"Enter Confirm Password");
        else if(!ucpassword.equals(upass)){
            ConstantData.showAlertDialog(Registration.this,"Confirm Password does not match");
        }
        else {
            final ProgressDialog pd=ProgressDialog.show(Registration.this,"","Please wait..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String params="function=registration&name="+uname+"&email="+uemail+"&mobile_no="+umobileNO+"&password="+upass;
                    String responce= ServerConnection.sendRequest(ServerConnection.users_url,params);

                    Log.d("responce",""+responce);

                    if(responce!=null){
                        try {
                            JSONObject jobj=new JSONObject(responce);
                            if(jobj.getString("success").equals("yes"))
                            {
                                String user_id=jobj.getString("data");
                                SharedPreferences.Editor shr= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                shr.putString("user_id",user_id);
                                shr.commit();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            }
                            else {
                                ConstantData.showAlertDialog(getApplicationContext(),jobj.getString("message"));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
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
