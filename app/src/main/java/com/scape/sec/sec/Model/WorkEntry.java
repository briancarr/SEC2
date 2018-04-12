package com.scape.sec.sec.Model;

import android.provider.BaseColumns;

/**
 * Created by Me on 24/10/2016.
 */

public class WorkEntry implements BaseColumns {
    public static final String TABLE_NAME = "works";
    public static final String COLUMN_LOCATION_NAME = "location_name";
    public static final String COLUMN_WORK_NAME = "work_name";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_AUDIO_URL = "audio_url";
    public static final String COLUMN_COORD_LAT = "coord_lat";
    public static final String COLUMN_COORD_LONG = "coord_long";
}
