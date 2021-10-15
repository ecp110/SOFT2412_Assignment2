import java.util.*;

public class Movie {

    private String title;
    private String description;
    private int length; //in minutes 
    private String classification; 
    private ArrayList<String> cast;
    
    public Movie (String title, String description, int length, String classification, ArrayList<String> cast){
        this.title = title;
        this.description = description;
        this.length = length;
        this.classification = classification;
        this.cast = cast;
    }

    //GETTER METHODS 
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getLength(){
        return length;
    }

    public String getClassification(){
        return classification;
    }

    public ArrayList<String> getCast(){
        return cast;
    }


    //SETTER METHODS 
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setDescription(String newDes){
        this.description = newDes;
    }

    public void setLength(int newLength){
        this.length = newLength;
    }

    public void setClassification(String newClassification){
        this.classification = newClassification;
    }

    public void setCast(ArrayList<String> newCast){
        this.cast = newCast;
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

    public void appendDescription(String paragraph){
        this.description = this.description + " " + paragraph;
    }

    public String toString() {
        return "Name: " + this.title + "Length: " + this.length + "Rating: " + this.classification;
    }
}