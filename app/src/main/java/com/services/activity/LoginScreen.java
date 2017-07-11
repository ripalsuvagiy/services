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
import android.widget.TextView;

import com.services.R;
import com.services.custom.ConstantData;
import com.services.custom.ServerConnection;

import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {
    EditText edt_email,edt_pass;
    TextView txt_fpass;
    Button btnSubmit;
    String email,password,userId;
    TextView txtNewAc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        txtNewAc =(TextView)findViewById(R.id.txt_create_ac);
        edt_pass=(EditText)findViewById(R.id.edt_password);
        edt_email=(EditText)findViewById(R.id.edt_email);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();

            }
        });
        txt_fpass=(TextView)findViewById(R.id.txt_fpassword);
        txt_fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginScreen.this,ForgotPassword.class);
                startActivity(i);

            }
        });

        txtNewAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginScreen.this,Registration.class);
                startActivity(i);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doLogin();

            }
        });
    }
    private void doLogin() {
        email=edt_email.getText().toString().trim();
        password=edt_pass.getText().toString().trim();

        if(email.equals(""))
            ConstantData.showAlertDialog(LoginScreen.this,"Please enter email");
        else if (ConstantData.emailValidation(email).equals("false")){
            ConstantData.showAlertDialog(LoginScreen.this,"Please enter valid email");
        }
        else if(password.equals(""))
            ConstantData.showAlertDialog(LoginScreen.this,"Please enter password");
        else
        {
//            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Login.this);
//            userId = sh.getString("user_id", "");
            final ProgressDialog pd=ProgressDialog.show(LoginScreen.this,"","Please Wait");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String param="function=login&email="+email+"&password="+password;
                    String responce= ServerConnection.sendRequest(ServerConnection.users_url,param);
                    Log.d("responce",""+responce);
                    if(responce!=null){
                        try {
                            JSONObject ob=new JSONObject(responce);
                            if(ob.getString("success").equals("yes")){
                                JSONObject ob1=ob.getJSONObject("data");
                                String user_name=ob1.getString("email");
                                String user_password=ob1.getString("password");
                                String user_id=ob1.getString("id");
                                SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(LoginScreen.this).edit();
                                editor.putString("email",user_name);
                                editor.putString("password",user_password);
                                editor.putString("user_id",user_id);
                                editor.commit();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Intent i=new Intent(LoginScreen.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });

                            }
                            else {
                                ConstantData.showAlertDialog(LoginScreen.this,"message");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        edt_email.setText("");
                                        edt_pass.setText("");
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

