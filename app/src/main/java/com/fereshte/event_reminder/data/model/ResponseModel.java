package com.fereshte.event_reminder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("baseId")
    @Expose
    private String baseId;

    @SerializedName("token")
    @Expose
    private String token;

    public ResponseModel(String link, String baseId, String token) {
        this.link = link;
        this.baseId = baseId;
        this.token = token;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
