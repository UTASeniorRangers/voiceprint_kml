package org.octoprint.api.util;

//import org.json.simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonObject;


public interface JSONLoader {

	public void loadJSON(JsonObject json);
}
