package com.kuycoding.moviecatalogue3.db;

import android.provider.BaseColumns;

class DatabaseContract {
    static String TABLE_FAV_MOVIE = "movie_fav";
    static String TABLE_FAV_TV_SHOW = "tv_show_fav";

    static final class MovieFavColumns implements BaseColumns {
        static String ID_MOVIE = "id_movie";
        static String POSTER_PATH = "poster_path";
        static String BACKDROP_PATH = "backdrop_path";
        static String TITLE = "title";
        static String RELEASE_DATE = "release_data";
        static String POPULARITY = "popularity";
        static String VOTE_COUNT = "vote_count";
        static String VOTE_AVERAGE = "vote_average";
        static String ORIGINAL_LANGUAGE = "original_language";
        static String GENRE = "genre";
        static String OVERVIEW = "overview";
    }

    static final class TvShowFavColumns implements BaseColumns{
        static String ID_TV_SHOW = "id_tv_show";
        static String POSTER_PATH = "poster_path";
        static String BACKDROP_PATH = "backdrop_path";
        static String NAME = "name";
        static String FIRST_AIR_DATE = "first_air_date";
        static String POPULARITY = "popularity";
        static String ORIGIN_COUNTRY = "origin_country";
        static String VOTE_COUNT = "vote_count";
        static String VOTE_AVERAGE = "vote_average";
        static String ORIGINAL_LANGUAGE = "original_language";
        static String GENRE = "genre";
        static String OVERVIEW = "overview";
    }
}
