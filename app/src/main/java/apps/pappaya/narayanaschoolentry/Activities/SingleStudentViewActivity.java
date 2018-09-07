package apps.pappaya.narayanaschoolentry.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import apps.pappaya.narayanaschoolentry.DataModels.StudentsDataModel;
import apps.pappaya.narayanaschoolentry.R;

public class SingleStudentViewActivity extends AppCompatActivity {

    ArrayList<StudentsDataModel> studentsDataModelArrayList;

    TextView Name_TV,Father_Name_TV;
    TextView course_TV,Mobile_TV;
    TextView State_TV,District_TV;

    TextView City_TV,Ward_TV,Area_TV;
    TextView Mandal_TV,Village_TV;

    TextView School_TV,School_District_TV;
    TextView School_State_TV,School_Mandal_TV;

    LinearLayout Urban_LinLay_ID,Rural_LinLay_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_student_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        StudentsDataModel studentsDataModel = (StudentsDataModel) bundle.getSerializable("SingleStudent");

        Log.d(" getStudent_Name ## "," getStudent_Name ##"+studentsDataModel.getStudent_Name());

        Urban_LinLay_ID=(LinearLayout)findViewById(R.id.Urban_LinLay_ID);
        Rural_LinLay_ID=(LinearLayout)findViewById(R.id.Rural_LinLay_ID);

        Name_TV=(TextView)findViewById(R.id.Name_TV);
        Father_Name_TV=(TextView)findViewById(R.id.Father_Name_TV);
        course_TV=(TextView)findViewById(R.id.course_TV);
        Mobile_TV=(TextView)findViewById(R.id.Mobile_TV);
        State_TV=(TextView)findViewById(R.id.State_TV);
        District_TV=(TextView)findViewById(R.id.District_TV);

        City_TV=(TextView)findViewById(R.id.City_TV);
        Ward_TV=(TextView)findViewById(R.id.Ward_TV);
        Area_TV=(TextView)findViewById(R.id.Area_TV);
        Mandal_TV=(TextView)findViewById(R.id.Mandal_TV);
        Village_TV=(TextView)findViewById(R.id.Village_TV);


        School_TV=(TextView)findViewById(R.id.School_TV);
        School_District_TV=(TextView)findViewById(R.id.School_District_TV);
        School_District_TV=(TextView)findViewById(R.id.School_District_TV);
        School_State_TV=(TextView)findViewById(R.id.School_State_TV);
        School_Mandal_TV=(TextView)findViewById(R.id.School_Mandal_TV);

        Name_TV.setText(studentsDataModel.getStudent_Name());
        Father_Name_TV.setText(studentsDataModel.getFather_name());
        course_TV.setText(studentsDataModel.getStudying_course());
        Mobile_TV.setText(studentsDataModel.getMobile());
        State_TV.setText(studentsDataModel.getState_name());
        District_TV.setText(studentsDataModel.getDistrict_name());

        City_TV.setText(studentsDataModel.getCity_name());
        Ward_TV.setText(studentsDataModel.getWard_name());
        Area_TV.setText(studentsDataModel.getArea_name());
        Mandal_TV.setText(studentsDataModel.getMandal_name());
        Village_TV.setText(studentsDataModel.getVillage_name());

        School_TV.setText(studentsDataModel.getSchool_name());
        School_District_TV.setText(studentsDataModel.getSchool_district_name());
        School_State_TV.setText(studentsDataModel.getSchool_state_name());
        School_Mandal_TV.setText(studentsDataModel.getSchool_mandal_name());


        if (studentsDataModel.getLocation_type().equals("urban"))
        {
            Urban_LinLay_ID.setVisibility(View.VISIBLE);
            Rural_LinLay_ID.setVisibility(View.GONE);
        }else{
            Urban_LinLay_ID.setVisibility(View.GONE);
            Rural_LinLay_ID.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
