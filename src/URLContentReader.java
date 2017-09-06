import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class URLContentReader {
    static final Gson GSON = new Gson();
    URL url;
    String inputLine = null;
    PersonsList_1[] person_1;
    PersonsList_2[] person_2 = new PersonsList_2[11];
    BufferedReader br;
    String inputLineAge;
    int minMaleAge = 1000;
    int minFemaleAge = 1000;
    int malePosition;
    int femalePosition;


    public void contentReader() {

        try {
            //Getting connection
            String link = "http://testlodtask20172.azurewebsites.net/task";
            url = new URL(link);
            URLConnection conn = url.openConnection();

            //Reading text
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            inputLine = br.readLine();


            //Conversion from Json
            person_1 = GSON.fromJson(inputLine,PersonsList_1[].class);


            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void ageReader(){

        try {
            for (int i=0; i<11; i++){

                String link = "http://testlodtask20172.azurewebsites.net/task/" + person_1[i].id;
                url = new URL(link);
                URLConnection conn = url.openConnection();

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                inputLineAge = br.readLine();

                person_2[i] = GSON.fromJson(inputLineAge, PersonsList_2.class);

            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        gettingYounger(person_1, person_2);

    }

    public void gettingYounger(PersonsList_1[] person_1, PersonsList_2[] person_2){

        for (int i=0; i<11; i++){

                if (person_2[i].sex.equals("male") && (person_2[i].age < minMaleAge)){
                    minMaleAge = person_2[i].age;
                    malePosition = i;
                }
                else if ((person_2[i].sex.equals("female")) && (person_2[i].age < minFemaleAge)){
                    minFemaleAge = person_2[i].age;
                    femalePosition = i;

                }
        }

        System.out.println(("Name: " + person_1[malePosition].name) + ",  " + "age: " + minMaleAge + ";");
        System.out.println(("Name: " + person_1[femalePosition].name) + ",  " + "age: " + minFemaleAge + ";");

    }
}


