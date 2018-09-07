package apps.pappaya.narayanaschoolentry.Fragments.EnterDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.pappaya.narayanaschoolentry.Activities.LoginActivity;
import apps.pappaya.narayanaschoolentry.Activities.MainActivity;
import apps.pappaya.narayanaschoolentry.Common.VolleySingleton;
import apps.pappaya.narayanaschoolentry.DataModels.CitiesDataModel;
import apps.pappaya.narayanaschoolentry.DataModels.CommonIdNameModel;
import apps.pappaya.narayanaschoolentry.DataModels.WardDataModel;
import apps.pappaya.narayanaschoolentry.Fragments.ViewDetails.ViewDetailsFragment;
import apps.pappaya.narayanaschoolentry.R;
import dmax.dialog.SpotsDialog;

import static apps.pappaya.narayanaschoolentry.ConstantData.Service_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnterDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnterDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<CommonIdNameModel> StateArrayList_DataModel=new ArrayList<>();

    ArrayList<CommonIdNameModel> CourseArrayList=new ArrayList<>();
    ArrayList<String> GenderArrayList=new ArrayList<>();



    ArrayList<CommonIdNameModel> StateArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> disctrict_dataArraylist=new ArrayList<>();
    ArrayList<CommonIdNameModel> city_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> ward_data_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> area_data_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> mandal_data_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> village_data_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> school_data_dataArrayList=new ArrayList<>();

    ArrayList<CommonIdNameModel> StateArrayListSchool=new ArrayList<>();
    ArrayList<CommonIdNameModel> disctrict_dataArraylistSchool=new ArrayList<>();
    ArrayList<CommonIdNameModel> mandal_data_dataArrayListSchool=new ArrayList<>();

    private android.app.AlertDialog progressDialog;

    SwitchCompat switch_compat;

    String Location_Type="urban";
    LinearLayout Urban_ID,Rural_ID;
    EditText Employee_ID;

    EditText Father_Name_TV,Student_Name_ET,Mobile_Number_ET;
    Spinner Studying_course_Spinner;
    Spinner state_Spinner_ID,District_Spinner_ID;
    Spinner City_Spinner_id,Ward_Spinner_ID,Area_Spinner_ID,Mandal_Spinner_ID,Village_Spinner_ID;
Spinner Gender_Spinner_ID;
    EditText School_EditText;
    Spinner School_District_Spinner_ID,School_State_Spinner_ID,School_Mandal_Spinner_ID;

    SharedPreferences sharedPreferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EnterDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnterDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnterDetailFragment newInstance(String param1, String param2) {
        EnterDetailFragment fragment = new EnterDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_enter_detail, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Create Lead");

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.toolbar_draw);


        ImageView imageView=    ((MainActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.home_IM);
        TextView textView=    ((MainActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
        textView.setText("Home");
//        Glide.with(this).load(R.drawable.home).into(imageView);


        switch_compat=(SwitchCompat)view.findViewById(R.id.switch_compat);
        Rural_ID=(LinearLayout)view.findViewById(R.id.Rural_ID);
        Urban_ID=(LinearLayout) view.findViewById(R.id.Urban_ID);
        Employee_ID=(EditText) view.findViewById(R.id.Employee_ID);
        Father_Name_TV=(EditText) view.findViewById(R.id.Father_Name_TV);
        Mobile_Number_ET=(EditText) view.findViewById(R.id.Mobile_Number_ET);
        Student_Name_ET=(EditText) view.findViewById(R.id.Student_Name_ET);
        Studying_course_Spinner=(Spinner) view.findViewById(R.id.Studying_course_Spinner);

        District_Spinner_ID=(Spinner) view.findViewById(R.id.District_Spinner_ID);
        state_Spinner_ID=(Spinner) view.findViewById(R.id.state_Spinner_ID);
        Gender_Spinner_ID=(Spinner) view.findViewById(R.id.Gender_Spinner_ID);

        City_Spinner_id=(Spinner) view.findViewById(R.id.City_Spinner_id);
        Ward_Spinner_ID=(Spinner) view.findViewById(R.id.Ward_Spinner_ID);
        Area_Spinner_ID=(Spinner) view.findViewById(R.id.Area_Spinner_ID);
        Mandal_Spinner_ID=(Spinner) view.findViewById(R.id.Mandal_Spinner_ID);
        Village_Spinner_ID=(Spinner) view.findViewById(R.id.Village_Spinner_ID);

        School_EditText=(EditText) view.findViewById(R.id.School_EditText);
        School_District_Spinner_ID=(Spinner) view.findViewById(R.id.School_District_Spinner_ID);
        School_State_Spinner_ID=(Spinner) view.findViewById(R.id.School_State_Spinner_ID);
        School_Mandal_Spinner_ID=(Spinner) view.findViewById(R.id.School_Mandal_Spinner_ID);

        sharedPreferences=getActivity().getSharedPreferences("Log",0);

        Employee_ID.setText(sharedPreferences.getString("emp_db_id",""));

        GenderArrayList.add("Male");
        GenderArrayList.add("Female");

        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, GenderArrayList);
        genderAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        Gender_Spinner_ID.setAdapter(genderAdapter);

        DropdownStateChange();

        switch_compat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    Rural_ID.setVisibility(View.VISIBLE);
                    Urban_ID.setVisibility(View.GONE);
                    Location_Type="rural";
                }else{

                    Location_Type="urban";
                    Urban_ID.setVisibility(View.VISIBLE);
                    Rural_ID.setVisibility(View.GONE);
                }

            }
        });


//        fetchAllRecords();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    public void SendData()
    {
        Log.d(""," Gender_Spinner_ID ## "+Gender_Spinner_ID.getSelectedItem());

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{


            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            Log.d(" req_hash_Temp "," req_hash ##"+req_hash.toString());
            req_hash.put("name", Student_Name_ET.getText().toString());
            if(Gender_Spinner_ID.getSelectedItem().equals("Male"))
            req_hash.put("gender", "male");
            else
                req_hash.put("gender", "female");
            req_hash.put("father_name", Father_Name_TV.getText().toString());
            req_hash.put("mobile", Mobile_Number_ET.getText().toString());
            req_hash.put("studying_school_name", School_EditText.getText().toString());
            req_hash.put("studying_course_id", CourseArrayList.get(Studying_course_Spinner.getSelectedItemPosition()).getID());
            req_hash.put("state_id", StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());


            req_hash.put("location_type", Location_Type);

            if (Location_Type.equals("urban")) {
                req_hash.put("city_id", city_dataArrayList.get(City_Spinner_id.getSelectedItemPosition()).getID());
                req_hash.put("ward_id", ward_data_dataArrayList.get(Ward_Spinner_ID.getSelectedItemPosition()).getID());
                req_hash.put("area_id", area_data_dataArrayList.get(Area_Spinner_ID.getSelectedItemPosition()).getID());

                req_hash.put("mandal_id", "");
                req_hash.put("village_id", "");
            }else {
                req_hash.put("city_id", "");
                req_hash.put("ward_id", "");
                req_hash.put("area_id", "");

                req_hash.put("mandal_id", mandal_data_dataArrayList.get(Mandal_Spinner_ID.getSelectedItemPosition()).getID());
                req_hash.put("village_id", village_data_dataArrayList.get(Village_Spinner_ID.getSelectedItemPosition()).getID());
            }

            req_hash.put("school_state_id", StateArrayList.get(School_State_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("school_district_id", disctrict_dataArraylistSchool.get(School_District_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("school_mandal_id", mandal_data_dataArrayListSchool.get(School_Mandal_Spinner_ID.getSelectedItemPosition()).getID());


            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));


            jsonObject.put("name", req_hash.get("name"));
            jsonObject.put("father_name", req_hash.get("father_name"));


            Log.d(" mobile ## "," mobile_Result## "+req_hash.get("mobile"));
            jsonObject.put("mobile", Long.parseLong(req_hash.get("mobile")));
//            Log.d(" jsonObject_temp "," jsonObject_temp ###"+jsonObject.toString());
            jsonObject.put("studying_school_name",req_hash.get("studying_school_name"));
            jsonObject.put("studying_course_id", Integer.parseInt(req_hash.get("studying_course_id")));

            jsonObject.put("state_id", Long.parseLong(req_hash.get("state_id")));

            jsonObject.put("district_id", Long.parseLong(req_hash.get("district_id")));
            jsonObject.put("gender", req_hash.get("gender"));


            jsonObject.put("location_type", req_hash.get("location_type"));

            if (Location_Type.equals("urban")) {
                jsonObject.put("city_id", Long.parseLong(req_hash.get("city_id")));
                jsonObject.put("ward_id", Long.parseLong(req_hash.get("ward_id")));
                jsonObject.put("area_id", Long.parseLong(req_hash.get("area_id")));

                jsonObject.put("mandal_id", "");
                jsonObject.put("village_id", "");
            }else {
                jsonObject.put("city_id", "");
                jsonObject.put("ward_id", "");
                jsonObject.put("area_id", "");

                jsonObject.put("mandal_id", Long.parseLong(req_hash.get("mandal_id")));
                jsonObject.put("village_id", Long.parseLong(req_hash.get("village_id")));
            }

            jsonObject.put("school_state_id", Long.parseLong(req_hash.get("school_state_id")));
            jsonObject.put("school_district_id",Long.parseLong(req_hash.get("school_district_id")));
            jsonObject.put("school_mandal_id", Long.parseLong(req_hash.get("school_mandal_id")));
            Log.d(" jsonObject_temp "," jsonObject_temp ###"+jsonObject.toString());
            Log.d(" jsonObject ","jsonObject_lead ##"+jsonObject.toString());

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

            Log.d("Create_Lead_Req "," LastData ##"+jj.toString());
            Log.d("Create_Lead_Req "," URL ##"+Service_URL+"/mobile/rest/create_lead");

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/create_lead", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" response ## "," response_insert ## "+response);

                                Toast.makeText(getContext(), "Lead Created Successfully", Toast.LENGTH_SHORT).show();

                                ((MainActivity)getActivity()).ChangeFragment(new EnterDetailFragment());


                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);

                                Log.d(" ExceptionEE "," ExceptionEE ##"+e.toString());
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Invalid request,Login again", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void fetchAllRecords()
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("lead_request", "yes");
//            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
//            req_hash.put("params",jsonObject.toString());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("lead_request", req_hash.get("lead_request"));

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
            JSONObject jj = new JSONObject("{"+data+"}");

            Log.d("LastData "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/pro_lead_form_inputs", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" Raw_Result "," result ## "+response.toString());

                                JSONObject result=response.getJSONObject("result");

                                JSONObject lead_form_inputs=result.getJSONObject("lead_form_inputs");
                                JSONArray state_data=lead_form_inputs.getJSONArray("state_data");
                                JSONArray disctrict_data=lead_form_inputs.getJSONArray("disctrict_data");
                                JSONArray city_data=lead_form_inputs.getJSONArray("city_data");
                                JSONArray ward_data=lead_form_inputs.getJSONArray("ward_data");
                                JSONArray area_data=lead_form_inputs.getJSONArray("area_data");
                                JSONArray mandal_data=lead_form_inputs.getJSONArray("mandal_data");
                                JSONArray village_data=lead_form_inputs.getJSONArray("village_data");
                                JSONArray school_data=lead_form_inputs.getJSONArray("school_data");


                                for (int i=0;i<state_data.length();i++)
                                {
                                    JSONObject single_State=state_data.getJSONObject(i);
                                    StateArrayList.add(new CommonIdNameModel(single_State.getString("id"),single_State.getString("state")));

                                }

                                for (int i=0;i<disctrict_data.length();i++)
                                {
                                    JSONObject single_disctrict=disctrict_data.getJSONObject(i);
                                    disctrict_dataArraylist.add(new CommonIdNameModel(single_disctrict.getString("id"),single_disctrict.getString("state")));

                                }
                                for (int i=0;i<city_data.length();i++)
                                {
                                    JSONObject single_obj=city_data.getJSONObject(i);
                                    city_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("city")));

                                }
                                for (int i=0;i<ward_data.length();i++)
                                {
                                    JSONObject single_obj=ward_data.getJSONObject(i);
                                    ward_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("ward")));

                                }
                                for (int i=0;i<area_data.length();i++)
                                {
                                    JSONObject single_obj=area_data.getJSONObject(i);
                                    area_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("area")));

                                }
                                for (int i=0;i<mandal_data.length();i++)
                                {
                                    JSONObject single_obj=mandal_data.getJSONObject(i);
                                    mandal_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("state")));

                                }
                                for (int i=0;i<village_data.length();i++)
                                {
                                    JSONObject single_obj=village_data.getJSONObject(i);
                                    village_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("village")));

                                }
                                for (int i=0;i<school_data.length();i++)
                                {
                                    JSONObject single_obj=school_data.getJSONObject(i);
                                    school_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("school_name")));

                                }
                                for (int i=0;i<school_data.length();i++)
                                {
                                    JSONObject single_obj=school_data.getJSONObject(i);
                                    school_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("school_name")));

                                }

                                ArrayAdapter<CommonIdNameModel> state_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, StateArrayList);
                                state_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                state_Spinner_ID.setAdapter(state_adap);

                                ArrayAdapter<CommonIdNameModel> dist_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, disctrict_dataArraylist);
                                dist_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                District_Spinner_ID.setAdapter(dist_adap);

                                ArrayAdapter<CommonIdNameModel> city_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, city_dataArrayList);
                                city_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                City_Spinner_id.setAdapter(city_adap);

                                ArrayAdapter<CommonIdNameModel> ward_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, ward_data_dataArrayList);
                                ward_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Ward_Spinner_ID.setAdapter(ward_adap);

                                ArrayAdapter<CommonIdNameModel> area_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, area_data_dataArrayList);
                                area_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Area_Spinner_ID.setAdapter(area_adap);


                                ArrayAdapter<CommonIdNameModel> mandal_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, mandal_data_dataArrayList);
                                mandal_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Mandal_Spinner_ID.setAdapter(mandal_adap);


                                ArrayAdapter<CommonIdNameModel> village_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, village_data_dataArrayList);
                                village_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Village_Spinner_ID.setAdapter(village_adap);


//                                ArrayAdapter<CommonIdNameModel> school_lis_adap =
//                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, school_data_dataArrayList);
//                                school_lis_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

//                                School_Spinner_ID.setAdapter(school_lis_adap);

                                School_State_Spinner_ID.setAdapter(state_adap);
                                School_District_Spinner_ID.setAdapter(dist_adap);
                                School_Mandal_Spinner_ID.setAdapter(mandal_adap);

                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Invalid Login", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
        ;
        MenuItem item = menu.findItem(R.id.button_item);
        ImageView btn = item.getActionView().findViewById(R.id.Button_Save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation())
                {
                    SendData();
                }
                else{
                    Toast.makeText(getContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean Validation()
    {
        if (!Student_Name_ET.getText().toString().equals("") && !Father_Name_TV.getText().toString().equals("") &&
                !Mobile_Number_ET.getText().toString().equals("") && !CourseArrayList.get(Studying_course_Spinner.getSelectedItemPosition()).getID().equals("")
                && !StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                && !disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                && !StateArrayListSchool.get(School_State_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                && !disctrict_dataArraylistSchool.get(School_District_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                && !mandal_data_dataArrayListSchool.get(School_Mandal_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                && !School_EditText.getText().toString().equals("")
                )
        {

            if (Mobile_Number_ET.getText().toString().length()==10)
            {
                if (Location_Type.equals("urban"))
                {
                    if (!city_dataArrayList.get(City_Spinner_id.getSelectedItemPosition()).getID().equals("")
                            && !ward_data_dataArrayList.get(Ward_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                            && !area_data_dataArrayList.get(Area_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                            )
                    {
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    if (!mandal_data_dataArrayList.get(Mandal_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                            &&!village_data_dataArrayList.get(Village_Spinner_ID.getSelectedItemPosition()).getID().equals("")
                            )
                    {
                        return true;
                    }else{
                        return false;
                    }
                }
            }else
            {
                Toast.makeText(getContext(), "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                return false;
            }



        }
        else{
         return false;
        }
    }


    int check_state_Spinner_ID = 0;
    int check_District_Spinner_ID = 0;
    int check_City_Spinner_id = 0;
    int check_Mandal_Spinner_ID = 0;
    int check_Ward_Spinner_ID = 0;

    public void DropdownStateChange()
    {
        getCourse();



//        getStateSchool();

        state_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

//                if (++check_state_Spinner_ID>1) {
                if (!StateArrayList.get(position).getID().equals("")) {
                    Ward_Spinner_ID.setAdapter(null);
                    Village_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    Mandal_Spinner_ID.setAdapter(null);
                    City_Spinner_id.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    village_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();
                    mandal_data_dataArrayList.clear();
                    city_dataArrayList.clear();
                    getDistrict(Integer.parseInt(StateArrayList.get(position).getID()));
                }else{
                    Ward_Spinner_ID.setAdapter(null);
                    Village_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    Mandal_Spinner_ID.setAdapter(null);
                    City_Spinner_id.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    village_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();
                    mandal_data_dataArrayList.clear();
                    city_dataArrayList.clear();
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        District_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!disctrict_dataArraylist.get(position).getID().equals("")) {
                    Ward_Spinner_ID.setAdapter(null);
                    Village_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    Mandal_Spinner_ID.setAdapter(null);
                    City_Spinner_id.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    village_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();
                    mandal_data_dataArrayList.clear();
                    city_dataArrayList.clear();
                    getCity(Integer.parseInt(disctrict_dataArraylist.get(position).getID()));
                    getMandal(Integer.parseInt(disctrict_dataArraylist.get(position).getID()));
                }else{
                    Ward_Spinner_ID.setAdapter(null);
                    Village_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    Mandal_Spinner_ID.setAdapter(null);
                    City_Spinner_id.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    village_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();
                    mandal_data_dataArrayList.clear();
                    city_dataArrayList.clear();
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        City_Spinner_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!city_dataArrayList.get(position).getID().equals("")) {
                    Ward_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();
                    Log.d("", " city_dataArrayList ##" + city_dataArrayList.get(position).getID() + "#");
                    getWard(Integer.parseInt(city_dataArrayList.get(position).getID()));
                }else{
                    Ward_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Ward_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!ward_data_dataArrayList.get(position).getID().equals("")) {

                    Area_Spinner_ID.setAdapter(null);
                    area_data_dataArrayList.clear();
                    getArea(Integer.parseInt(ward_data_dataArrayList.get(position).getID()));
                }else{
                    Area_Spinner_ID.setAdapter(null);
                    area_data_dataArrayList.clear();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        Mandal_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!mandal_data_dataArrayList.get(position).getID().equals(""))
                    getVillage(Integer.parseInt(mandal_data_dataArrayList.get(position).getID()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        School_State_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (!StateArrayListSchool.get(position).getID().equals("")) {
                    School_Mandal_Spinner_ID.setAdapter(null);
                    mandal_data_dataArrayListSchool.clear();
                    School_District_Spinner_ID.setAdapter(null);
                    disctrict_dataArraylistSchool.clear();
                    getDistrictSchool(Integer.parseInt(StateArrayListSchool.get(position).getID()));
                }else{
                    School_Mandal_Spinner_ID.setAdapter(null);
                    mandal_data_dataArrayListSchool.clear();
                    School_District_Spinner_ID.setAdapter(null);
                    disctrict_dataArraylistSchool.clear();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        School_District_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (!disctrict_dataArraylistSchool.get(position).getID().equals("")) {
                    School_Mandal_Spinner_ID.setAdapter(null);
                    mandal_data_dataArrayListSchool.clear();
                    getMandalSchool(Integer.parseInt(disctrict_dataArraylistSchool.get(position).getID()));
                }else{
                    School_Mandal_Spinner_ID.setAdapter(null);
                    mandal_data_dataArrayListSchool.clear();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    public void getState()
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));

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

            Log.d("LastData_state "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_states", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" State_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                StateArrayList=new ArrayList<>();
                                StateArrayListSchool=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("states");

                                JSONArray state_data=states_json_Obj.getJSONArray("state_data");

                                StateArrayList.add(new CommonIdNameModel("","Select State"));
                                StateArrayListSchool.add(new CommonIdNameModel("","Select State"));

                                for (int i=0;i<state_data.length();i++)
                                {
                                    JSONObject single_State=state_data.getJSONObject(i);
                                    StateArrayList.add(new CommonIdNameModel(single_State.getString("id"),single_State.getString("name")));
                                    StateArrayListSchool.add(new CommonIdNameModel(single_State.getString("id"),single_State.getString("name")));

                                }

                                Log.d(" StateArrayList "," result ## "+StateArrayList.size());
                                Log.d(" StateArrayListSchool "," result ## "+StateArrayListSchool.size());

                                progressDialog.dismiss();
                                ArrayAdapter<CommonIdNameModel> state_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, StateArrayList);
                                state_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                 ArrayAdapter<CommonIdNameModel> state_adap_sch =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, StateArrayListSchool);
                                state_adap_sch.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);


                                state_Spinner_ID.setAdapter(state_adap);
                                School_State_Spinner_ID.setAdapter(state_adap_sch);



                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getDistrict(int state_id)
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
            req_hash.put("state_id", ""+state_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_districts", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                disctrict_dataArraylist=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("districts");

                                JSONArray disctrict_data=states_json_Obj.getJSONArray("district_data");
                                disctrict_dataArraylist.add(new CommonIdNameModel("","Select District"));

                                for (int i=0;i<disctrict_data.length();i++)
                                {
                                    JSONObject single_disctrict=disctrict_data.getJSONObject(i);
                                    disctrict_dataArraylist.add(new CommonIdNameModel(single_disctrict.getString("id"),single_disctrict.getString("name")));

                                }

                                ArrayAdapter<CommonIdNameModel> dist_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, disctrict_dataArraylist);
                                dist_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                District_Spinner_ID.setAdapter(dist_adap);


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getCity(int district_id)
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            req_hash.put("district_id", ""+district_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));

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

            Log.d("LastData "," LastData_getCity ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_cities", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                city_dataArrayList=new ArrayList<>();


                                JSONObject cities=result.getJSONObject("cities");

                                JSONArray city_data=cities.getJSONArray("city_data");

                                city_dataArrayList.add(new CommonIdNameModel("","Select City"));
                                for (int i=0;i<city_data.length();i++)
                                {
                                    JSONObject single_obj=city_data.getJSONObject(i);
                                    city_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("name")));

                                }


                                ArrayAdapter<CommonIdNameModel> city_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, city_dataArrayList);
                                city_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                City_Spinner_id.setAdapter(city_adap);


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getWard(int city_id)
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
            req_hash.put("city_id", ""+city_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("city_id", Integer.parseInt(req_hash.get("city_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_ward", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                ward_data_dataArrayList=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("ward");

                                JSONArray ward_data=states_json_Obj.getJSONArray("ward_data");

                                ward_data_dataArrayList.add(new CommonIdNameModel("","Select Ward"));
                                for (int i=0;i<ward_data.length();i++)
                                {
                                    JSONObject single_disctrict=ward_data.getJSONObject(i);
                                    ward_data_dataArrayList.add(new CommonIdNameModel(single_disctrict.getString("id"),single_disctrict.getString("name")));

                                }

                                ArrayAdapter<CommonIdNameModel> dist_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, ward_data_dataArrayList);
                                dist_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Ward_Spinner_ID.setAdapter(dist_adap);


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getArea(int ward_id)
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
            req_hash.put("ward_id", ""+ward_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("ward_id", Integer.parseInt(req_hash.get("ward_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_area", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                area_data_dataArrayList=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("area");

                                JSONArray ward_data=states_json_Obj.getJSONArray("area_data");


                                area_data_dataArrayList.add(new CommonIdNameModel("","Select Area"));
                                for (int i=0;i<ward_data.length();i++)
                                {
                                    JSONObject single_obj=ward_data.getJSONObject(i);
                                    area_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("name")));

                                }

                                ArrayAdapter<CommonIdNameModel> ward_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, area_data_dataArrayList);
                                ward_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Area_Spinner_ID.setAdapter(ward_adap);


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getMandal(int district_id)
    {
if(!progressDialog.isShowing()) {
    progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
    progressDialog.setTitle("Please Wait");
    progressDialog.show();
    progressDialog.setCancelable(false);
}
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            req_hash.put("district_id", ""+district_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_mandal", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                mandal_data_dataArrayList=new ArrayList<>();

                                JSONObject mandal=result.getJSONObject("mandal");

                                JSONArray mandal_data=mandal.getJSONArray("mandal_data");

                                mandal_data_dataArrayList.add(new CommonIdNameModel("","Select Mandal"));
                                for (int i=0;i<mandal_data.length();i++)
                                {
                                    JSONObject single_obj=mandal_data.getJSONObject(i);
                                    mandal_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("name")));
                                }


                                ArrayAdapter<CommonIdNameModel> city_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, mandal_data_dataArrayList);
                                city_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Mandal_Spinner_ID.setAdapter(city_adap);

                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            } catch (Exception e) {
//                                e.printStackTrace();
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getVillage(int mandal_id)
    {
        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            req_hash.put("mandal_id", ""+mandal_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("mandal_id", Integer.parseInt(req_hash.get("mandal_id")));

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

            Log.d("LastData "," LastData_village ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_village", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


//                                disctrict_dataArraylist=new ArrayList<>();

                                JSONObject cities=result.getJSONObject("village");

                                JSONArray city_data=cities.getJSONArray("village_data");


                                for (int i=0;i<city_data.length();i++)
                                {
                                    JSONObject single_obj=city_data.getJSONObject(i);
                                    village_data_dataArrayList.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("name")));
                                }


                                ArrayAdapter<CommonIdNameModel> city_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, village_data_dataArrayList);
                                city_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Village_Spinner_ID.setAdapter(city_adap);


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    //school spinner array data

    public void getStateSchool()
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));

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

            Log.d("LastData_state "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_states", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


//                                StateArrayList_DataModel=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("states");

                                JSONArray state_data=states_json_Obj.getJSONArray("state_data");

                                for (int i=0;i<state_data.length();i++)
                                {
                                    JSONObject single_State=state_data.getJSONObject(i);
                                    StateArrayListSchool.add(new CommonIdNameModel(single_State.getString("id"),single_State.getString("name")));

                                }

                                Log.d(" City_Result_size "," result ## "+StateArrayListSchool.size());


                                ArrayAdapter<CommonIdNameModel> state_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, StateArrayListSchool);
                                state_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                School_State_Spinner_ID.setAdapter(state_adap);
                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getDistrictSchool(int state_id)
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
            req_hash.put("state_id", ""+state_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_districts", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                disctrict_dataArraylistSchool=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("districts");

                                JSONArray disctrict_data=states_json_Obj.getJSONArray("district_data");

                                disctrict_dataArraylistSchool.add(new CommonIdNameModel("","Select District"));
                                for (int i=0;i<disctrict_data.length();i++)
                                {
                                    JSONObject single_disctrict=disctrict_data.getJSONObject(i);
                                    disctrict_dataArraylistSchool.add(new CommonIdNameModel(single_disctrict.getString("id"),single_disctrict.getString("name")));

                                }

                                ArrayAdapter<CommonIdNameModel> dist_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, disctrict_dataArraylistSchool);
                                dist_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                School_District_Spinner_ID.setAdapter(dist_adap);


                                progressDialog.dismiss();

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

    public void getMandalSchool(int district_id)
    {
        if(!progressDialog.isShowing()) {
            progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
            progressDialog.setTitle("Please Wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            req_hash.put("district_id", ""+district_id);

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_pro_mandal", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                mandal_data_dataArrayListSchool=new ArrayList<>();

                                JSONObject mandal=result.getJSONObject("mandal");

                                JSONArray mandal_data=mandal.getJSONArray("mandal_data");


                                for (int i=0;i<mandal_data.length();i++)
                                {
                                    JSONObject single_obj=mandal_data.getJSONObject(i);
                                    mandal_data_dataArrayListSchool.add(new CommonIdNameModel(single_obj.getString("id"),single_obj.getString("name")));
                                }


                                ArrayAdapter<CommonIdNameModel> city_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, mandal_data_dataArrayListSchool);
                                city_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                School_Mandal_Spinner_ID.setAdapter(city_adap);

                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            } catch (Exception e) {
//                                e.printStackTrace();
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }


    public void getCourse()
    {

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JSONObject jsonObject2=new JSONObject();

        JSONObject jsonObject=new JSONObject();

        HashMap<String, String> req_hash = new HashMap<String, String>();
        HashMap<String, String> req_hash2 = new HashMap<String, String>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        try{

            SharedPreferences sharedPreferences=getContext().getSharedPreferences("Log",0);

            req_hash.put("login", sharedPreferences.getString("login",""));
            req_hash.put("password", sharedPreferences.getString("password",""));
            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));

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

            Log.d("LastData_state "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_studying_course", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


//                                StateArrayList_DataModel=new ArrayList<>();

                                JSONObject states_json_Obj=result.getJSONObject("courses");

                                JSONArray state_data=states_json_Obj.getJSONArray("course_data");

                                CourseArrayList.add(new CommonIdNameModel("","Select Course"));

                                for (int i=0;i<state_data.length();i++)
                                {
                                    JSONObject single_State=state_data.getJSONObject(i);
                                    CourseArrayList.add(new CommonIdNameModel(single_State.getString("id"),single_State.getString("name")));
                                }

                                Log.d(" CourseArrayList "," result ## "+CourseArrayList.size());

                                progressDialog.dismiss();

                                ArrayAdapter<CommonIdNameModel> state_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, CourseArrayList);
                                state_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                Studying_course_Spinner.setAdapter(state_adap);

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            getState();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());

                    Toast.makeText(getContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(req);


        }catch(Exception ee){
            Log.d(" errorrr "," errorrr ##"+ee.toString());
        }

    }

}