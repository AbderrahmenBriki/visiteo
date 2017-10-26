package com.appsolute.cel.models;

/**
 * Created by lucienguimaraes on 12/05/2017.
 */

public class Observation {

    private String name;
    private boolean selected;

    public Observation() {
    }

    public Observation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
