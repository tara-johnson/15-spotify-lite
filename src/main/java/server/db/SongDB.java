package server.db;

import org.mindrot.jbcrypt.BCrypt;
import server.models.Song;
import server.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDB {
    public static final List<Song> songs = new ArrayList<>();

    private static Connection mConn;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/spotifylite";

            try {
                mConn = DriverManager.getConnection(url);
                ResultSet results = mConn.createStatement().executeQuery("SELECT * FROM songs");
                while (results.next()) {
                    int id = results.getInt("songid");
                    String username = results.getString("username");
                    String artist = results.getString("artist");
                    String title = results.getString("title");
                    String path = results.getString("path");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("DB error.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Postgres driver not configured correctly.");
        }
    }

    public static Song createSong(String username, String artist, String title, String path) {
        String sql = "INSERT INTO songs(username, artist, title, path) VALUES('%s', '%s', '%s', '%s') RETURNING (songid);";
        sql = String.format(sql, username, artist, title, path);

        try (ResultSet results = mConn.createStatement().executeQuery(sql)) {
            results.next();
            int songid = results.getInt("songid");
            Song song = new Song(songid, username, artist, title, path);
            return song;
        } catch (SQLException e) {
            return null;
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (ResultSet results = mConn.createStatement().executeQuery(sql)) {
            while (results.next()) {
                int id = results.getInt("id");
                String username = results.getString("username");
                String passhash = results.getString("passhash");

                User user = new User(id, username, passhash);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User getUserById(int searchId) {
        String sql = "SELECT * FROM users WHERE id=%d";
        sql = String.format(sql, searchId);

        try (ResultSet results = mConn.createStatement().executeQuery(sql)) {
            results.next();
            int id = results.getInt("id");
            String username = results.getString("username");
            String passhash = results.getString("passhash");

            User user = new User(id, username, passhash);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserByName(String searchUsername) {
        String sql = "SELECT * FROM users WHERE username='%s'";
        sql = String.format(sql, searchUsername);

        try (ResultSet results = mConn.createStatement().executeQuery(sql)) {
            if (!results.next()) {
                // no matching user
                return null;
            }

            int id = results.getInt("id");
            String username = results.getString("username").trim();
            String passhash = results.getString("passhash").trim();

            User user = new User(id, username, passhash);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User updateUserBio (int searchId, String newBio) {
        String sql = "UPDATE users SET bio='%s' WHERE id=%d RETURNING *;";
        sql = String.format(sql, newBio, searchId);

        try (ResultSet results = mConn.createStatement().executeQuery(sql)) {
            results.next();

            int id = results.getInt("id");
            String username = results.getString("username");
            String passhash = results.getString("passhash");

            User user = new User(id, username, passhash);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteUser (int searchId) {
        String sql = "DELETE FROM users WHERE id=%d;";
        sql = String.format(sql, searchId);

        try {
            mConn.createStatement().execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}