package at.kaisertorian.chrysotile;

import at.kaisertorian.chrysotile.daos.LoggingChannelsDAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class DataBaseManager {
    private static final String FOLDER_LOCATION = "data";
    private static final String DB_FILENAME = "chrysotile.db";
    private static final String URL = "jdbc:sqlite:";

    private final Chrysotile chrysotile;

    private final LoggingChannelsDAO loggingChannelsDAO = new LoggingChannelsDAO(this);

    public DataBaseManager(Chrysotile chrysotile) {
        this.chrysotile = chrysotile;
        loggingChannelsDAO.createTable();
    }

    public interface SQLConsumer{
        void accept(Connection connection) throws SQLException;
    }

    public void connect(SQLConsumer consumer){
        File dbFile = new File(FOLDER_LOCATION + "/" + DB_FILENAME);
        if (!dbFile.exists()){
            try {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            } catch (IOException e){
                System.err.println(e);
            }
        }

        try (Connection connection = DriverManager.getConnection(URL + FOLDER_LOCATION + "/" + DB_FILENAME)) {
            consumer.accept(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
