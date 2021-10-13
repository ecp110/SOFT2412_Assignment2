public class movies{

    private String title;
    private String description;
    private String length; //in minutes 
    private String classification; 
    
    public movies(String title, String description, String length, String classification){
        this.title = title;
        this.description = description;
        this.length = length;
        this.classification = classification;
    }

    //GETTER METHODS 
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getLength(){
        return length;
    }

    public String getClassification(){
        return classification;
    }


    //SETTER METHODS 
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setDescription(String newDes){
        this.description = newDes;
    }

    public void setLength(String newLength){
        this.length = newLength;
    }

    public void setClassification(String newClassification){
        this.classification = newClassification;
    }
}