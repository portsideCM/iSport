package src.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.ArrayList;
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
     * @param p         The protocol to use
     * @param queryType Where in the api to query
     * @param params    Parameters for the query
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

            return new URL(p.getValue(), m_apiHost, filePathBuilder.toString());
        } catch (UnsupportedEncodingException | MalformedURLException e) {
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
        if (status > 299) throw new IOException("Failed to open connection, status code " + status);
        StringBuffer result = new StringBuffer();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inLine;
            while ((inLine = r.readLine()) != null) {
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
    private static CurrentWeather extractCurrentWeatherData(String xmlData) {
        CurrentWeather result = new CurrentWeather();
        JSONObject json = new JSONObject(xmlData);
        if (json.has("weather")) {
            JSONArray weatherArray = json.getJSONArray("weather");
            result.WeatherType = weatherArray.getJSONObject(0).getString("main");
            result.Description = weatherArray.getJSONObject(0).getString("description");
            result.WeatherId = weatherArray.getJSONObject(0).getInt("id");
        }
        if (json.has("main")) {
            JSONObject main = json.getJSONObject("main");
            result.Temp = main.getDouble("temp");
            result.Pressure = main.getDouble("pressure");
            result.Humidity = main.getDouble("humidity");
        }
        if (json.has("wind")) {
            JSONObject wind = json.getJSONObject("wind");
            result.WindSpeed = wind.getDouble("speed");
            result.WindDir = wind.getInt("deg");
        }
        if (json.has("clouds")) {
            JSONObject clouds = json.getJSONObject("clouds");
            result.CloudCover = clouds.getInt("all");
        }
        if (json.has("sys")) {
            JSONObject sys = json.getJSONObject("sys");
            result.Sunrise = Instant.ofEpochSecond(sys.getLong("sunrise"));
            result.Sunset = Instant.ofEpochSecond(sys.getLong("sunset"));
        }
        if (json.has("rain")) {
            JSONObject rain = json.getJSONObject("rain");
            if (rain.has("1h")) result.Rain1h = rain.getDouble("1h");
            if (rain.has("3h")) result.Rain3h = rain.getDouble("3h");
        }
        if (json.has("dt"))
            result.CalcTime = Instant.ofEpochSecond(json.getLong("dt"));
        if (json.has("visibility"))
            result.Visibility = json.getInt("visibility");

        result.TempUnit = "kelvin";

        return result;
    }

    private static Forecast extractForecastXMLData(String xmlData) {
        JSONObject json = new JSONObject(xmlData);
        Forecast result = new Forecast();
        JSONArray weatherList = json.getJSONArray("list");
        result.ForecastList = new ArrayList<>();
        for (int i = 0; i < weatherList.length(); ++i) {
            JSONObject currWeatherData = weatherList.getJSONObject(i);
            ForecastData temp = new ForecastData();

            if (currWeatherData.has("main")) {
                JSONObject main = currWeatherData.getJSONObject("main");
                temp.TempUnit = "kelvin";
                temp.Temp = main.getDouble("temp");
                temp.Pressure = main.getDouble("pressure");
                temp.Humidity = main.getDouble("humidity");
                temp.TempMin = main.getDouble("temp_min");
                temp.TempMax = main.getDouble("temp_max");
            }
            if (currWeatherData.has("weather")) {
                JSONArray weather = currWeatherData.getJSONArray("weather");
                temp.WeatherDescription = weather.getJSONObject(0).getString("description");
                temp.WeatherType = weather.getJSONObject(0).getString("main");
                temp.WeatherId = weather.getJSONObject(0).getInt("id");
            }
            if (currWeatherData.has("clouds")) {
                JSONObject clouds = currWeatherData.getJSONObject("clouds");
                temp.Clouds = clouds.getDouble("all");
            }
            if (currWeatherData.has("wind")) {
                JSONObject wind = currWeatherData.getJSONObject("wind");
                temp.WindSpeed = wind.getDouble("speed");
                temp.WindDir = wind.getInt("deg");
            }
            if (currWeatherData.has("rain")) {
                JSONObject rain = currWeatherData.getJSONObject("rain");
                if (rain.has("3h")) temp.Rain3h = rain.getDouble("3h");
            }
            if (currWeatherData.has("dt")) {
                temp.Time = Instant.ofEpochSecond(currWeatherData.getLong("dt"));
            }
            result.ForecastList.add(temp);
        }
        return result;
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
        return extractCurrentWeatherData(result);
    }


    /**
     * @param lon      Longitude to query
     * @param lat      Latitude to query
     * @param useHTTPS Whether to use HTTPS or HTTP
     * @return A API.CurrentWeather object with data about the current weather at the given location
     * @throws IOException If the location is invalid or the connection to the API server fails
     */
    public CurrentWeather getCurrentWeather(String lon, String lat, boolean useHTTPS) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("lon", lon);
        params.put("lat", lat);
        URL apiURL = createURL((useHTTPS) ? Protocol.HTTPS : Protocol.HTTP, "weather", params);
        String result = getConnectionContents(apiURL);
        return extractCurrentWeatherData(result);
    }

    public Forecast getForecast(String cityName, boolean useHTTPS) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("q", cityName);
        URL apiURL = createURL((useHTTPS) ? Protocol.HTTPS : Protocol.HTTP, "forecast", params);
        String result = getConnectionContents(apiURL);
        Forecast f = extractForecastXMLData(result);
        if (f != null) return f;
        return null;
    }

    public Forecast getForecast(String lon, String lat, boolean useHTTPS) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("lon", lon);
        params.put("lat", lat);
        URL apiURL = createURL((useHTTPS) ? Protocol.HTTPS : Protocol.HTTP, "forecast", params);
        String result = getConnectionContents(apiURL);
        Forecast f = extractForecastXMLData(result);
        if (f != null) return f;
        return null;
    }

    public static APIConnectionSingleton getAPIConnection() {
        if (m_connection == null) {
            m_connection = new APIConnectionSingleton();
        }
        return m_connection;
    }
}