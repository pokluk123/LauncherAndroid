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





