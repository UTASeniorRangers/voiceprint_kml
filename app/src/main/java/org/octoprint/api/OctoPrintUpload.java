package org.octoprint.api;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.json.simple.JsonObject;

/**
 * An object to encapsulate the http request
 *
 * @author rweber
 *
 */
public class OctoPrintUpload {
    private String m_url = null;
    private String m_type = null;
    private String m_params = null;

    public OctoPrintUpload(String url) {
        m_url = "/api/" + url;
        m_type = "POST";
        m_params = new String();
    }

    protected String getURL(){
        return m_url;
    }

    protected String getType(){
        return m_type;
    }

    protected String getParams(){
        return m_params;
    }

    public void setType(String type){
        m_type = type;
    }


    public HttpURLConnection createConnection(URL url, String key) {
        HttpURLConnection connection = null;

        try{

            URL apiUrl = new URL(url + this.getURL());

            //create the connection
            connection = (HttpURLConnection)apiUrl.openConnection();

            //set default connection parameters
            connection.setRequestProperty("X-Api-Key", key);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----VoicePrint");
            connection.setRequestMethod(this.getType());

            if(this.getType().toString().equals("GET")){
                connection.setDoInput(true);
            }
            else{
                connection.setDoOutput(true);
            }

            m_params = "----VoicePrint\n" +
                    "Content-Disposition: form-data; name=\"file\"; filename=\"petersleepy.gcode\"\n" +
                    "Content-Type: application/octet-stream\n" +
                    "\n" +
                    "G29 C-0.8 Z0.3";

//            for(
//                    readline
//                            m_param +=
//            )

            m_params +=      "----VoicePrint\n" +
                    "Content-Disposition: form-data; name=\"select\"\n" +
                    "\n" +
                    "true\n" +
                    "----VoicePrint\n" +
                    "Content-Disposition: form-data; name=\"print\"\n" +
                    "\n" +
                    "true\n" +
                    "----VoicePrint--";

            //connection.setDoInput(true);
            //connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(this.getParams().getBytes());
            os.flush();


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return connection;
    }

}