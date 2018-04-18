package com.oldandx.oldnx.db;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.oldandx.oldnx.db.converter.DateConverter;

/**
 * Created by Mitash Gaurh on 4/18/2018.
 */

@TypeConverters(DateConverter.class)
public abstract class OldnXDatabase extends RoomDatabase {
}
