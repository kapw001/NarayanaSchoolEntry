package apps.pappaya.narayanaschoolentry.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.pappaya.narayanaschoolentry.Common.VolleySingleton;
import apps.pappaya.narayanaschoolentry.R;
import dmax.dialog.SpotsDialog;

import static apps.pappaya.narayanaschoolentry.ConstantData.Service_URL;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    Button Login_BT_ID;

    EditText Password_ET_ID,UserName_ET_ID;

    private android.app.AlertDialog progressDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

       pref  = getApplicationContext().getSharedPreferences("Log", 0); // 0 - for private mode
         editor = pref.edit();

        if (!pref.getString("login","").equals(""))
        {
           startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        Login_BT_ID=(Button)findViewById(R.id.Login_BT_ID);
//        UserName_ET_ID=(EditText) findViewById(R.id.School_EditText);
        UserName_ET_ID=(EditText) findViewById(R.id.UserName_ET_ID);
        Password_ET_ID=(EditText)findViewById(R.id.Password_ET_ID);
        Login_BT_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Password_ET_ID.getText().toString().equals("") && !Login_BT_ID.getText().toString().equals("")) {
                    network();

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        }

    public void network()
    {

        progressDialog = new SpotsDialog(LoginActivity.this, R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{


//            req.put("login", jsonObject2.toString());

            req_hash.put("login", UserName_ET_ID.getText().toString());
            req_hash.put("password", Password_ET_ID.getText().toString());
//            req_hash.put("params",jsonObject.toString());




            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));

            jsonObject2.put("params",jsonObject.toString());

            req_hash2.put("data",jsonObject2.get("params").toString());

            Log.d(" req_hash2 ","req_hash2 ##"+req_hash2.get("data"));

        }catch(Exception e)
        {

        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("params", req_hash.toString());

        String data="\"params\":"+req_hash2.get("data");

        Log.d("datass "," datass ##"+data);

        try{
//            JSONObject jj=new JSONObject(data);

//            JSONObject jj = new JSONObject(data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1));
            JSONObject jj = new JSONObject("{"+data+"}");

            Log.d("LastData "," LastData ##"+jj.toString());
            Log.d("LastData "," URL ##"+Service_URL+"/mobile/rest/prologin");

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/prologin", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" Login_response "," response ## "+response.toString());

                                JSONObject result=response.getJSONObject("result");

                                Log.d(" Login_result "," result ## "+result.toString());



                                Log.d(" response "," employee_id ###"+result.getString("employee_id"));

                                editor.putString("user_name",result.getString("user_name"));
                                editor.putString("password",result.getString("password"));
                                editor.putString("user_image",result.getString("user_image"));
                                editor.putString("login",result.getString("login"));
                                editor.putString("employee_id",result.getString("employee_id"));
                                editor.putString("employee_email",result.getString("employee_email"));
                                editor.putString("employee_mobile",result.getString("employee_mobile"));
                                editor.putString("emp_db_id",result.getString("emp_db_id"));
                                editor.apply();
                                Log.d(" emp_db_id ## "," emp_db_id ## "+result.getString("emp_db_id"));
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
Toast.makeText(getApplicationContext(),"Invalid credentials! ",Toast.LENGTH_SHORT).show();
                                Log.d(" ExceptionEE "," ExceptionEE ##"+e.toString());
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(LoginActivity.this, "Username or password incorrect", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
//
//                    if (refresh.isRefreshing()) {
//                        refresh.setRefreshing(false);
//                    }

                    VolleyLog.e(" Error_main : ", error.getMessage());

                    // As of f605da3 the following should work
                    NetworkResponse response = error.networkResponse;
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
//                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }

                }
            });

            req.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }
    }

}