//package edu.wm.step1;
//
//import org.apache.http.auth.AuthScope;
//
//import com.loopj.android.http.*;
//
//public class Step1RestClient {
//	
//	  private static final String BASE_URL = "http://step1.herokuapp.com/";
//
//	  private static AsyncHttpClient client = new AsyncHttpClient();
//	  
//	  
//	  public static void setBasicAuth(String user, String pass){
//	        AuthScope scope = AuthScope.ANY;
//	        client.setBasicAuth(user, pass, scope);
//	    }
//
//	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//	      client.get(getAbsoluteUrl(url), params, responseHandler);
//	  }
//
//	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//	      client.post(getAbsoluteUrl(url), params, responseHandler);
//	  }
//
//	  private static String getAbsoluteUrl(String relativeUrl) {
//	      return BASE_URL + relativeUrl;
//	  }
//	  
//	  public static void setCookieStore(PersistentCookieStore cookieStore) {
//		  
//		    client.setCookieStore(cookieStore);
//		}
//	}
