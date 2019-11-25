package com.kuycoding.moviecatalogue3.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kuycoding.moviecatalogue3.db.MovieHelper;
import com.kuycoding.moviecatalogue3.model.GenreModel;
import com.kuycoding.moviecatalogue3.model.Movies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;


public class MovieViewModel extends ViewModel implements LoadMovieFavCallback {
    private static final String API_KEY = "99e9a8a9f7993100cfe848d86fdbbe26";
    private MutableLiveData<ArrayList<Movies>> listMovies = new MutableLiveData<>();

    public void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
        final ArrayList<GenreModel> listGenre = new ArrayList<>();

        String urlGenre = "http://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY;
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        client.get(urlGenre, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray genres = responseObject.getJSONArray("genres");
                    for (int i = 0; i < genres.length(); i++) {
                        JSONObject genre = genres.getJSONObject(i);
                        GenreModel genreModel = new GenreModel(genre);
                        listGenre.add(genreModel);
                    }

                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", Objects.requireNonNull(error.getMessage()));
            }
        });

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);

                        JSONArray arrayGenreIds = movie.getJSONArray("genre_ids");
                        ArrayList<String> nameGenre = new ArrayList<>();
                        for (int j = 0; j < arrayGenreIds.length(); j++) {
                            int item = arrayGenreIds.getInt(j);

                            for (int n = 0; n < listGenre.size(); n++) {
                                if (listGenre.get(n).getId() == item) {
                                    nameGenre.add(listGenre.get(n).getName());

                                    String namaGenreItem = listGenre.get(n).getName();
                                    arrayGenreIds.put(j, namaGenreItem);
                                }
                            }
                        }

                        Log.d("nameGenre", String.valueOf(nameGenre));
                        Log.d("arrayGenreIds", String.valueOf(arrayGenreIds));

                        Movies movies = new Movies(movie);
                        listItems.add(movies);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }


    public LiveData<ArrayList<Movies>> getMovies() {
        return listMovies;
    }

    public void setMovieFav(Context context) {
        MovieHelper movieHelper = MovieHelper.getInstance(context);
        movieHelper.open();
        new LoadMovieFavAsync(movieHelper, this).execute();
    }

    @Override
    public void preExecute() {
        Log.d("MovieViewModel", "preExecute");
    }

    @Override
    public void postExecute(ArrayList<Movies> listMovies) {
        this.listMovies.postValue(listMovies);
        Log.d("MovieViewModel", "postExecute" + listMovies.toString());
    }


    private static class LoadMovieFavAsync extends AsyncTask<Void, Void, ArrayList<Movies>> {
        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMovieFavCallback> weakCallback;

        private LoadMovieFavAsync(MovieHelper movieHelper, LoadMovieFavCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMoviesFav();
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }
}

interface LoadMovieFavCallback {
    void preExecute();

    void postExecute(ArrayList<Movies> listMovies);
}
