package com.lnsel.findmess.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lnsel.findmess.R;
import com.lnsel.findmess.appconroller.AppController;
import com.lnsel.findmess.utlity.CreateDialog;
import com.lnsel.findmess.utlity.Webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    Button btn_submit;
    EditText etn_full_name,etn_username,etn_contact_no,etn_landmark,etn_address,etn_room_rent,etn_about_us;
    Spinner spinner_location,spinner_vacancy,spinner_no_of_room,spinner_no_of_member;
    CheckBox checkbox_wifi,checkbox_foods;
    String full_name="",username="",contact_no="",landmark="",address="",room_rent="",about_us="";
    String wifi="0",foods="0";
    String location="",vacancy="",no_of_room="",no_of_member="";
    ArrayList<String> numberList;
    ArrayList<String> locationList;
    ArrayAdapter<String> adapterVacancy;
    ArrayAdapter<String> adapterRoom;
    ArrayAdapter<String> adapterMember;
    ArrayAdapter<String> adapterLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        numberList=new ArrayList<>();
        for(int i=1;i<21;i++){
            numberList.add(""+i);
        }
        locationList=new ArrayList<>();
        Button btn_back=(Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
        etn_full_name=(EditText)findViewById(R.id.etn_full_name);
        etn_username=(EditText)findViewById(R.id.etn_username);
        etn_contact_no=(EditText)findViewById(R.id.etn_contact_no);
        etn_landmark=(EditText)findViewById(R.id.etn_landmark);
        etn_address=(EditText)findViewById(R.id.etn_address);
        etn_room_rent=(EditText)findViewById(R.id.etn_room_rent);
        etn_about_us=(EditText)findViewById(R.id.etn_about_us);

        spinner_location=(Spinner)findViewById(R.id.spinner_location);
        spinner_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                location=locationList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapterLocation =new ArrayAdapter<String>(RegisterActivity.this,R.layout.spinner_rows, locationList);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_location.setAdapter(adapterLocation);

        spinner_vacancy=(Spinner)findViewById(R.id.spinner_vacancy);
        spinner_vacancy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                vacancy=numberList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapterVacancy =new ArrayAdapter<String>(RegisterActivity.this,R.layout.spinner_rows, numberList);
        adapterVacancy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vacancy.setAdapter(adapterVacancy);

        spinner_no_of_room=(Spinner)findViewById(R.id.spinner_no_of_room);
        spinner_no_of_room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                no_of_room=numberList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapterRoom =new ArrayAdapter<String>(RegisterActivity.this,R.layout.spinner_rows, numberList);
        adapterRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_no_of_room.setAdapter(adapterRoom);

        spinner_no_of_member=(Spinner)findViewById(R.id.spinner_no_of_member);
        spinner_no_of_member.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                no_of_member=numberList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapterMember =new ArrayAdapter<String>(RegisterActivity.this,R.layout.spinner_rows, numberList);
        adapterMember.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_no_of_member.setAdapter(adapterMember);

        checkbox_wifi=(CheckBox)findViewById(R.id.checkbox_wifi);
        checkbox_foods=(CheckBox)findViewById(R.id.checkbox_foods);
        checkbox_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    wifi="1";
                }else {
                    wifi="0";
                }
            }
        });
        checkbox_foods.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    foods="1";
                }else {
                    foods="0";
                }
            }
        });
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                full_name=etn_full_name.getText().toString();
                username=etn_username.getText().toString();
                contact_no=etn_contact_no.getText().toString();
                landmark=etn_landmark.getText().toString();
                address=etn_address.getText().toString();
                room_rent=etn_room_rent.getText().toString();
                about_us=etn_about_us.getText().toString();
                if(full_name.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Name can't be blank");
                }else if(username.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Username can't be blank");
                }else if(contact_no.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Mobile Number can't be blank");
                }else if(landmark.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Landmark can't be blank");
                }else if(location.equalsIgnoreCase("Select Location")) {
                    CreateDialog.showDialog(RegisterActivity.this,"Please select location");
                }else if(address.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Address can't be blank");
                }else if(room_rent.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Name can't be blank");
                }else if(about_us.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"About us can't be blank");
                }else if(vacancy.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Please select no of vacancy");
                }else if(no_of_room.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Please select no of room");
                }else if(no_of_member.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"Please select no of member");
                }else if(wifi.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"check wi-fi availability");
                }else if(foods.equalsIgnoreCase("")){
                    CreateDialog.showDialog(RegisterActivity.this,"check foods availability");
                }else {
                    doRegistation();
                }
            }
        });

        getLocationList();


    }

    public void doRegistation(){

        final ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.MESS_REGISTER_URL;

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
                        Toast.makeText(RegisterActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name",full_name);
                params.put("username",username);
                params.put("location",location);
                params.put("address",address);
                params.put("landmark",landmark);
                params.put("vacancy",vacancy);

                params.put("no_of_room",no_of_room);
                params.put("no_of_member",no_of_member);
                params.put("contact_no",contact_no);
                params.put("wi_fi",wifi);
                params.put("food",foods);

                params.put("room_rent",room_rent);
                params.put("about_us",about_us);
                params.put("status","1");
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
                JSONObject response=parentObj.getJSONObject("response");
                String name=response.getString("name");
                String mess_id=response.getString("mess_id");
                startActivity(new Intent(RegisterActivity.this,MessActivity.class));
                finish();
            }else {
                String error_msg=parentObj.getString("response");
                Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getLocationList(){
        final  ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        String url;

        url = Webservice.LOCATION_LIST;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        responseLocation(response);
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
                        //doCityList(stateid);
                        Toast.makeText(RegisterActivity.this, "Have a Network Error Please check Internet Connection.", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void responseLocation(String responsedata){
        try {

            if(locationList.size()>0){
                locationList.clear();
            }
            JSONObject parentObj = new JSONObject(responsedata);
            String status=parentObj.optString("status");
            if (status.equalsIgnoreCase("true")){
                JSONArray jArray = parentObj.getJSONArray("result");
                locationList.add("Select Location");
                for(int i=1;i<jArray.length();i++){
                    JSONObject records = jArray.optJSONObject(i);
                    locationList.add(records.optString("name"));
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        adapterLocation.notifyDataSetChanged();
    }
}
