package com.kuycoding.moviecatalogue3.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movies implements Parcelable {
    private int id;
    private String posterPath;
    private String backdrop_path;
    private String title;
    private String releaseDate;
    private double popularity;
    private int voteCount;
    private double voteAverage;
    private String originalLanguage;
    private ArrayList<Integer> genre;
    private ArrayList<GenreModel> listGenreModel;
    private ArrayList<String> listGenreString;
    private String overview;

    public Movies(JSONObject object) {
        try {
            int id = object.getInt("id");
            String posterPath = "https://image.tmdb.org/t/p/w185" + object.getString("poster_path");
            String backdrop_path = "https://image.tmdb.org/t/p/w780" + object.getString("backdrop_path");
            String title = object.getString("title");
            String releaseDate = object.getString("release_date");
            double popularity = object.getDouble("popularity");
            int voteCount = object.getInt("vote_count");
            double voteAverage = object.getDouble("vote_average");
            String originalLanguage = object.getString("original_language");

            final ArrayList<String> listGenreString = new ArrayList<>();
            JSONArray arrayGenreIds = object.getJSONArray("genre_ids");
            for (int i = 0; i < arrayGenreIds.length(); i++) {
                String item = arrayGenreIds.getString(i);
                listGenreString.add(item);
            }

            String overview = object.getString("overview");

            this.id = id;
            this.posterPath = posterPath;
            this.backdrop_path = backdrop_path;
            this.title = title;
            this.releaseDate = releaseDate;
            this.popularity = popularity;
            this.voteCount = voteCount;
            this.voteAverage = voteAverage;
            this.originalLanguage = originalLanguage;
            this.listGenreString = listGenreString;
            this.overview = overview;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GenreModel> getListGenreModel() {
        return listGenreModel;
    }

    public void setListGenreModel(ArrayList<GenreModel> listGenreModel) {
        this.listGenreModel = listGenreModel;
    }

    public ArrayList<String> getListGenreString() {
        return listGenreString;
    }

    public void setListGenreString(ArrayList<String> listGenreString) {
        this.listGenreString = listGenreString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public ArrayList getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<Integer> genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Movies() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.originalLanguage);
        dest.writeList(this.genre);
        dest.writeList(this.listGenreModel);
        dest.writeStringList(this.listGenreString);
        dest.writeString(this.overview);
    }

    private Movies(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.backdrop_path = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.popularity = in.readDouble();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
        this.originalLanguage = in.readString();
        this.genre = new ArrayList<>();
        in.readList(this.genre, Integer.class.getClassLoader());
        this.listGenreModel = new ArrayList<>();
        in.readList(this.listGenreModel, GenreModel.class.getClassLoader());
        this.listGenreString = in.createStringArrayList();
        this.overview = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
