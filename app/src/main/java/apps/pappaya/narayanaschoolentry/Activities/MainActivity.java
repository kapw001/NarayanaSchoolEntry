package apps.pappaya.narayanaschoolentry.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import apps.pappaya.narayanaschoolentry.Fragments.EnterDetail.EnterDetailFragment;
import apps.pappaya.narayanaschoolentry.Fragments.UserProfile.UserProfileFragment;
import apps.pappaya.narayanaschoolentry.Fragments.ViewDetails.ViewDetailsFragment;
import apps.pappaya.narayanaschoolentry.R;

public class MainActivity extends AppCompatActivity implements EnterDetailFragment.OnFragmentInteractionListener,ViewDetailsFragment.OnFragmentInteractionListener,UserProfileFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ChangeFragment(new EnterDetailFragment());
    }

    public void ChangeFragment(Fragment fragment)
    {

        getSupportFragmentManager().beginTransaction().replace(R.id.Frame_id,fragment,"").commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_enter_details:
                    if(getLocalClassName().equals("EnterDetailFragment")){
                        Log.d(" getLocalClassName ## "," getLocalClassName ## "+getLocalClassName().equals("EnterDetailFragment"));
                    }else{
                        ChangeFragment(new EnterDetailFragment());
                    }

                    return true;
                case R.id.navigation_view_detail:
                    ChangeFragment(new ViewDetailsFragment());
                    return true;
                case R.id.navigation_user_profile:
                    ChangeFragment(new UserProfileFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}