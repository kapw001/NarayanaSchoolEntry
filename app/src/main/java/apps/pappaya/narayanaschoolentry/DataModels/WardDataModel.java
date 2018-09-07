package apps.pappaya.narayanaschoolentry.DataModels;

public class WardDataModel {

    String ward_name,ward_id;

    public WardDataModel(String ward_name, String ward_id)
    {
       this.ward_name=ward_name;
       this.ward_id=ward_id;

    }

    public String getCity_ID() {
        return ward_id;
    }

    public String getCity_name() {
        return ward_name;
    }

    @Override
    public String toString() {
        return ward_name;
    }

}
