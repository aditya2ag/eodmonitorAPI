package oracle.fsgbu.eod.monitor.application.services.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.axis.encoding.Base64;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CallWebserviceRest {
	static Logger log = LogManager.getLogger(CallWebserviceRest.class.getName());
	public String token;
	public int respCode = 500;
	public String timeout = "1000";

	public String[] postRequest(String reqMethod, String pInputHeader, String pInputParam, String pInputJSON,
			String pUrl) throws ClientProtocolException, IOException {
		String responseString = "";
		String[] outputString = { null, null, null, null };

		outputString[0] = String.valueOf(this.respCode);
		outputString[1] = null;
		outputString[3] = "1 KB";
		outputString[2] = "0 ms";
		
		
		int timeout = Integer.parseInt(this.timeout);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpPost method = new HttpPost(pUrl);

		JSONObject jsonObjHeader = new JSONObject(pInputHeader);
		jsonObjHeader.keySet().forEach(key -> {
			method.setHeader(key, jsonObjHeader.getString(key));
		});

		method.setHeader("Content-Type", "application/json; charset=utf-8");
		// method.setHeader("Authorization", "Bearer " + this.token);
		HttpParams params = new BasicHttpParams();

		JSONObject jsonObjParam = new JSONObject(pInputParam);
		jsonObjParam.keySet().forEach(key -> {
			params.setParameter(key, jsonObjParam.getString(key));
		});

		method.setParams(params);

		log.debug("Have set the headers.");

		System.out.println("Input request body:" + pInputJSON);
		StringEntity input = new StringEntity(pInputJSON);
		method.setEntity(input);

		InputStreamReader isr = null;
		long start = System.currentTimeMillis();

		HttpResponse response = httpClient.execute(method);

		long finish = System.currentTimeMillis();

		this.respCode = response.getStatusLine().getStatusCode();
		outputString[0] = String.valueOf(this.respCode);

		log.debug("Server Response :" + this.respCode);
		System.out.println("Server Response :" + this.respCode);
		isr = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8);

		BufferedReader in = new BufferedReader(isr);

		while ((responseString = in.readLine()) != null) {
			outputString[1] = outputString[1] + responseString;
		}

		long timeElapsed = finish - start;
		outputString[2] = (String.valueOf(timeElapsed) + " ms");

		CallWebserviceRest.ObjectSizeFetcher objectSizeFetcher = new CallWebserviceRest.ObjectSizeFetcher();

		//outputString[3] = String.valueOf(objectSizeFetcher.getObjectSize(outputString[1])) + " bytes";

		outputString[3] = "1 KB";
		
		log.debug("Got the response.");
		System.out.println("Got the response.");
		log.debug(outputString[1]);
		System.out.println(outputString);
		log.debug("--------------");

		httpClient.close();
		return outputString;
	}

	public Object getJsonObject(Object obj, String apiRequest) {
		JSONObject jsonObj = new JSONObject(apiRequest);
		jsonObj.keySet().forEach(key -> {
			Object keyValue = jsonObj.get(key);

		});
		return obj;
	}

	public String[] getRequest(String reqMethod, String pInputHeader, String pInputParam, String pInputJSON, String pUrl)
			throws ClientProtocolException, IOException {
		String responseString = "";
		String[] outputString = new String[4];

		outputString[0] = String.valueOf(this.respCode);
		outputString[1] = null;
		outputString[3] = "1 KB";
		outputString[2] = "0 ms";
		
		int timeout = Integer.parseInt(this.timeout);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpGet method = new HttpGet(pUrl);
		System.out.println(pInputHeader);

		if (null != pInputHeader) {
			JSONObject jsonObjHeader = new JSONObject(pInputHeader);
			jsonObjHeader.keySet().forEach(key -> {
				method.setHeader(key, jsonObjHeader.getString(key));
			});

		}
		method.setHeader("Content-Type", "application/json; charset=utf-8");
		HttpParams params = new BasicHttpParams();

		if (null !=pInputParam) {
			JSONObject jsonObjParam = new JSONObject(pInputParam);
			jsonObjParam.keySet().forEach(key -> {
				params.setParameter(key, jsonObjParam.getString(key));
			});

			method.setParams(params);
		}
		log.debug("Have set the headers.");
		InputStreamReader isr = null;
		long start = System.currentTimeMillis();

		HttpResponse response = httpClient.execute(method);

		long finish = System.currentTimeMillis();

		this.respCode = response.getStatusLine().getStatusCode();
		outputString[0] = String.valueOf(this.respCode);

		log.debug("Server Response :" + this.respCode);
		System.out.println("Server Response :" + this.respCode);
		isr = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8);

		BufferedReader in = new BufferedReader(isr);

		while ((responseString = in.readLine()) != null) {
			if(null == outputString[1]) {
				outputString[1] = responseString;
			} else {
			outputString[1] += responseString;
			}
		}

		long timeElapsed = finish - start;
		outputString[2] = (String.valueOf(timeElapsed) + " ms");

		CallWebserviceRest.ObjectSizeFetcher objectSizeFetcher = new CallWebserviceRest.ObjectSizeFetcher();

		//outputString[3] = String.valueOf(objectSizeFetcher.getObjectSize(outputString[1])) + " bytes";
		outputString[3] = "1 KB";
		
		log.debug("Got the response.");
		System.out.println("Got the response.");
		log.debug(outputString[1]);
		System.out.println(outputString);
		log.debug("--------------");

		httpClient.close();
		return outputString;
	}

	public String patchRequest(String reqMethod, String pInputHeader, String pInputJSON, String pUrl)
			throws ClientProtocolException, IOException {
		String responseString = "";
		String outputString = "";

		int timeout = Integer.parseInt(this.timeout);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpPatch method = new HttpPatch(pUrl);

		method.setHeader("Content-Type", "application/json; charset=utf-8");
		method.setHeader("Authorization", "Bearer " + this.token);

		log.debug("Have set the headers.");

		StringEntity input = new StringEntity(pInputJSON);
		method.setEntity(input);

		InputStreamReader isr = null;

		HttpResponse response = httpClient.execute(method);
		this.respCode = response.getStatusLine().getStatusCode();

		log.debug("Server Response :" + this.respCode);

		isr = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8);

		BufferedReader in = new BufferedReader(isr);

		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}

		log.debug("Got the response.");
		log.debug(outputString);
		log.debug("--------------");

		httpClient.close();
		return outputString;
	}

	public String putRequest(String reqMethod, String pInputHeader, String pInputJSON, String pUrl)
			throws ClientProtocolException, IOException {
		String responseString = "";
		String outputString = "";

		int timeout = Integer.parseInt(this.timeout);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		HttpPut method = new HttpPut(pUrl);

		method.setHeader("Content-Type", "application/json; charset=utf-8");
		method.setHeader("Authorization", "Bearer " + this.token);

		log.debug("Have set the headers.");

		StringEntity input = new StringEntity(pInputJSON);
		method.setEntity(input);

		InputStreamReader isr = null;

		HttpResponse response = httpClient.execute(method);
		this.respCode = response.getStatusLine().getStatusCode();

		log.debug("Server Response :" + this.respCode);

		isr = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8);

		BufferedReader in = new BufferedReader(isr);

		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}

		log.debug("Got the response.");
		log.debug(outputString);
		log.debug("--------------");

		httpClient.close();
		return outputString;
	}

	public byte[] getBinaryRequest(String reqMethod, String pInputJSON, String pUrl, String apiVersion)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet method = new HttpGet(pUrl);

		method.setHeader("Content-Type", "application/json; charset=utf-8");

		log.debug("Have set the headers.");

		InputStream is = null;

		HttpResponse response = httpClient.execute(method);
		this.respCode = response.getStatusLine().getStatusCode();

		log.debug("Server Response :" + this.respCode);

		is = response.getEntity().getContent();

		byte[] byteChunk = new byte[4096];
		byte[] resp = new byte[(int) response.getEntity().getContentLength()];
		int idx = 0;
		int n;
		int x;
		for (x = 0; (n = is.read(byteChunk)) > 0 && x < n;) {
			resp[idx] = byteChunk[x];

			x++;
			idx++;
		}

		log.debug("Got the response.");

		log.debug("--------------");

		httpClient.close();
		return resp;
	}

	public String getServerTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		return dateFormat.format(calendar.getTime());
	}

	public String generateAuthHttpHeader(String appId, String appSecret, String verb, String contentMd5, String date,
			String pathAndQuery, String posId) {
		log.debug("In generateAuthHttpHeader");

		log.debug("verb :" + verb);
		log.debug("contentMd5 :" + contentMd5);
		log.debug("date :" + date);
		log.debug("pathAndQuery :" + pathAndQuery);

		byte[] secret = Base64.decode(appSecret);
		String data = verb.toUpperCase() + "\n" + contentMd5 + "\n" + date + "\n" + pathAndQuery.toLowerCase() + "\n"
				+ posId;
		log.debug(data);

		data = encodeHmacSHA256(secret, data.getBytes(StandardCharsets.UTF_8));
		return data;
	}

	public String encodeHmacSHA256(byte[] key, byte[] data) {
		try {
			String algo = "HmacSHA256";
			SecretKeySpec secret_key = new SecretKeySpec(key, algo);

			Mac sha256_HMAC = Mac.getInstance(algo);
			sha256_HMAC.init(secret_key);

			byte[] result = sha256_HMAC.doFinal(data);
			return Base64.encode(result);
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
		} catch (InvalidKeyException e) {
			log.error(e);
		}

		return null;
	}

	public String getContentMD5(String contentStr) {
		log.debug("In get md5 :" + contentStr);

		if (contentStr.equals("")) {
			return contentStr;
		}

		log.debug("About to generate md5");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] contentMD5Byte = md.digest(contentStr.getBytes(StandardCharsets.UTF_8));
			return Base64.encode(contentMD5Byte);
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
		}
		return "";
	}

	public static class ObjectSizeFetcher {
		private static Instrumentation instrumentation ;

		public void premain(String args, Instrumentation inst) {
			instrumentation = inst;
		}

		public long getObjectSize(Object o) {
			System.out.println(o.toString());
			
			return instrumentation.getObjectSize(o);
		}
	}
}