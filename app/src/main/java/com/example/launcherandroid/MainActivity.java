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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.bson.Document;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    private static String serverIp="";
    private static String serverPort="";
    private static OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url="rask.cening.si/android.ip";



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
                    String body = response.body().toString();//dobimo jedro sporočila
                    Document doc = Document.parse(body);//"parsamo" v JSON
                    Document mobilos = doc.get("mobilos", Document.class); //vzamemo ven mobilos JSON
                    serverIp = mobilos.getString("ip");
                    serverPort = mobilos.getString("port");
                    Log.i("znidi", "prejeli smo server ip in port: "+serverIp + ":"+serverPort);
                }
            }
        });

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = this.getPackageManager().queryIntentActivities( mainIntent, 0);

        LinearLayout ll = findViewById(R.id.vseIkonce);
        for(ResolveInfo info:pkgAppsList){
            //RequestQueue

            String packageName = info.resolvePackageName;
            Drawable ikonica = null;
            try {
                ikonica = getApplicationContext().getPackageManager().getApplicationIcon(packageName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e("bp","catch"+e.getMessage());
            }
            Log.v(packageName,"dela");
            ImageView novo = new ImageView(this);

            final ActivityInfo activity = info.activityInfo;
            novo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    i.setComponent(name);
                    startActivity(i);
                }
            });
//test
            novo.setImageDrawable(ikonica);
            ll.addView(novo);
        }
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

}





