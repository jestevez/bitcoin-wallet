package de.schildbach.wallet.info.onixcoin.api;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import java.io.IOException;
import java.math.BigDecimal;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jestevez
 */
public class OnixcoinInfoApi {

    private static final String ONIXCOIN_INFO_API = "https://www.onixcoin.info/api";

    private static String sendPostFormUrlencode(String url, String paramUrlencode) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, paramUrlencode);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Cache-Control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String out = response.body().string();
        return out;
    }

    private static String sendGet(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cache-Control", "no-cache")
                .build();
        Response response = client.newCall(request).execute();
        String out = response.body().string();
        return out;
    }

    public static JSONObject txSend(String rawtx) throws Exception {
        String url = ONIXCOIN_INFO_API + "/tx/send";
        String json = sendPostFormUrlencode(url, "rawtx="+rawtx);
        JSONObject req = new JSONObject(json);
        return req;
    }

    public static JSONObject tx(String tx) throws Exception {
        String url = ONIXCOIN_INFO_API + "/tx/" + tx;
        String json = sendGet(url);
        JSONObject req = new JSONObject(json);
        return req;
    }

    public static JSONObject addr(String address) throws Exception {
        String url = ONIXCOIN_INFO_API + "/addr/" + address;
        String json = sendGet(url);
        JSONObject req = new JSONObject(json);
        return req;
    }

    public static JSONArray addrUtxo(String address) throws Exception {
        String url = ONIXCOIN_INFO_API + "/addr/" + address + "/utxo";
        String json = sendGet(url);
        JSONArray req = new JSONArray(json);
        return req;
    }

    public static BigDecimal balance(String address) throws Exception {
        String url = ONIXCOIN_INFO_API + "/addr/" + address + "/balance";
        String balance = sendGet(url);
        return new BigDecimal(balance);
    }
}
