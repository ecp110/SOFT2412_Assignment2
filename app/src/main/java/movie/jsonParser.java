import java.util.*;
import java.io.*;
import org.json.simple.*;

public class jsonParser {

    public static void main(String[] args) {

        String mPath = "./Databases/Movies.json";

        JSONParser parser = new JSONParser();
        ArrayList<Movie> readMovies = new ArrayList<Movie>();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(mPath));
            JSONObject movies = jsonObject.get("movies");

            String title;
            String id;
            String classification;
            int runtime;
            String director;
            ArrayList<String> cast = null;
            Calendar release = null;
            String synopsis;
            Movie mFull;
            
            for (Object movie : movies) {
                JSONObject m = (JSONObject) movie;

                title = (String) m.get("name");
                id = (String) m.get("id");
                classification = (String) m.get("classification");
                runtime = (Integer) m.get("runtime");
                director = (String) m.get("director");
                synopsis = (String) m.get("synopsis");

                mFull = new Movie(title, synopsis, runtime, classification, cast, id, director, release);
                readMovies.add(mFull);
                mFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
        }

        for (Movie m : readMovies) {
            System.out.println(m.toString());
        }
    }

}