package org.example;

import java.util.ArrayList;
import java.util.List;

public class UniversitiessAPI {
    List<UniversitiesAPI> universitiesAPIList= new ArrayList<>();


    public String toString(UniversitiesAPI[] array){
        String uni = "";
        for (UniversitiesAPI university:array) {
            uni += "University Name: " + university.getName() + "\n";
            uni += "Country: " + university.getCountry() + "\n";
            uni += "website: " + university.getDomains().get(0);
            uni += "\n\n";
        }
        return uni;
    }
}
