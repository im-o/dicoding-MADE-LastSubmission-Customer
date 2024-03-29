package com.stimednp.boxofficefavoritecuk.mymapcursor;

import android.database.Cursor;

import com.stimednp.boxofficefavoritecuk.mydbmodel.MoviesModel;

import java.util.ArrayList;

import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_BACK_PATH;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_OVERVIEW;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_POSTER_PATH;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_RELEASE_DATE;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_TITLE;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_VOTE_AVERAGE;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.COLUMN_VOTE_COUNT;
import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.ID;

/**
 * Created by rivaldy on 8/21/2019.
 */

public class MappingHelper {
    /**
     * Karena nanti di adapter kita akan menggunakan arraylist, sedangkan di sini obyek yang di
     * kembalikan berupa Cursor maka dari itu kita harus mengonversi dari Cursor ke Arraylist
     */
    public static ArrayList<MoviesModel> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<MoviesModel> modelArrayList = new ArrayList<>();
        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE));
            double vote_average = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(COLUMN_VOTE_AVERAGE));
            String vote_count = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_VOTE_COUNT));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_OVERVIEW));
            String poster_path = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH));
            String backdrop_path = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_BACK_PATH));
            modelArrayList.add(new MoviesModel(id, title, release_date, vote_average, vote_count, overview, poster_path, backdrop_path));
        }
        return modelArrayList;
    }
}
