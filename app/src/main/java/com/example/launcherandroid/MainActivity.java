package com.example.launcherandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = this.getPackageManager().queryIntentActivities( mainIntent, 0);

        LinearLayout ll = findViewById(R.id.vseIkonce);
        for(ResolveInfo info:pkgAppsList){
            Drawable ikonica = info.loadIcon(this.getPackageManager());
            String packageName = info.resolvePackageName;
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

            novo.setImageDrawable(ikonica);
            ll.addView(novo);
        }




        /*final PackageManager pm = getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Log.e("ales", "stevilo packagov: "+ packages.size());
        for (ApplicationInfo packageInfo : packages) {
            Log.e("ales", "Installed package :" + packageInfo.packageName);
            Log.e("ales", "Source dir : " + packageInfo.sourceDir);
            Log.e("ales", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
        }*/
        // the getLaunchIntentForPackage returns an intent that you can use with startActivity()

/*
        ImageView chromeIcon = findViewById(R.id.chromeImageView);
        chromeIcon.setOnClickListener(onChromeButtonClick);
        chromeIcon.setImageDrawable(getActivityIcon(this,"com.android.chrome", "com.google.android.apps.chrome.Main"));

        ImageView contacts = (ImageView) findViewById(R.id.contactsImageView);
        contacts.setOnClickListener(onChromeButtonClick);
        contacts.setImageDrawable(getActivityIcon(this,"com.android.contacts", "com.android.contacts.apps.contacts.Main"));
        */

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
        /*PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        return resolveInfo.loadIcon(pm);*/
        try{
            Drawable icon = context.getPackageManager().getApplicationIcon(packageName);
            return icon;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        ImageView chrome = (ImageView) findViewById(R.id.chromeImageView);
        chrome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chrome.setImageDrawable(getActivityIcon(this,"com.android.chrome", "com.google.android.apps.chrome.Main"));
            }
        }
    }
        private View.OnClickListener onChromeButtonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(launchIntent);
            }
        };
        /*
        ImageView photo = (ImageView) findViewById(R.id.photoImageView);
        ImageView dialer = (ImageView) findViewById(R.id.dialerImageView);
        ImageView contacts = (ImageView) findViewById(R.id.contactsImageView);
        photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.photos");
                startActivity(launchIntent);
            }
        });

        dialer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.dialer");
                startActivity(launchIntent);
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.contacts");
                startActivity(launchIntent);
            }
        });



       /* ScrollView vsiKnofi = findViewById(R.id.neki);

       for(ResolveInfo res: pkgAppsList){
            Log.e("ales", res.toString());
            ImageView enKnof = new ImageView(getApplicationContext());

           vsiKnofi.addView(enKnof);
       }

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

        public static Drawable getActivityIcon(View.OnClickListener context, String packageName, String activityName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityName));
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);

        return resolveInfo.loadIcon(pm);
}


*/



