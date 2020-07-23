package com.example.launcherandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.bson.Document;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static final MediaType JSONMime = MediaType.parse("application/json; charset=utf-8");
    private static Headers headers;
    private static String serverIp="";
    private static String serverPort="";
    private static String username="andrej@demo1";
    private static String userID;
    private static String token;
    private static List<Document> firmIDs;
    private static List<Document> groupIDs;
    private static OkHttpClient client = new OkHttpClient();

    public static AppCompatActivity dis;//ugly hackzz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dis=this;//ugly hackzz part2
        Request request = new Request.Builder()
                .url("https://www.rask.cening.si/android.ip")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("znidi", "error on receive IP " + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("znidi", "error on receive IP weird code " + response.code());
                } else {

                    String body = response.body().string();//dobimo jedro sporočila
                    Log.v("znidi", "response.body().toString(): "+body);
                    Document doc = Document.parse(body);//"parsamo" v JSON
                    Document mobilos = doc.get("mobilos", Document.class); //vzamemo ven mobilos JSON
                    serverIp = mobilos.getString("serverip");
                    serverPort = mobilos.getString("port");
                    Log.i("znidi", "prejeli smo server ip in port: "+serverIp + ":"+serverPort);
                    //DrawLaucher.redraw();//raje ne, ker tako ali tako še nismo prijavljeni
                    login();
                }
            }
        });



    }
    private View.OnClickListener onChromeButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
            startActivity(launchIntent);
        }
    };
    private View.OnClickListener onContactButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.contacts");
            startActivity(launchIntent);
        }
    };
    public static Drawable getActivityIcon(Context context, String packageName, String activityName) {

        try{
            Drawable icon = context.getPackageManager().getApplicationIcon(packageName);
            return icon;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OkHttpClient getHTTP(){
        return client;
    }

    public static String getServerURL(){
        return "http://"+serverIp+":"+serverPort+"/rest/";
    }

    public static String getUserID(){
        return userID;
    }

    public static Headers getHeaders(){
        if(headers==null){
            login();
            return Headers.of();
        }
        return headers;
    }

    private static void login(){

        Document doc = new Document("username", username);
        doc.append("password", "123admin");

        Request request = new Request.Builder()
                .url(getServerURL()+"user/auth/mobilos")
                .post(RequestBody.create(JSONMime, doc.toJson()))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("znidi", "error on receive login token " + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("znidi", "error on receive login token weird code " + response.code());
                    if(response.code()==401){
                        Log.e("znidi", "Handle wrong password error");
                    }
                } else {
                    String body = response.body().string();//dobimo jedro sporočila
                    Document doc = Document.parse(body);//"parsamo" v JSON
                    token = doc.getString("token");
                    userID = doc.getObjectId("_id").toString();
                    firmIDs = doc.get("firmIDs", ArrayList.class);
                    groupIDs = doc.get("groupIDs", ArrayList.class);
                    Log.i("znidi", "prejeli smo token: "+token+" in id: "+userID);
                    Map<String, String> headerMap = new HashMap<String, String>();
                    headerMap.put("username", username);
                    headerMap.put("token", token);
                    headers = Headers.of(headerMap);//shranimo si headerje, ker so v seh requestih enaki

                    DataService.requestDovoljene();
                }
            }
        });
    }
}





