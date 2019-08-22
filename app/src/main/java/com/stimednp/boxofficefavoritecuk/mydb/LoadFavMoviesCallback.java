package com.stimednp.boxofficefavoritecuk.mydb;

import android.database.Cursor;

/**
 * Created by rivaldy on 8/22/2019.
 */

public interface LoadFavMoviesCallback {
    void preExecute();

    void postExecute(Cursor cursor);
}
