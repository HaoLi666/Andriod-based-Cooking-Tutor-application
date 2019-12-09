package com.example.finalproject_cooktutor.entity;

public class Chat {
    private String name;
    private String lastMsg;
    private int imageId;

    public Chat(String name, String lastMsg, int imageId) {
        this.name = name;
        this.lastMsg = lastMsg;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getLastMsg(){
        return lastMsg;
    }

    public int getImageId() {
        return imageId;
    }


}
