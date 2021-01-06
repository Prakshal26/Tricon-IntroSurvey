package pojo;

public class CountryList {

    String name;
    String altHeading;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltHeading() {
        return altHeading;
    }

    public void setAltHeading(String altHeading) {

        if (this.altHeading == null) {
            this.altHeading = altHeading;
        } else {
            this.altHeading = this.altHeading + altHeading;
        }
        this.altHeading = altHeading;
    }
}
