package apps.pappaya.narayanaschoolentry.Fragments.ViewDetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.pappaya.narayanaschoolentry.Activities.LoginActivity;
import apps.pappaya.narayanaschoolentry.Activities.MainActivity;
import apps.pappaya.narayanaschoolentry.Adapters.StudentsListAdapter;
import apps.pappaya.narayanaschoolentry.Common.VolleySingleton;
import apps.pappaya.narayanaschoolentry.DataModels.CitiesDataModel;
import apps.pappaya.narayanaschoolentry.DataModels.CommonIdNameModel;
import apps.pappaya.narayanaschoolentry.DataModels.StudentsDataModel;
import apps.pappaya.narayanaschoolentry.DataModels.WardDataModel;
import apps.pappaya.narayanaschoolentry.R;
import dmax.dialog.SpotsDialog;

import static apps.pappaya.narayanaschoolentry.ConstantData.Service_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private android.app.AlertDialog progressDialog;

    Spinner City_Spinner_id;
    Spinner Ward_Spinner_ID;
    Spinner state_Spinner_ID,District_Spinner_ID;
    ArrayList<CommonIdNameModel> ward_data_dataArrayList=new ArrayList<>();

    ArrayList<CommonIdNameModel> StateArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> disctrict_dataArraylist=new ArrayList<>();

    ArrayList<CitiesDataModel> citiesDataModelArrayList;
    ArrayList<StudentsDataModel> studentsDataModelArrayList;
    ArrayList<CommonIdNameModel> city_dataArrayList=new ArrayList<>();

    RecyclerView Student_List_RV_ID;

    SharedPreferences sharedPreferences;

    Spinner Area_Spinner_ID,Mandal_Spinner_ID,Village_Spinner_ID;

    TextView Count_TV_ID,Count_district_TV,City_count_TV,Count_ward_TV,Count_Village_TV,Count_mandal_TV,Count_area_TV;

    ArrayList<CommonIdNameModel> area_data_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> mandal_data_dataArrayList=new ArrayList<>();
    ArrayList<CommonIdNameModel> village_data_dataArrayList=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewDetailsFragment newInstance(String param1, String param2) {
        ViewDetailsFragment fragment = new ViewDetailsFragment();
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

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("View Details");

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.toolbar_draw);

        ImageView imageView=    ((MainActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.home_IM);
        TextView textView=    ((MainActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
        textView.setText("View Details");
        imageView.setVisibility(View.GONE);

        View view=inflater.inflate(R.layout.fragment_view_details, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("View Leads");
        City_Spinner_id=(Spinner)view.findViewById(R.id.City_Spinner_id);
        Ward_Spinner_ID=(Spinner)view.findViewById(R.id.Ward_Spinner_ID);

        state_Spinner_ID=(Spinner)view.findViewById(R.id.state_Spinner_ID);
        District_Spinner_ID=(Spinner)view.findViewById(R.id.District_Spinner_ID);
        City_Spinner_id=(Spinner)view.findViewById(R.id.City_Spinner_id);

        Village_Spinner_ID=(Spinner)view.findViewById(R.id.Village_Spinner_ID);
        Mandal_Spinner_ID=(Spinner)view.findViewById(R.id.Mandal_Spinner_ID);
        Area_Spinner_ID=(Spinner)view.findViewById(R.id.Area_Spinner_ID);

        Count_TV_ID=(TextView) view.findViewById(R.id.Count_TV_ID);
        Count_district_TV=(TextView) view.findViewById(R.id.Count_district_TV);
        City_count_TV=(TextView) view.findViewById(R.id.City_count_TV);
        Count_ward_TV=(TextView) view.findViewById(R.id.Count_ward_TV);

        Count_area_TV=(TextView) view.findViewById(R.id.Count_area_TV);
        Count_mandal_TV=(TextView) view.findViewById(R.id.Count_mandal_TV);
        Count_Village_TV=(TextView) view.findViewById(R.id.Count_Village_TV);

        Student_List_RV_ID=(RecyclerView)view.findViewById(R.id.Student_List_RV_ID);
        sharedPreferences=getContext().getSharedPreferences("Log",0);



        city_dataArrayList.add(new CommonIdNameModel("","Select City"));

        getState();


        state_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

//                if (++check_state_Spinner_ID>1) {
                if (!StateArrayList.get(position).getID().equals(""))
                {
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
                    Count_TV_ID.setText("-");
                    Count_district_TV.setText("-");
                    City_count_TV.setText("-");
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                    Count_mandal_TV.setText("-");
                    Count_Village_TV.setText("-");
                    getDistrict(Integer.parseInt(StateArrayList.get(position).getID()),position);
//                getStateCount(Integer.parseInt(StateArrayList.get(position).getID()));
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
                    Count_TV_ID.setText("-");
                    Count_district_TV.setText("-");
                    City_count_TV.setText("-");
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                    Count_mandal_TV.setText("-");
                    Count_Village_TV.setText("-");
                }

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
                    Count_district_TV.setText("-");
                    City_count_TV.setText("-");
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                    Count_mandal_TV.setText("-");
                    Count_Village_TV.setText("-");

                    getDistrictCount(Integer.parseInt(StateArrayList.get(position).getID()),Integer.parseInt(disctrict_dataArraylist.get(position).getID()),position);

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

                    Count_district_TV.setText("-");
                    City_count_TV.setText("-");
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                    Count_mandal_TV.setText("-");
                    Count_Village_TV.setText("-");
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

                    City_count_TV.setText("-");
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                    getCityCount();

                }else{
                    Ward_Spinner_ID.setAdapter(null);
                    Area_Spinner_ID.setAdapter(null);
                    ward_data_dataArrayList.clear();
                    area_data_dataArrayList.clear();

                    City_count_TV.setText("-");
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                }
//                }
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

                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                    getWardCount();

                }else{
                    Area_Spinner_ID.setAdapter(null);
                    area_data_dataArrayList.clear();
                    Count_ward_TV.setText("-");
                    Count_area_TV.setText("-");
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        Area_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!area_data_dataArrayList.get(position).getID().equals("")) {
                    getAreaCount();

                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Mandal_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!mandal_data_dataArrayList.get(position).getID().equals("")) {

                    Count_mandal_TV.setText("-");
                    Count_Village_TV.setText("-");
                    getMandalCount();

                }else{
                    Count_mandal_TV.setText("-");
                    Count_Village_TV.setText("-");
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        Village_Spinner_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!village_data_dataArrayList.get(position).getID().equals("")) {

                    Count_Village_TV.setText("-");
                    getVillageCount();

                }else{
                    Count_Village_TV.setText("-");
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

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

    public void network()
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
//            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
//            req_hash.put("params",jsonObject.toString());

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

            Log.d("LastData "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/show_pro_cities", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");


                                 citiesDataModelArrayList=new ArrayList<>();

                                JSONObject cities_json_Obj=result.getJSONObject("cities");

                                JSONArray city_data=cities_json_Obj.getJSONArray("city_data");

                                for (int i=0;i<city_data.length();i++)
                                {
                                    JSONObject single=city_data.getJSONObject(i);
                                    citiesDataModelArrayList.add(new CitiesDataModel(single.getString("city_name"),single.getString("city_id")));
                                }

                                Log.d(" City_Result_size "," result ## "+citiesDataModelArrayList.size());


                                ArrayAdapter<CitiesDataModel> adapter =
                                        new ArrayAdapter<CitiesDataModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, citiesDataModelArrayList);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                City_Spinner_id.setAdapter(adapter);
//                                Ward_Spinner_ID.setAdapter(ward_adapter);
//
//                                if (wardDataModelArrayList.size()!=0)
//                                    Ward_Spinner_ID.setSelection(0);


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





    public void getStudentList(int Ward_pos,int City_pos)
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
            req_hash.put("city_id", citiesDataModelArrayList.get(City_pos).getCity_ID());
//            req_hash.put("ward_id", wardDataModelArrayList.get(Ward_pos).getCity_ID());
//            req_hash.put("emp_db_id", sharedPreferences.getString("emp_db_id",""));
//            req_hash.put("params",jsonObject.toString());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("city_id", Integer.parseInt(req_hash.get("city_id")));
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

            Log.d("LastData "," LastData_stuList ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/pro_student_data", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONObject result=response.getJSONObject("result");

                                Log.d(" Student_Result_size "," result ## "+result.toString());

                                studentsDataModelArrayList=new ArrayList<>();

                                JSONObject student_data=result.getJSONObject("student_data");

                                JSONArray student_data_array=student_data.getJSONArray("student_data");


                                Log.d(" student_data_array ## "," size ## "+student_data_array.length());

                                for (int i=0;i<student_data_array.length();i++)
                                {
                                    JSONObject single=student_data_array.getJSONObject(i);
                                    Log.d(" single ## "," db_id "+single.getString("db_id"));
                                    Log.d(" single ## "," name "+single.getString("name"));
//                                    studentsDataModelArrayList.add(new StudentsDataModel(single.getString("db_id"),single.getString("name")));

        studentsDataModelArrayList.add(new StudentsDataModel(single.getString("db_id"),
        single.getString("name"),
        single.getString("father_name"),single.getString("studying_course") ,
        single.getString("mobile"),
        single.getString("state_name"),single.getString("district_name") ,
        single.getString("city_name"),single.getString("ward_name") ,single.getString("area_name"),
        single.getString("mandal_name"),single.getString("village_name"),
        single.getString("school_name"),single.getString("school_district_name"),
        single.getString("school_state_name"),single.getString("school_mandal_name"),
        single.getString("employee_name"),single.getString("location_type")
                                    ));
                                }

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                Student_List_RV_ID.setLayoutManager(mLayoutManager);
                                Student_List_RV_ID.setItemAnimator(new DefaultItemAnimator());
                                Student_List_RV_ID.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

                                Student_List_RV_ID.setAdapter(new StudentsListAdapter(getContext(),studentsDataModelArrayList));

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

                                JSONObject states_json_Obj=result.getJSONObject("states");

                                JSONArray state_data=states_json_Obj.getJSONArray("state_data");

                                StateArrayList.add(new CommonIdNameModel("","Select State"));

                                for (int i=0;i<state_data.length();i++)
                                {
                                    JSONObject single_State=state_data.getJSONObject(i);
                                    StateArrayList.add(new CommonIdNameModel(single_State.getString("id"),single_State.getString("name")));

                                }

                                Log.d(" StateArrayList "," result ## "+StateArrayList.size());

                                progressDialog.dismiss();
                                ArrayAdapter<CommonIdNameModel> state_adap =
                                        new ArrayAdapter<CommonIdNameModel>(getContext(),  android.R.layout.simple_spinner_dropdown_item, StateArrayList);
                                state_adap.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);


                                state_Spinner_ID.setAdapter(state_adap);

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


    public void getDistrict(int state_id,final int position)
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


                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                            getStateCount(Integer.parseInt(StateArrayList.get(position).getID()));


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



    public void getStateCount(int state_id)
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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_state", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");



                                Count_TV_ID.setText(result.getString("count"));

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



    public void getDistrictCount(int state_id,int district_id,final int position)
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
            req_hash.put("state_id", ""+StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));
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

        Log.d("datass "," datass_count_by_district ##"+data);

        try{
//            JSONObject jj=new JSONObject(data);

//            JSONObject jj = new JSONObject(data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1));
            JSONObject jj = new JSONObject("{"+data+"}");

            Log.d("LastData "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_district", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" District_Count "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");

                                Count_district_TV.setText(result.getString("count"));

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                            getCity(District_Spinner_ID.getSelectedItemPosition());
                            getMandal();

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

            req_hash.put("district_id", ""+disctrict_dataArraylist.get(district_id).getID());

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

    public void getCityCount()
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
            req_hash.put("state_id", ""+StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("city_id", ""+city_dataArrayList.get(City_Spinner_id.getSelectedItemPosition()).getID());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));
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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_city", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");

                                City_count_TV.setText(result.getString("count"));

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                            getWard();

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

    public void getWard()
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
            req_hash.put("city_id", ""+city_dataArrayList.get(City_Spinner_id.getSelectedItemPosition()).getID());

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

    public void getWardCount()
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
            req_hash.put("state_id", ""+StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("city_id", ""+city_dataArrayList.get(City_Spinner_id.getSelectedItemPosition()).getID());
            req_hash.put("ward_id", ""+ward_data_dataArrayList.get(Ward_Spinner_ID.getSelectedItemPosition()).getID());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));
            jsonObject.put("city_id", Integer.parseInt(req_hash.get("city_id")));
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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_ward", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");

                                Count_ward_TV.setText(result.getString("count"));

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                            getArea();

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
    public void getAreaCount()
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
            req_hash.put("state_id", ""+StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("city_id", ""+city_dataArrayList.get(City_Spinner_id.getSelectedItemPosition()).getID());
            req_hash.put("ward_id", ""+ward_data_dataArrayList.get(Ward_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("area_id", ""+area_data_dataArrayList.get(Area_Spinner_ID.getSelectedItemPosition()).getID());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));
            jsonObject.put("city_id", Integer.parseInt(req_hash.get("city_id")));
            jsonObject.put("ward_id", Integer.parseInt(req_hash.get("ward_id")));
            jsonObject.put("area_id", Integer.parseInt(req_hash.get("area_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_ward", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");

                                Count_area_TV.setText(result.getString("count"));

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

//                            getMandal();

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

    public void getArea()
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
            req_hash.put("ward_id", ""+ward_data_dataArrayList.get(Ward_Spinner_ID.getSelectedItemPosition()).getID());

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

    public void getMandal()
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

            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());

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

    public void getMandalCount()
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
            req_hash.put("state_id", ""+StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("mandal_id", ""+mandal_data_dataArrayList.get(Mandal_Spinner_ID.getSelectedItemPosition()).getID());

            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));
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

            Log.d("LastData "," LastData ##"+jj.toString());

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_mandal", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");

                                Count_mandal_TV.setText(result.getString("count"));

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

                                getVillage();
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

    public void getVillage()
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

            req_hash.put("mandal_id", ""+mandal_data_dataArrayList.get(Mandal_Spinner_ID.getSelectedItemPosition()).getID());

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


                                village_data_dataArrayList=new ArrayList<>();

                                village_data_dataArrayList.add(new CommonIdNameModel("","Select Village"));

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

    public void getVillageCount()
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
            req_hash.put("state_id", ""+StateArrayList.get(state_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("district_id", ""+disctrict_dataArraylist.get(District_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("mandal_id", ""+mandal_data_dataArrayList.get(Mandal_Spinner_ID.getSelectedItemPosition()).getID());
            req_hash.put("village_id", ""+village_data_dataArrayList.get(Village_Spinner_ID.getSelectedItemPosition()).getID());


            Log.d(" req1 "," req ###"+req_hash.toString());

            jsonObject.put("login", req_hash.get("login"));
            jsonObject.put("password", req_hash.get("password"));
            jsonObject.put("emp_db_id", Integer.parseInt(req_hash.get("emp_db_id")));
            jsonObject.put("state_id", Integer.parseInt(req_hash.get("state_id")));
            jsonObject.put("district_id", Integer.parseInt(req_hash.get("district_id")));
            jsonObject.put("mandal_id", Integer.parseInt(req_hash.get("mandal_id")));
            jsonObject.put("village_id", Integer.parseInt(req_hash.get("village_id")));

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

            JsonObjectRequest req = new JsonObjectRequest(Service_URL+"/mobile/rest/get_student_count_by_village", new JSONObject("{"+data+"}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                Log.d(" City_Result "," result ## "+response.toString());
                                JSONObject result=response.getJSONObject("result");

                                Count_Village_TV.setText(result.getString("count"));

                            } catch (Exception e) {
//                                e.printStackTrace();
                                progressDialog.dismiss();
//                                newsletter_rv_ID.setAdapter(null);
                                e.printStackTrace();

                            }

                            progressDialog.dismiss();

//                            getMandal();

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