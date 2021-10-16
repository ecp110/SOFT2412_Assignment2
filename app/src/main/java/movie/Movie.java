package movie;

import java.util.*;

public class Movie {

    private String title;
    private String synopsis;
    private int runTime; //in minutes 
    private String classification; 
    private ArrayList<String> cast;
    private String id;
    private String director;
    private Calendar releaseDate;
    
    public Movie (String title, String synopsis, int runTime, String classification, ArrayList<String> cast, String director, String id, Calendar releaseDate){
        this.title = title;
        this.synopsis = synopsis;
        this.runTime = runTime;
        this.classification = classification;
        this.cast = cast;
        this.director = director;
        this.id = id;
        this.releaseDate = releaseDate;
    }

    //GETTER METHODS 
    public String getTitle(){
        return title;
    }

    public String getSynopsis(){
        return synopsis;
    }

    public int getRunTime(){
        return runTime;
    }

    public String getClassification(){
        return classification;
    }

    public ArrayList<String> getCast(){
        return cast;
    }

    public String getDirector(){
        return director;
    }

    public String getID(){
        return id;
    }

    public Calendar getReleaseDate(){
        return releaseDate;
    }

    //SETTER METHODS 
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setSynopsis(String newSyn){
        this.synopsis = newSyn
    }

    public void setRunTime(int newRunTime){
        this.runTime = newRunTime;
    }

    public void setClassification(String newClassification){
        this.classification = newClassification;
    }

    public void setCast(ArrayList<String> newCast){
        this.cast = newCast;
    }

    public void setDirector(String newDirector){
        this.director = newDirector;
    }

    public void setID(String newID){
        this.id = newID
    }

    public void setReleaseDate(Calendar newReleaseDate){
        this.releaseDate = newReleaseDate;
    }

    //More methods 


    //inserts a new actor/actress into the movie cast
    public void insertActor(String name){

        //Checking if actor/actress is already in cast
        if(this.cast.contains(name)){
            System.out.println("This actor is already included in the movie cast");
        }
        else{
            this.cast.add(name);
        }
    }

    public void appendSynopsis(String paragraph){
        this.synopsis = this.synopsis + " " + paragraph;
    }

    public String toString() {
        return "Name: " + this.title + "Run time: " + this.runTime + "Rating: " + this.classification;
    }
}