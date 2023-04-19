/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.entity;

/**
 *
 * @author hayth
 */
public class StatTournoi {
    private String type;
    private int stat;
    public StatTournoi() {
    }

    public StatTournoi(String type, int stat) {
        this.type = type;
        this.stat = stat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "TypeTournoi{" + "type=" + type + ", stat=" + stat + '}';
    }
}
