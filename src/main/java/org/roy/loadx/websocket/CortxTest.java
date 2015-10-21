package org.roy.loadx.websocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.roy.loadx.Util;

public class CortxTest {
	// private static final String BASE_URL = "http://localhost.qa01.cenx.localnet:1234/"; // or "http://balancer01-s02.cenx.localnet/"
	private static final String BASE_URL = "http://balancer01.qa01.cenx.localnet/";

	private static final String HTML_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	private static final String EDN_ACCEPT = "application/vnd.cenx.services.v1+edn;charset=UTF-8";

	public static void main(String[] args) {
		// CookieStore cookieStore = new BasicCookieStore();
		// CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

		CloseableHttpClient client = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet(BASE_URL);
		execute(client, httpGet, HTML_ACCEPT, null, 200);

		HttpPost httpPost = new HttpPost(BASE_URL + "j_security_check");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("j_username", "ikent"));
		nvps.add(new BasicNameValuePair("j_password", "tester"));
		nvps.add(new BasicNameValuePair("submit", "Log in"));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		execute(client, httpPost, HTML_ACCEPT, "application/x-www-form-urlencoded", 302);

		httpGet = new HttpGet(BASE_URL + ";");
		execute(client, httpGet, HTML_ACCEPT, null, 200);

		httpGet = new HttpGet(BASE_URL + "bootstrap-data");
		String edn = execute(client, httpGet, EDN_ACCEPT, null, 200);
		String presentationBaseUrl = Util.findBoundaries(":presentation-base-url \"", "\", ", edn);
		System.out.println(presentationBaseUrl);

		// List<Cookie> cookies = cookieStore.getCookies();
		WebSocketClient webSocketClient = new WebSocketClient(presentationBaseUrl.substring("http://".length()), null);

		webSocketClient.sendMessage(
				"+[[:resource/sub {:path \"/fault/entities\", :method :get, :params {:type \"state\", :count 100, :q \"site:\\\"OH COLUMBUS MTSO\\\" OR site:\\\"PH PHILADELPHIA MSC\\\" OR site:\\\"NE MSC TAUNTON_MA\\\" OR site:\\\"UP MSC ROCHESTER\\\" OR site:\\\"NJ MSC BRANCHBURG 3\\\" OR site:\\\"MC LODGE MTSO7\\\" OR site:\\\"RO MTSO ROCKFORD\\\" OR site:\\\"FL VZW JUPITER MSC\\\" OR site:\\\"MSC SALISBURY\\\" OR site:\\\"PH HARRISBURG MSC\\\" OR site:\\\"PI MSC ST CLAIRSVILLE\\\" OR site:\\\"NA MTSO GREENVILLE\\\" OR site:\\\"MSC ANNAPOLIS JUNCTION\\\" OR site:\\\"AP MTSO APPLETON\\\" OR site:\\\"KM LENEXA MTSO\\\" OR site:\\\"GP GOLDEN VALLEY MSC\\\" OR site:\\\"UTSL-KEARNS MTSO\\\" OR site:\\\"NE MSC HOOKSETT_NH\\\" OR site:\\\"HIMI-MILILANI MSC\\\" OR site:\\\"NA EAST TENNESSEE MTSO\\\" OR site:\\\"CASO SANTA ANA MTSO\\\" OR site:\\\"GC 4160-135886-VERIZON COVINGTON MSC\\\" OR site:\\\"GP BLOOMINGTON MSC\\\" OR site:\\\"UTSL-WEST JORDAN MTSO\\\" OR site:\\\"FL VZW MIAMI MSC\\\" OR site:\\\"WASP-SPOKANE MSC\\\" OR site:\\\"AG LAKESHORE MTSO\\\" OR site:\\\"NA MTSO CHARLESTON NEW\\\" OR site:\\\"HO 0012-173813-VERIZON CICERO MTSO\\\" OR site:\\\"NY MSC WEST NYACK\\\" OR site:\\\"CASO AZUSA MTSO\\\" OR site:\\\"FL VZW JACKSONVILLE MSC\\\" OR site:\\\"WATA-TACOMA MSC\\\" OR site:\\\"FL VZW ORLANDO MSC\\\" OR site:\\\"MW MTSO NEW BERLIN\\\" OR site:\\\"DA SHREVEPORT_MTSO\\\" OR site:\\\"CANO STOCKTON MTSO\\\" OR site:\\\"GP FARGO MSC\\\" OR site:\\\"BOI BOISE MSC\\\" OR site:\\\"NY MSC MINEOLA\\\" OR site:\\\"CASO INLAND MTSO\\\" OR site:\\\"NY MSC FARMINGDALE\\\" OR site:\\\"NJ MSC WAYNE\\\" OR site:\\\"SA LUBBOCK_MTSO [NEW]\\\" OR site:\\\"MSC CHANTILLY\\\" OR site:\\\"NA MTSO RALEIGH THE ROCK\\\" OR site:\\\"UP MSC EAST SYRACUSE\\\" OR site:\\\"CH MTSO ELGIN2\\\" OR site:\\\"NW MSC WINDSOR\\\" OR site:\\\"CANO ROCKLIN MTSO\\\" OR site:\\\"CANO FAIRFIELD MTSO\\\" OR site:\\\"CASO LA MTSO\\\" OR site:\\\"CASO TUSTIN MTSO\\\" OR site:\\\"NA MTSO GREENSBORO\\\" OR site:\\\"MC WESTLAND MTSO5 (DET5) / 0104\\\" OR site:\\\"MSC ADELPHI\\\" OR site:\\\"GC 4160-236895-PENSACOLA MTSO [ALLTEL]\\\" OR site:\\\"NE MSC BILLERICA_MA\\\" OR site:\\\"KM SPRINGFIELD MTSO\\\" OR site:\\\"MSC RICHMOND GOODESBRIDGE\\\" OR site:\\\"NY MSC YONKERS\\\" OR site:\\\"PHO PHOENIX MTSO\\\" OR site:\\\"NA MIDDLE TENNESSEE MTSO\\\" OR site:\\\"MSC RICHMOND SHOCKOE\\\" OR site:\\\"NY MSC WHITESTONE\\\" OR site:\\\"SCR MEMPHIS MTSO\\\" OR site:\\\"IN LOUISVILLE MTSO\\\" OR site:\\\"SCR TULSA MTSO\\\" OR site:\\\"CANO SUNNYVALE MTSO\\\" OR site:\\\"CANO PLEASANTON II MTSO\\\" OR site:\\\"WASE-NORTH SEATTLE MSC\\\" OR site:\\\"MC LAHSER ROAD MTSO6 / 0105\\\" OR site:\\\"KM NEW CARBONDALE MTSO\\\" OR site:\\\"OH MAUMEE MTSO [ALLTEL]\\\" OR site:\\\"SCR FORT SMITH MTSO\\\" OR site:\\\"NA ALLTEL WIRELESS RALEIGH\\\" OR site:\\\"MSC ROANOKE\\\" OR site:\\\"PH MAPLE SHADE MSC\\\" OR site:\\\"DA DFW_MTSO\\\" OR site:\\\"PHO TEMPE MTSO (RNC)\\\" OR site:\\\"CANO BAKERSFIELD MTSO\\\" OR site:\\\"PHO GILBERT MTSO\\\" OR site:\\\"IN EVANSVILLE MTSO\\\" OR site:\\\"CASO VISTA MTSO\\\" OR site:\\\"MC ROYAL OAK MTSO2 / 0101\\\" OR site:\\\"NJ MSC WALL\\\" OR site:\\\"MSC LYNCHBURG [ALLTEL]\\\" OR site:\\\"SB MTSO SOUTH BEND\\\" OR site:\\\"NE MSC WESTBOROUGH_MA\\\" OR site:\\\"TUC TUCSON MTSO\\\" OR site:\\\"KM ST LOUIS MTSO\\\" OR site:\\\"KM DARDENNE MTSO\\\" OR site:\\\"NW MSC WALLINGFORD\\\" OR site:\\\"MSC WOODLAWN\\\" OR site:\\\"NJ MSC JERSEY CITY\\\" OR site:\\\"GP WEST OMAHA MSC\\\" OR site:\\\"GE SUGARLOAF MTSO\\\" OR site:\\\"ABQ ALBUQUERQUE MTSO\\\" OR site:\\\"PI MSC JOHNSTOWN\\\" OR site:\\\"PH PLYMOUTH MEETING MSC\\\"\", :entity-type \"site\"}, :headers {\"Accept\" \"application/vnd.cenx.services.v1+json\", \"cookie\" \"\"}}]]");
		webSocketClient.sendMessage(
				"+[[:resource/sub {:path \"/fault/entities\", :method :get, :params {:type \"state\", :count 100, :q \"site:\\\"CASO SAN DIEGO MTSO\\\" OR site:\\\"OREU-EUGENE MSC\\\" OR site:\\\"IN WESTSIDE MTSO\\\" OR site:\\\"MSC NEWPORT NEWS\\\" OR site:\\\"GP ARMY POST MSC\\\" OR site:\\\"SP MTSO SPRINGFIELD\\\" OR site:\\\"DEN WESTMINSTER MTSO\\\" OR site:\\\"GP OWATONNA MTSO (ALLTEL)\\\" OR site:\\\"NA MTSO CHARLOTTE\\\" OR site:\\\"SA MIDLAND_MTSO\\\" OR site:\\\"IN GUION ROAD MTSO\\\" OR site:\\\"OH AKRON MTSO\\\" OR site:\\\"OH CLEVELAND MTSO\\\" OR site:\\\"SCR LITTLE ROCK CENTRAL MTSO\\\" OR site:\\\"ELP EL PASO OSBORNE MTSO\\\" OR site:\\\"OH LEWIS CENTER MTSO\\\" OR site:\\\"UP MSC NORTH GREENBUSH\\\" OR site:\\\"CASO MARINA MTSO\\\" OR site:\\\"HO 0012-108803-VERIZON COPPERFIELD MTSO II\\\" OR site:\\\"NA MTSO COLUMBIA\\\" OR site:\\\"GP SIOUX FALLS MSC\\\" OR site:\\\"GE MACON MTSO\\\" OR site:\\\"SA SCHERTZ_MTSO\\\" OR site:\\\"NJ MSC BRANCHBURG\\\" OR site:\\\"OH DUFF DRIVE MTSO\\\" OR site:\\\"LSV LAS VEGAS MSC\\\" OR site:\\\"UP MSC BUFFALO\\\" OR site:\\\"CH MTSO HICKORY HILLS\\\" OR site:\\\"CH MTSO ELGIN\\\" OR site:\\\"ORHI-HILLSBORO MSC\\\" OR site:\\\"AKAN-ANCHORAGE MSC\\\" OR site:\\\"PH WILMINGTON MSC\\\" OR site:\\\"GE ALPHARETTA MTSO\\\" OR site:\\\"GC 4160-135443-VERIZON BATON ROUGE MSC\\\" OR site:\\\"PH PITTSTON MSC\\\" OR site:\\\"WARE-REDMOND RIDGE MSC\\\" OR site:\\\"NE MSC WEST ROXBURY_MA\\\" OR site:\\\"PI MSC BRIDGEVILLE\\\" OR site:\\\"DEN CLINTON MTSO\\\" OR site:\\\"FL VZW PLANT CITY MSC\\\" OR site:\\\"DEN AURORA MTSO\\\" OR site:\\\"PI MSC BRIDGEVILLE 2\\\" OR site:\\\"MTHE HELENA MTSO\\\"\", :entity-type \"site\"}, :headers {\"Accept\" \"application/vnd.cenx.services.v1+json\", \"cookie\" \"\"}}]]");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String execute(CloseableHttpClient client, HttpUriRequest request, String accept, String contentType, int statusCode) {
		String body = null;
		request.addHeader("Accept", accept);
		if (contentType != null) {
			request.addHeader("Content-Type", contentType);
		}
		try (CloseableHttpResponse response = client.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() != statusCode) {
				throw new RuntimeException("invalid status code - expected: " + statusCode + " - actual:" + response.getStatusLine().getStatusCode());
			}
			body = EntityUtils.toString(entity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return body;
	}
}
