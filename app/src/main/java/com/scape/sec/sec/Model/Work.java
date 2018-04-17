package com.scape.sec.sec.Model;

/**
 * Created by Me on 15/09/2016.
 */
public class Work {

    private String work_id;
    private String name;
    private String image;
    private String audio;
    private String location;
    private boolean active;

    public Work(String work_id,String name,String image, String audio, String location, boolean active){
        this.work_id = work_id;
        this.name = name;
        this.image = image;
        this.audio = audio;
        this.location = location;
        this.active = active;
    }

    public Work() {

    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
