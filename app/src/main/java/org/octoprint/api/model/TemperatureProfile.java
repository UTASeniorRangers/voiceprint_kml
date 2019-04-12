package org.octoprint.api.model;

import java.io.IOException;
import java.io.Writer;

//import org.json.simple.JsonObject;
//import org.json.simple.Jsonable;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.octoprint.api.util.JSONLoader;


/**
 * Temperature Profile information as provided by OctoPrint Settings - currently doesn't handle more than one extruder!
 * 
 * @author rweber
 */
public final class TemperatureProfile implements Jsonable, JSONLoader {
	private JsonObject m_json = null;
	
	public TemperatureProfile() {
		m_json = new JsonObject();
	}

	public String getName(){
		return m_json.get("name").toString();
	}
	
	/**
	 * @return the bed temp of this profile, in degrees celsius
	 */
	public Long getBedTemp(){
		return m_json.getLong(new JsonKey() {
			@Override
			public String getKey() { return "bed"; }

			@Override
			public Object getValue() {
				return false;
			}
		});
	}
	
	/**
	 * @return the extruder temp of this profile, in degrees celsius
	 */
	public Long getExtruderTemp(){
		return m_json.getLong(new JsonKey() {
			@Override
			public String getKey() { return "extruder"; }

			@Override
			public Object getValue() {
				return false;
			}
		});
	}
	
	@Override 
	public String toString(){
		return this.getName();
	}
	
	@Override
	public void loadJSON(JsonObject json) {
		m_json = json;
	}

	@Override
	public String toJson() {
		return m_json.toJson();
	}

	@Override
	public void toJson(Writer arg0) throws IOException {
		arg0.write(this.toJson());
	}

}
