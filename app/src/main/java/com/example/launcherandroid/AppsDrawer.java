package com.example.launcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class AppsDrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps_drawer);

        RAdapter radapter = new RAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.appsList);

        recyclerView.setAdapter(radapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new myThread().execute();
    }
    public class myThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Params) {
            //RAdapter radapter = new RAdapter(this);
            PackageManager pm = getPackageManager();
            List<AppInfo> appsList = new ArrayList<>();

            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for(ResolveInfo ri:allApps) {
                AppInfo app = new AppInfo();
                app.label = ri.loadLabel(pm);
                app.packageName = ri.activityInfo.packageName;
                app.icon = ri.activityInfo.loadIcon(pm);
                appsList.add(app);
                //radapter.addApp(app);
            }
            return "Success";

        }

        /*@Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateStuff();
        }*/

    }
    /*public void updateStuff() {
        //RAdapter radapter = new RAdapter(this);
        RecyclerView.Adapter<RecyclerView.ViewHolder> radapter = null;
        radapter.notifyItemInserted(radapter.getItemCount()-1);

    }*/


}
