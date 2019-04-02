package org.octoprint.api.model;
import java.io.IOException;
import java.io.Writer;

//import org.json.simple.JsonObject;
//import org.json.simple.Jsonable;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import org.json.JSONException;
import org.json.JSONObject;
import org.octoprint.api.util.JSONLoader;
import com.github.cliftonlabs.json_simple.JsonKey;

/**
 * Representation of the connection state of the printer 
 * 
 * @author rweber
 */

public final class ConnectionState implements Jsonable, JSONLoader {
	private JsonObject m_json = null;
	
	public ConnectionState() {
		m_json = new JsonObject();
	}

	/**
	 * @return if OctoPrint is connected to a 3D printer
	 */
	public boolean isConnected(){
		//return true if state does not equal closed
		return !m_json.get("state").equals("Closed");
	}

	/**
	 * My Implementation, BUT UNNECESSARY, because if condition already checks if printer is connected
	 * @return connected state, "closed" if not connected
	 */
	public String getState() throws JSONException {
		String result = null;

		if(!m_json.get("state").equals("Closed"))
		{
			result = m_json.getString(new JsonKey() {
				@Override
				public String getKey() {
					return "state";
				}

				@Override
				public Object getValue() {
					return false;
				}
			});
		}

		return result;
	}


	/**
	 * @return connected baudrate, null if nothing available
	 */
	public Long getBaudrate() throws JSONException {
		Long result = null;
		
		if(m_json.get("baudrate") != null)
		{

			result = m_json.getLong(new JsonKey() {
				@Override
				public String getKey() { return "baudrate"; }

				@Override
				public Object getValue() {
					return false;
				}
			});
		}
		
		return result;
	}
	
	/**
	 * @return port as a string (most likely in the /dev/tty0 format)
	 */
	public String getPort(){
		String result = null;
		
		if(m_json.containsKey("port"))
		{
			result = m_json.getString(new JsonKey() {
				@Override
				public String getKey() { return "port"; }

				@Override
				public Object getValue() {
					return false;
				}
			});
		}
		
		return result;
	}
	
	/**
	 * @return name of connection profile
	 */
	public String getPrinterProfile(){
		return m_json.getString(new JsonKey() {
			@Override
			public String getKey() { return "printerProfile"; }

			@Override
			public Object getValue() {
				return false;
			}
		});
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
