package apps.pappaya.narayanaschoolentry.DataModels;

import android.os.Parcelable;

import java.io.Serializable;

public class StudentsDataModel implements Serializable{
    String Student_ID,Student_Name,father_name,studying_course,mobile,state_name,district_name;

    String location_type;

    String city_name,ward_name,area_name,mandal_name,village_name;

    String employee_name;

    String school_name,school_district_name,school_state_name,school_mandal_name;




    public StudentsDataModel(String Student_ID,String Student_Name,String father_name,String studying_course,
            String mobile,String state_name,String district_name,
            String city_name,String ward_name,String area_name,String mandal_name,String village_name,
            String school_name,String school_district_name,String school_state_name,String school_mandal_name,
             String employee_name,String location_type
                            )
    {
        this.Student_ID=Student_ID;
        this.Student_Name=Student_Name;
        this.father_name=father_name;
        this.studying_course=studying_course;
        this.mobile=mobile;
        this.state_name=state_name;
        this.district_name=district_name;

        this.city_name=city_name;
        this.ward_name=ward_name;
        this.area_name=area_name;
        this.mandal_name=mandal_name;
        this.village_name=village_name;

        this.school_name=school_name;
        this.school_district_name=school_district_name;
        this.school_state_name=school_state_name;
        this.school_mandal_name=school_mandal_name;

        this.location_type=location_type;
        this.employee_name=employee_name;

    }

    public String getStudent_ID() {
        return Student_ID;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getStudying_course() {
        return studying_course;
    }

    public String getMobile() {
        return mobile;
    }

    public String getState_name() {
        return state_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getLocation_type() {
        return location_type;
    }

    public String getMandal_name() {
        return mandal_name;
    }

    public String getSchool_district_name() {
        return school_district_name;
    }

    public String getSchool_mandal_name() {
        return school_mandal_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public String getSchool_state_name() {
        return school_state_name;
    }

    public String getVillage_name() {
        return village_name;
    }

    public String getWard_name() {
        return ward_name;
    }
}
