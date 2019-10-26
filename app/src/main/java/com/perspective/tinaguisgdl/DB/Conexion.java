package com.perspective.tinaguisgdl.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.util.Log;

import com.perspective.tinaguisgdl.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Conexion {

    private Context context;
    private InputStream is;
    private String result;
    private JSONArray jArray;
    private JSONObject json_data;
    private ArrayList<String> tablas = new ArrayList<String>();

    public Conexion(){

    }

    public Conexion(Context context){
        this.context = context;
    }

    public boolean validarConexion(Context context){
        boolean conexion = false;
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conn.getActiveNetworkInfo() != null && conn.getActiveNetworkInfo().isAvailable() && conn.getActiveNetworkInfo().isConnected())
            conexion = true;

        return conexion;
    }

    public String search(String url) {
        ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
        dat.add(new BasicNameValuePair("id", "0"));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(dat));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            this.is = entity.getContent();
            Log.i("is", is + " x)");
        } catch (Exception e) {
            Log.e("ERROR 1", e.getMessage() + " ");
            return "No se pudo conectar con el servidor";
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            this.is.close();
            result = sb.toString();
        } catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage() + " ");
            return "null";
        }
        catch (IOException e) {
            Log.e("IOException", e.getMessage() + " ");
            return "null";
        }
        catch (Exception e) {
            Log.e("Exception", e.getMessage() + " ");
            return "null";
        }
        return result;
    }

    public boolean insetarRegistros(String url, String tabla) {


        GestionBD gestion = new GestionBD(context,"TianguisGDL",null, MainActivity.VERSION);
        SQLiteDatabase db = gestion.getWritableDatabase();

        db.beginTransaction();

        ContentValues cv = new ContentValues();

        Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);

        ArrayList<NameValuePair> dat = new ArrayList<NameValuePair>();
        dat.add(new BasicNameValuePair("id", "0"));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(dat));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            this.is = entity.getContent();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.is, "iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            this.is.close();
            result = sb.toString();

            jArray = new JSONArray(result);
            this.json_data = this.jArray.getJSONObject(0);
            @SuppressWarnings("unchecked")
            Iterator<String> itr = json_data.keys();
            tablas.clear();
            while (itr.hasNext()) {
                tablas.add(itr.next());
            }

            for (int i = 0; i < tablas.size(); i++) {
                System.out.println("t " + tablas.get(i));
                if (c.getColumnIndex(tablas.get(i)) > -1) {
                    //System.out.println("si existe " );
                }
                else {
                    db.execSQL("ALTER TABLE " + tabla + " ADD COLUMN " + tablas.get(i) + " TEXT ");
                    System.out.println("ALTER TABLE " + tabla + " ADD COLUMN " + tablas.get(i) + " TEXT ");
                }
            }
            c.close();
            for (int i = 0; i < tablas.size(); i++) {
                System.out.println("t " + tablas.get(i));
            }
            for (int i = 0; i < jArray.length(); i++) {
                this.json_data = this.jArray.getJSONObject(i);

                for (int j = 0; j < tablas.size(); j++) {
                    if (!json_data.isNull(tablas.get(j))) {
                        cv.put(tablas.get(j), json_data.getString(tablas.get(j).trim()).trim());
                        System.out.println("registro" + i + " " + tablas.get(j) + " " + json_data.getString(tablas.get(j).trim()).trim() + "hola");
                    }
                    else {
                        cv.put(tablas.get(j), "");
                        System.out.println("registro" + i + " " + tablas.get(j) + " ");
                    }
                }
                db.insert(tabla, null, cv);
            }
            db.setTransactionSuccessful();
        }catch (SQLiteException sqlite) {
            Log.e("SQLiteException", sqlite.getMessage());
            return false;
        }catch (JSONException j) {
            Log.e("JSONException", j.getMessage());
            return false;
        }
        catch (ClientProtocolException e) {
            Log.e("ClientProtocolException", e.getMessage());
            return false;
        }
        catch (IOException e) {
            Log.e("IOException", e.getMessage());
            return false;
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return true;
    }

}
