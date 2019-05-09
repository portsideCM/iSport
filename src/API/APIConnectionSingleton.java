package API;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class APIConnectionSingleton {
    //HTTP or HTTPS
    public enum Protocol {
        HTTP("http"), HTTPS("https");

        private String val;

        public String getValue() {
            return this.val;
        }

        private Protocol(String v) {
            this.val = v;
        }
    }

    private static APIConnectionSingleton m_connection;

    //Secret key
    private static final String m_apikey = "11ca36974caa7e63998abae6cee9a5e1";
    //Start of every api call
    private static final String m_apiHost = "api.openweathermap.org";
    //Where the data is hosted on the api host
    private static final String m_urlStart = "/data/2.5/";

    //Private constructor
    private APIConnectionSingleton() {
        
    }

    /**
     * @param p The protocol to use
     * @param queryType Where in the api to query
     * @param params Parameters for the query
     * @return URL to the requested location, with parameters inserted safely
     */
    private static URL createURL(Protocol p, String queryType, Map<String, String> params) {
        try {
            StringBuilder filePathBuilder = new StringBuilder();
            filePathBuilder.append(m_urlStart);
            filePathBuilder.append(queryType);
            filePathBuilder.append("?");

            for (Map.Entry<String, String> parameter : params.entrySet()) {
                filePathBuilder.append(URLEncoder.encode(parameter.getKey(), "UTF-8"));
                filePathBuilder.append("=");
                filePathBuilder.append(URLEncoder.encode(parameter.getValue(), "UTF-8"));
                filePathBuilder.append("&");
            }
            filePathBuilder.append("APPID=" + m_apikey);

            filePathBuilder.append("&mode=xml");

            return new URL(p.getValue(), m_apiHost, filePathBuilder.toString());
        }
        catch(UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url URL to read from
     * @return The contents of the response from the URL
     * @throws IOException Thrown if the connection or authentication fails
     */
    private static String getConnectionContents(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int status = conn.getResponseCode();
        if(status > 299) throw new IOException("Failed to open connection, status code " + status);
        StringBuffer result = new StringBuffer();
        try(BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inLine;
            while((inLine = r.readLine()) != null) {
                result.append(inLine);
            }
        }
        conn.disconnect();
        return result.toString();
    }

    /**
     * @param xmlData XML form in the structure of OpenWeatherMaps current weather API
     * @return API.CurrentWeather object with the same data as the passed in XML
     */
    private static CurrentWeather extractCurrentWeatherXMLData(String xmlData) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CurrentWeather.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            CurrentWeather currentWeather = (CurrentWeather) jaxbUnmarshaller.unmarshal(new StringReader(xmlData));
            return currentWeather;
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param cityName API.City name to get weather from
     * @param useHTTPS Whether to use HTTPS or HTTP
     * @return A API.CurrentWeather object representing the current weather in the given city
     * @throws IOException If the city name is invalid or the connection to the API server fails
     */
    public CurrentWeather getCurrentWeather(String cityName, boolean useHTTPS) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("q", cityName);
        URL apiURL = createURL((useHTTPS) ? Protocol.HTTPS : Protocol.HTTP, "weather", params);
        String result = getConnectionContents(apiURL);
        return extractCurrentWeatherXMLData(result);
    }

    /**
     * @param lon Longitude to query
     * @param lat Latitude to query
     * @param useHTTPS Whether to use HTTPS or HTTP
     * @return A API.CurrentWeather object with data about the current weather at the given location
     * @throws IOException If the location is invalid or the connection to the API server fails
     */
    public CurrentWeather getCurrentWeather(String lon, String lat, boolean useHTTPS) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("lon", lon); params.put("lat", lat);
        URL apiURL = createURL((useHTTPS) ? Protocol.HTTPS : Protocol.HTTP, "weather", params);
        String result = getConnectionContents(apiURL);
        return extractCurrentWeatherXMLData(result);
    }

    public static APIConnectionSingleton getAPIConnection() {
        if(m_connection == null) {
            m_connection = new APIConnectionSingleton();
        }
        return m_connection;
    }
}