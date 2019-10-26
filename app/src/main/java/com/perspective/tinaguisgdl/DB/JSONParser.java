package com.perspective.tinaguisgdl.DB;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class JSONParser {

    static String result = "";
    static InputStream is = null;
    static JSONObject jObject = null;

    public JSONParser() {

    }

    public JSONObject realizarHttpRequest(String url,String metodo, ArrayList<NameValuePair> parametro) {

        try {
            if (metodo == "POST") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(parametro));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
            else if(metodo == "GET") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String parametros= URLEncodedUtils.format(parametro, "utf-8");
                url += "?" + parametros;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncodingException", e.getMessage());
        }catch (ClientProtocolException e) {
            Log.e("IOException", e.getMessage());
        }catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage()+" ");
        }
        Log.i("result", result);
        try {
            jObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("JSONException 1", e.getMessage());
        }

        return jObject;
    }

}
