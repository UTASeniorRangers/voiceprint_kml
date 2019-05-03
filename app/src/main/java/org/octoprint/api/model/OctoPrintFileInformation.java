package org.octoprint.api.model;

import java.io.IOException;
import java.io.Writer;

//import org.json.simple.JsonObject;
//import org.json.simple.Jsonable;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import org.octoprint.api.util.JSONLoader;

/**
 * A representation of a File Information on the OctoPrint server http://docs.octoprint.org/en/master/api/datamodel.html#file-information 
 * 
 * This is a top-level type that serves as the extension point for specific Files or Folders
 * 
 * @author rweber
 * 
 */
public abstract class OctoPrintFileInformation implements Jsonable, JSONLoader {
	protected JsonObject m_data = null;
	protected FileType m_type = null;
	
	public OctoPrintFileInformation(FileType t,JsonObject json) {
		m_type = t;
		m_data = json;
	}

	public String getName(){
		return m_data.getString(new JsonKey() {
			@Override
			public String getKey() { return "name"; }

			@Override
			public Object getValue() {
				return false;
			}
		});
	}


	/**
	 * @return the type of file (gcode,model,folder)
	 */
	public FileType getType(){
		return m_type;
	}
	
	public String getPath(){
		return m_data.getString(new JsonKey() {
			@Override
			public String getKey() { return "path"; }

			@Override
			public Object getValue() {
				return false;
			}
		});
	}

	/**
	 * ***
	 * @return size of Long type in bytes
	 */
	public Long getSize(){

		return m_data.getLong(new JsonKey() {
			@Override
			public String getKey() { return "size"; }

			@Override
			public Object getValue() {
				return false;
			}
		});
	}

	/**
	 * ***
	 * @return the date of file uploaded
	 */
	public Long getDate(){

		return m_data.getLong(new JsonKey() {
			@Override
			public String getKey() {
				return "date";
			}

			@Override
			public Object getValue() {
				return null;
			}
		});
	}



	public float getEstimatedPrintTime() {

		return ((JsonObject)(m_data.get("gcodeAnalysis"))).getFloat(new JsonKey() {
			@Override
			public String getKey() {
				return "estimatedPrintTime";
			}
			@Override
			public Object getValue() {
				return null;
			}
		});
	}


	@Override
	public void loadJSON(JsonObject json) {
		m_data = json;
	}

	@Override
	public String toJson() {
		return m_data.toJson();
	}

	@Override
	public void toJson(Writer writable) throws IOException {
		writable.write(this.toJson());
	}
}
