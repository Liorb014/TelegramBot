package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UniversitiesAPI {
    private String name;
    private String country;
    private List<String> domains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    @Override
    public String toString() {
        return "UniversitiesAPI{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", domains=" + domains +
                '}';
    }


    public static String getUniversities(int limit , String country) {
        try {
        String apiUrl = "http://universities.hipolabs.com/search?country="+country;
        // Create the URL object with the API endpoint
        URL url = new URL(apiUrl);

        // Open a connection to the URL
        HttpURLConnection connection = null;

            connection = (HttpURLConnection) url.openConnection();


            // Set the request method (e.g., GET)
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response body
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Use Gson to deserialize the JSON response
                Gson gson = new Gson();
                UniversitiesAPI[] universities = gson.fromJson(response.toString(), UniversitiesAPI[].class);
                String uni = "";
                for (UniversitiesAPI university : Arrays.stream(universities).limit(limit).collect(Collectors.toList())) {
                    uni += "University Name: " + university.getName() + "\n";
                    uni += "Country: " + university.getCountry() + "\n";
                    uni += "website: " + university.getDomains().get(0);
                    uni += "\n\n";
                }
                return uni;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return "error";
    }
}