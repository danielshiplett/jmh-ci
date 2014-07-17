package jmh.ci.plugin;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

public class Json {

	private ObjectMapper om;
	private JsonFactory jf;

	public Json() {
		om = new ObjectMapper();
		jf = new JsonFactory();
	}

	public ObjectMapper getOm() {
		return om;
	}

	public JsonFactory getJf() {
		return jf;
	}
}
