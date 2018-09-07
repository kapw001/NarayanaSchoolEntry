package apps.pappaya.narayanaschoolentry.Fragments.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.pappaya.narayanaschoolentry.Activities.LoginActivity;
import apps.pappaya.narayanaschoolentry.Activities.MainActivity;
import apps.pappaya.narayanaschoolentry.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    CircleImageView profile_image;

    TextView Mobile_Number,Email_id,Employee_ID,Employee_NAME;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferences;

    private OnFragmentInteractionListener mListener;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("User Profile");

        ((MainActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((MainActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.toolbar_draw);

        ImageView imageView=    ((MainActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.home_IM);
        TextView textView=    ((MainActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.tvTitle);
        textView.setText("User Profile");
        imageView.setVisibility(View.INVISIBLE);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user_profile, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        Email_id=(TextView)view.findViewById(R.id.Email_id);
        Mobile_Number=(TextView)view.findViewById(R.id.Mobile_Number);
        profile_image=(CircleImageView)view.findViewById(R.id.profile_image);
        Employee_ID=(TextView)view.findViewById(R.id.Employee_ID);
        Employee_NAME=(TextView)view.findViewById(R.id.Employee_NAME);

        sharedPreferences=getContext().getSharedPreferences("Log",0);
        Log.d(" user_name ##"," user_name ## "+sharedPreferences.getString("user_name",""));

        Email_id.setText(sharedPreferences.getString("employee_email",""));
        Employee_NAME.setText(sharedPreferences.getString("user_name",""));
        Mobile_Number.setText(sharedPreferences.getString("employee_mobile",""));
        Employee_ID.setText(sharedPreferences.getString("employee_id",""));

        Bitmap imagebmp = decodeBitmap(getContext(), sharedPreferences.getString("user_image",""));

        profile_image.setImageBitmap(imagebmp);

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


    public static Bitmap decodeBitmap(Context context, String encodedImage) {
        Log.e("", "decodeBitmap : " + encodedImage);
        if (!encodedImage.equalsIgnoreCase("")) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } else {
            return BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher);
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);
        ;
        MenuItem item_logout = menu.findItem(R.id.logout_IV);
        ImageView logout = item_logout.getActionView().findViewById(R.id.logout_IMG_ID);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().commit();
                Toast.makeText(getContext(), "You were logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

    }

}