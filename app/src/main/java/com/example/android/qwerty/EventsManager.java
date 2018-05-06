package com.example.android.qwerty;

/**
 * Created by sanchit on 4/14/2018.
 */

public class EventsManager {
    private String title;
    private String society;
    private String description;
    private String image;
    private String registrationLink;

    public EventsManager() {
    }

    public EventsManager(String title, String society, String description, String image,String registrationLink) {
        this.title = title;
        this.society = society;
        this.description = description;
        this.image = image;
        this.registrationLink=registrationLink;
    }
    public String getRegistrationLink(){return registrationLink;}
    public void setRegistrationLink(String registrationLink){this.registrationLink=registrationLink;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
