package com.example.launcherandroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawLaucher {

    /**
     * naj bi ponovno narisal vse ikone aplikacij na namizje
     */
    public static void redraw(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager pm = MainActivity.dis.getPackageManager();
        //get a list of installed apps.
        ArrayList<Document> dovoljeneVJSON = DataService.getDovljeneList();
        Set<String> allowedPackageNames = new HashSet<String>();
        for(Document one : dovoljeneVJSON) {
            allowedPackageNames.add(one.getString("packageName"));
        }
        if(dovoljeneVJSON == null || dovoljeneVJSON.size()==0){
            Log.e("znidi", "seznam aplikacij je prazen");
        }
        Log.v("znidi", "dovoljene list: " + allowedPackageNames);
        LinearLayout ll = MainActivity.dis.findViewById(R.id.vseIkonce);//dobimo layout objekt, na katerega bomo lepili ikonice
        List<ResolveInfo> intentInfos = pm.queryIntentActivities(mainIntent, 0);//tukaj dobimo vse ResolveInfote za vse aplikacije, ki se jih da zagnat

        for(ResolveInfo nfo : intentInfos){//se sprehodimo čez vse resolveInfote, ki smo jih dobili zgoraj
            final String packageName =  nfo.activityInfo.packageName;//packageName

            Log.d("znidi", "packageName: "+packageName);//izpis

            if(allowedPackageNames.contains(packageName)) {
                drawOne(packageName, nfo.activityInfo, ll);
            }

        }
    }

    private static void drawOne(String packageName, ActivityInfo activityInfo, final ViewGroup container){
        final ImageView imageView = new ImageView(MainActivity.dis);//to je kvadratek z ikonico //mora biti final, ker ga potem naprej uporabimo v Runnable
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName name = new ComponentName(packageName, activityInfo.name);
                Intent i = new Intent(Intent.ACTION_MAIN);
                //Intent i = pm.getLaunchIntentForPackage(packageName);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(name);
                MainActivity.dis.startActivity(i);
            }
        });
        try {
            imageView.setImageDrawable(MainActivity.dis.getApplicationContext().getPackageManager().getApplicationIcon(packageName));//one line to rule them all
            //dobimo ikonico in jo nastavimo v imageView
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("znidi","catch "+e.getMessage());
        }

        //ker smo v niti, ki handla odgovor na http request moramo operacije, ki posegajo v grafični vmesnik izvajajti v UIThread niti
        MainActivity.dis.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                container.addView(imageView);//dodamo imageView na seznam aplikacij na zaslonu
            }
        });
    }
}
