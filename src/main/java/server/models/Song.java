package server.models;

public class Song {
    private static final String DEFAULT_NAME = "No Name";
    private static final String DEFAULT_ARTIST = "Unknown";
    private static final String DEFAULT_SONG = "Unknown";
    private static final String DEFAULT_PATH = "/uploads";

    public int songid;
    public String username;
    public String artist;
    public String title;
    public String path;

    public Song() {
        this(DEFAULT_NAME, DEFAULT_ARTIST, DEFAULT_SONG, DEFAULT_PATH);
    }

    public Song(int songid, String artist, String title, String path) {
        this.artist = artist;
        this.title = title;
        this.path = path;
    }

    public Song(String username, String artist, String title, String path) {
        this(-1, username, artist, title, path);
    }

    public Song(int id, String username, String artist, String title, String path) {
        this.username = username;
        this.artist = artist;
        this.title = title;
        this.path = path;
    }
}