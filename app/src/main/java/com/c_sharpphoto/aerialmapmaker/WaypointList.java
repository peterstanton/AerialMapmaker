package com.c_sharpphoto.aerialmapmaker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WaypointList extends Service {
    public WaypointList() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
