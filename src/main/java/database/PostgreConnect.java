package database;

import pojo.CountryData;
import pojo.CountryList;

import java.sql.*;

public class PostgreConnect {

    static Connection connection;

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/europa_intro_survey",
                            "postgres", "root");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }
        return connection;
    }

    public static int insertCountryList(CountryList countryList) {

        int id = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT into country (name,xml_id,alt_heading)" + "VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            statement.setString(i++, countryList.getName());
            statement.setString(i++, countryList.getXmlId());
            statement.setString(i++, countryList.getAltHeading());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                id=rs.getInt(1);
            }
            statement.close();
            return id;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return id;
    }

    public static int insertCountryData(CountryData countryData) {

        int id = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT into country_data (heading,description, parent_id, country_id)" + "VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            statement.setString(i++, countryData.getHeading());
            statement.setString(i++, countryData.getDescription());
            statement.setInt(i++, countryData.getParent_id());
            statement.setInt(i++, countryData.getCountry_id());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()){
                id=rs.getInt(1);
            }
            statement.close();
            return id;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return id;
    }
}
