package com.fereshte.event_reminder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestModel {

    @SerializedName("baseId")
    @Expose
    private String baseId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("hasRemind")
    @Expose
    private Boolean hasRemind;

    @SerializedName("remind")
    @Expose
    private boolean[] remind;

    @SerializedName("rrule")
    @Expose
    private String rrule;

    @SerializedName("occasion")
    @Expose
    private Integer occasion;

    @SerializedName("color")
    @Expose
    private int color;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("location")
    @Expose
    private String location;

    public RequestModel(String  baseId, String title, Boolean hasRemind, boolean[] remind, String rrule,
                        Integer occasion, int color, String startTime, String endTime, String duration,
                        String description, String link, String location) {
        this.baseId = baseId;
        this.title = title;
        this.hasRemind = hasRemind;
        this.remind = remind;
        this.rrule = rrule;
        this.occasion = occasion;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.description = description;
        this.link = link;
        this.location = location;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getHasRemind() {
        return hasRemind;
    }

    public void setHasRemind(Boolean hasRemind) {
        this.hasRemind = hasRemind;
    }

    public boolean[] getRemind() {
        return remind;
    }

    public void setRemind(boolean[] remind) {
        this.remind = remind;
    }

    public String getRrule() {
        return rrule;
    }

    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

    public Integer getOccasion() {
        return occasion;
    }

    public void setOccasion(Integer occasion) {
        this.occasion = occasion;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
