package models;

public class Book {

    Integer id;

    String tile;

    //List<Author> authors

    public Book(Integer id, String tile) {
        this.id = id;
        this.tile = tile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }
}
