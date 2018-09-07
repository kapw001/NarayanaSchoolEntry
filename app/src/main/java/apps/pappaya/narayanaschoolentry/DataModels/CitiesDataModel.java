package apps.pappaya.narayanaschoolentry.DataModels;

public class CitiesDataModel {

    String City_name,City_ID;

    public CitiesDataModel(String City_name,String City_ID)
    {
       this.City_name=City_name;
       this.City_ID=City_ID;

    }

    public String getCity_ID() {
        return City_ID;
    }

    public String getCity_name() {
        return City_name;
    }

    @Override
    public String toString() {
        return City_name;
    }

}
