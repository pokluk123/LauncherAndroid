package com.example.launcherandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        ImageView chrome = findViewById(R.id.chromeImageView);
        ImageView photo = findViewById(R.id.);

        chrome.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                                          startActivity(launchIntent);
                                      }
                                  });
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
            }
        });

        /*List<ResolveInfo> pkgAppsList = this.getPackageManager().queryIntentActivities( mainIntent, 0);*/

       /* ScrollView vsiKnofi = findViewById(R.id.neki);

       for(ResolveInfo res: pkgAppsList){
            Log.e("ales", res.toString());
            ImageView enKnof = new ImageView(getApplicationContext());

           vsiKnofi.addView(enKnof);
       }*/

        /*ImageView chromeIcon = (ImageView) findViewById(R.id.chromeButton);
        chromeIcon.setImageDrawable(this, getActivityIcon(,"com.android.chrome", "com.google.android.apps.chrome.Main"));
        */


        /*ImageView chrome = findViewById(R.id.chromeButton);
        chrome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
            }
        });

        ImageView phone = findViewById(R.id.phoneButton);
        phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.phone");
                startActivity(launchIntent);
            }
        });
        //calculator ni naložen na samem sistemu
        ImageView calculator = findViewById(R.id.calculatorButton);
        calculator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.calculator");
                startActivity(launchIntent);
            }
        });

        ImageView gmail = findViewById(R.id.gmailButton);
        gmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                startActivity(launchIntent);
            }
        });
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = new Intent(MainActivity.this,AppsDrawer.class);
                startActivity(launchIntent);
            }
        });
*/

    }




    public static Drawable getActivityIcon(Context context, String packageName, String activityName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);

        return resolveInfo.loadIcon(pm);
    }

}
