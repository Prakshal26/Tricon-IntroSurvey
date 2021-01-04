package database;

import pojo.Country;

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

    public static int insertCountry(Country country) {

        int id = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT into country_data (entry_id,heading, description, parent_id, country_id)" + "VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            statement.setInt(i++, country.getEntryId());
            statement.setString(i++, country.getHeading());
            statement.setString(i++, country.getDescription());
            statement.setInt(i++, country.getParent_id());
            statement.setInt(i++, country.getCountry_id());
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
