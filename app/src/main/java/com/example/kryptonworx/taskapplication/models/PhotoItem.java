package com.example.kryptonworx.taskapplication.models;

import com.google.gson.annotations.SerializedName;

public class PhotoItem {
    @SerializedName("title")
    private String title;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("owner")
    private String owner;

    @SerializedName("farm")
    private String farm;

    @SerializedName("id")
    private String id;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    public PhotoItem() {
    }

    public PhotoItem(String title, String thumbnail, String owner, String farm, String id, String secret, String server) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.owner = owner;
        this.farm = farm;
        this.id = id;
        this.secret = secret;
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
