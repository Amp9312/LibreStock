package com.shogrenjacobdev.librestock;

public class Collection {
    private int id;
    private String name;
    private String type;
    private int size;

    public Collection(int id, String name, String type, int size) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return
                "id = " + id + "\n" +
                        "name = " + name + "\n" +
                        "type = " + type + "\n" +
                        "size = " + size + "\n";
    }
}
