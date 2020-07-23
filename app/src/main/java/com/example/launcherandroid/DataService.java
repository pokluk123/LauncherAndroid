package com.example.launcherandroid;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DataService {

    private static final int checkIntervalInMilis = 60000;
    private static long lastCheck = 0;
    private static ArrayList<Document> dovoljene = new ArrayList<>();

    public static ArrayList<Document> getDovljeneList(){

        if(new Date().getTime() - lastCheck > checkIntervalInMilis || dovoljene.size()==0){//če je preteko že preveč časa moramo osvežiti podatke. Ali pa prejšnjič nismo bili logirani in zato še nimamo aplikacij
            Log.i("znidi", "requestDovoljene");
            requestDovoljene();
        }


        return dovoljene;
    }

    public static void requestDovoljene(){

        Request request = new Request.Builder()
                .url(MainActivity.getServerURL()+"user/"+MainActivity.getUserID()+"/aplikacije/all/")
                .headers(MainActivity.getHeaders())
                .build();

        MainActivity.getHTTP().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("znidi", "error on user/aplikacije/all " + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("znidi", "error on user/aplikacije/all  weird code " + response.code());
                    Log.e("znidi", "response.body().toString(): " + response.body().string());
                } else {
                    String body = response.body().string();//dobimo jedro sporočila
                    Log.v("znidi", "response.body().toString(): "+body);
                    org.bson.Document doc = org.bson.Document.parse(body);//"parsamo" v JSON
                    dovoljene = doc.get("allowed", ArrayList.class);
                    DrawLaucher.redraw();
                }
            }
        });



        lastCheck = new Date().getTime();//trenutni datum/čas
    }

}
