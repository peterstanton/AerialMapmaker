package com.c_sharpphoto.aerialmapmaker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final int PICKFILE_RESULT_CODE = 1;
    public static final int EXTRA_FLIGHTPLAN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showFilesClick(View view) {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("gagt/sdf");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    public void planMission(View view) {
        Intent intent = new Intent (this, MissionPlanner.class);
        startActivity(intent);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    //FilePath is your file as a string
                }
        }
        Intent flightPlan = getIntent();
        Intent missionPlan = new Intent(this, MissionPlanner.class);
        missionPlan.putExtra(EXTRA_FLIGHTPLAN, flightPlan);
        startActivity(missionPlan);
    }
}

///test test test
