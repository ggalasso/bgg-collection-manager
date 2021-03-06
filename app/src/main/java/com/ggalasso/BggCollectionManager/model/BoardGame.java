package com.ggalasso.BggCollectionManager.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.math.BigDecimal;
import java.util.ArrayList;

@Root
public class BoardGame {

    //Id of the Game, this is required otherwise we shouldn't be able to find the game.
    @Attribute
    private String id;
    @ElementList(entry = "name", inline = true, required = false)
    private ArrayList<Name> names;
    private String primaryName;
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
    private String syncValue;
    private String thumbnailPath;

    public BoardGame() {
    }

    public BoardGame(
            String id
            , String primaryName
            , String yearPub
            , String description
            , String thumbnail
            , String image
            , double rating
            , String rank
            , int minPlayers
            , int maxPlayers
            , int playTime
            , int minTime
            , int maxTime
            , int minAge
    ) {
        this.id = id;
        this.primaryName = primaryName;
        this.yearPub = yearPub;
        this.description = description;
        this.thumbnail = thumbnail;
        this.image = image;
        this.rating = rating;
        this.rank = rank;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playTime = playTime;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.minAge = minAge;
    }

    // This is used to rehydrate the boardgame from the table
    public BoardGame(
            String id
            , String primaryName
            , String yearPub
            , String description
            , String thumbnail
            , String image
            , double rating
            , String rank
            , int minPlayers
            , int maxPlayers
            , int playTime
            , int minTime
            , int maxTime
            , int minAge
            , String thumbnailPath
    ) {
        this.id = id;
        this.primaryName = primaryName;
        this.yearPub = yearPub;
        this.description = description;
        this.thumbnail = thumbnail;
        this.image = image;
        this.rating = rating;
        this.rank = rank;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playTime = playTime;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.minAge = minAge;
        this.thumbnailPath = thumbnailPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryName() {
        if ((primaryName == null) || primaryName.isEmpty()) {
            for (Name name : names) {
                if (name.getType().equals("primary")) {
                    return name.getValue();
                }
            }
        }
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getYearPub() {
        return yearPub;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getMinTime() {
        return minTime;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public String getRank() {
        return rank;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailURL() {
        return thumbnail;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getThumbnailURLFileName() {
        String url = getThumbnailURL();
        int i = url.lastIndexOf("/");
        String fileName =  url.substring(i+1, url.length());
        return fileName;
    }

    public String getSyncValue() {
        return syncValue;
    }

    public void setSyncValue(String syncValue) {
        this.syncValue = syncValue;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public ArrayList<Name> getNames() {
        return names;
    }

    public void setNames(ArrayList<Name> names) {
        this.names = names;
    }

    public ArrayList<Link> getCategoryLinks() {
        return getLinksOfType("boardgamecategory");
    }

    public ArrayList<Link> getMechanicLinks() {
        return getLinksOfType("boardgamemechanic");
    }

    public ArrayList<Link> getLinksOfType(String filter) {
        ArrayList<Link> results = new ArrayList<>();
        for(Link link : getLinks()) {
            if(link.getType().equals(filter)){
                results.add(link);
            }
        }
        return results;
    }

    public String getRatingToString() {
        Double truncatedDouble = new BigDecimal(this.rating)
                .setScale(1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        return "Rating: " + truncatedDouble;
    }

    public String getMinMaxTimeToString(){
        if (this.minTime == this.maxTime){
            return Integer.toString(this.maxTime) + " Min";
        } else if(this.minTime > this.maxTime) {
            return Integer.toString(this.minTime) + " Min";
        } else {
            return Integer.toString(this.minTime) + "-" + Integer.toString(this.maxTime) + " Min";
        }
    }

    public String getMinMaxPlayersToString(){
        if (this.minPlayers == this.maxPlayers){
            return Integer.toString(this.minPlayers) + (this.minPlayers == 1 ? " Player" : " Players");
        } else if (this.minPlayers > this.maxPlayers){
            return Integer.toString(this.minPlayers) + (this.minPlayers == 1 ? " Player" : " Players");
        } else {
            return Integer.toString(this.minPlayers) + "-" + Integer.toString(this.maxPlayers) + " Players";
        }
    }

}
