package com.example.sit708_task_7_1p;

public class LostFoundItem {
    private String title;
    private String description;
    private String phone;
    private String date;
    private String location;
    public LostFoundItem(String title, String description, String phone, String date, String location) {
        this.title = title;
        this.description = description;
        this.phone = phone;
        this.date = date;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
