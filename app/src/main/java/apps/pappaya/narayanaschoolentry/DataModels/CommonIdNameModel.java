package apps.pappaya.narayanaschoolentry.DataModels;

public class CommonIdNameModel {

    String ID,NAME;
    public CommonIdNameModel(String ID,String NAME)
    {
        this.ID=ID;
        this.NAME=NAME;
    }

    public String getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
    @Override
    public String toString() {
        return NAME;
    }



}
