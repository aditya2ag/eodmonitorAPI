package oracle.fsgbu.eod.monitor.application.services.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.fsgbu.eod.monitor.application.services.service.EodMonitorService;

public class ValidateJson {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateJson.class);

	private static void debug(String msg) {
		LOGGER.debug("ValidateJson" + "-->" + msg);
		System.out.println("ValidateJson" + "-->" + msg);
	}
	public static boolean isValid(String json) {
		debug("Request String"+json);
		if (null != json) {
			try {
				new JSONObject(json);
				debug("It is a valid JSON");
			} catch (JSONException e) {
				debug("failed->JSONException");
				try {
					new JSONArray(json);
					debug("It is a valid JSON Array");
				} catch (JSONException ne) {
					debug("failed->JSONException");
					return false;
				}
			}
		}
		return true;
	}
}
