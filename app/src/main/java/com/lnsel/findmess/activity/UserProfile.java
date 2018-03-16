package com.lnsel.findmess.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lnsel.findmess.R;
import com.lnsel.findmess.SetterGetter.UserInfo;
import com.lnsel.findmess.appconroller.AppController;
import com.lnsel.findmess.utlity.CreateDialog;
import com.lnsel.findmess.utlity.Sharepreferences;
import com.lnsel.findmess.utlity.Webservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends Activity {

    EditText etn_name,etn_user_name,etn_mobile,etn_address,etn_occupation,etn_requirment;
    Button btn_submit;
    String user_name="",name="",mobile="",address="",occupation="",requirment="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this,FindActivity.class));
                finish();
            }
        });

        etn_name=(EditText)findViewById(R.id.etn_name);
        etn_user_name=(EditText)findViewById(R.id.etn_user_name);
        etn_mobile=(EditText)findViewById(R.id.etn_mobile);
        etn_address=(EditText)findViewById(R.id.etn_address);
        etn_occupation=(EditText)findViewById(R.id.etn_occupation);
        etn_requirment=(EditText)findViewById(R.id.etn_requirment);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name=etn_user_name.getText().toString();
                name=etn_name.getText().toString();
                mobile=etn_mobile.getText().toString();
                address=etn_address.getText().toString();
                occupation=etn_occupation.getText().toString();
                requirment=etn_requirment.getText().toString();
                if(name.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserProfile.this,"Name can't be blank");
                }else if(user_name.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserProfile.this,"Username can't be blank");
                }else if(mobile.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserProfile.this,"Mobile no can't be blank");
                }else if(address.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserProfile.this,"Address can't be blank");
                }else if(occupation.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserProfile.this,"Occupation password can't be blank");
                }else if(requirment.equalsIgnoreCase("")){
                    CreateDialog.showDialog(UserProfile.this,"Requirment password can't be blank");
                }else {
                    doUpdateProfile();
                }
            }
        });

        getMessDetails();
    }


    public void getMessDetails(){

        final ProgressDialog progressDialog=new ProgressDialog(UserProfile.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.USER_DETAILS;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONresponse(response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(UserProfile.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                UserInfo rememberData= Sharepreferences.getUserinfo(UserProfile.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id",rememberData.getUserId());
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public void JSONresponse(String responsedata){

        try {
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){
                JSONObject data = parentObj.getJSONObject("response");

                etn_name.setText(data.getString("name"));
                //etn_user_name.setText(data.getString("location"));
                etn_mobile.setText(data.getString("mobile"));
                etn_occupation.setText(data.getString("occupation"));
                etn_address.setText(data.getString("address"));
                etn_requirment.setText(data.getString("requirment"));

            }else {
                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void doUpdateProfile(){


        final ProgressDialog progressDialog=new ProgressDialog(UserProfile.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.USER_UPDATE;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        JSONUpdateresponse(response);
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        System.out.println("Error=========="+error);
                        Toast.makeText(UserProfile.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                UserInfo rememberData= Sharepreferences.getUserinfo(UserProfile.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id",rememberData.getUserId());
                params.put("name",name);
                params.put("occupation",occupation);
                params.put("address",address);
                params.put("mobile",mobile);
                params.put("requirment",requirment);
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public void JSONUpdateresponse(String responsedata){

        try {
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if(status.equalsIgnoreCase("true")){
                String response=parentObj.optString("response");
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

            }else {
                String response=parentObj.optString("response");
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
