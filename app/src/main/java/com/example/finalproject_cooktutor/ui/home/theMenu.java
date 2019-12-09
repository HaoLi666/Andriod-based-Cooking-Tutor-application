package com.example.finalproject_cooktutor.ui.home;

import java.io.Serializable;

public class theMenu implements Serializable {
    private String title;
    private String desc;
    private String intro;
    private String author;
    private String menuId;
    private String hot;
    private int photoId;

    /**
     * Constructs a new instance of {@code Object}.
     */
    public theMenu(String title, String desc, String intro, String author, String menuId, String hot, int photoId) {
        this.title=title;
        this.desc=desc;
        this.intro=intro;
        this.photoId=photoId;
        this.author=author;
        this.hot=hot;
        this.menuId=menuId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIntro(String intro) {
        this.intro=intro;
    }

    public void setAuthor(String author) { this.author=author; }

    public void setMenuId(String menuId) {
        this.menuId=menuId;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getDesc() {
        return desc;
    }

    public String getIntro() {
        return intro;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
    }

    public String getMenuId() { return menuId; }

    public String getHot() { return hot; }

    public String getAuthor() {
        return author;
    }
}
