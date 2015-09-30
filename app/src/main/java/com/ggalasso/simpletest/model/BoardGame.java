package com.ggalasso.simpletest.model;

import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;


@Root
public class BoardGame {
    //Id of the Game, this is required otherwise we shouldn't be able to find the game.
    @Attribute
    private String id;
    @ElementList(entry = "name", inline = true, required = false)
    private ArrayList<Name> names;
    @ElementList(entry = "link", inline = true, required = false)
    private ArrayList<Link> links;
    @Path("yearpublished")
    @Attribute(name = "value", required = false)
    private String yearPub;
    @Element(required = false)
    private String description;
    @Element(name = "thumbnail", required = false)
    private String thumbnail;
    @Element(name = "image", required = false)
    private String image;
    @Path("statistics/ratings/bayesaverage")
    @Attribute(name = "value", required = false)
    private double rating;
    @Path("statistics/ratings/ranks/rank[1]")
    @Attribute(name = "value", required = false)
    private String rank;
    @Path("minplayers")
    @Attribute(name = "value", required = false)
    private int minPlayers;
    @Path("maxplayers")
    @Attribute(name = "value", required = false)
    private int maxPlayers;
    @Path("playingtime")
    @Attribute(name = "value", required = false)
    private int playTime;
    @Path("minplaytime")
    @Attribute(name = "value", required = false)
    private int minTime;
    @Path("maxplaytime")
    @Attribute(name = "value", required = false)
    private int maxTime;
    @Path("minage")
    @Attribute(name = "value", required = false)
    private int minAge;

    public BoardGame() {
    }

    public BoardGame(String id, String name) {
        this.id = id;
        this.names = new ArrayList<Name>();
        this.names.add(new Name(name));
    }

    public String getPrimaryName() {
        for (Name name : names) {
            if (name.getType().equals("primary")) {
                return name.getValue();
            }
        }
        return "";
    }

    public String getYeapublished() {
        return yearPub;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }

}