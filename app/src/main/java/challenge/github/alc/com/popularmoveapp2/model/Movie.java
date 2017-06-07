package challenge.github.alc.com.popularmoveapp2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gino Osahon on 07/06/2017.
 */
public class Movie implements Parcelable {

    private String title;
    private  int id;
    private  String overviews;
    private  String thumbnail_url;
    private String date;
    private String genre;
    private Long rating;
    private String backdrop_url;
    private long movie_id;

    public  Movie(String title, int id, String overview, String thumbnail, String date, String genre,Long rating, String backdrop, long movie_id){
        this.title = title;
        this.id = id;
        this.overviews = overview;
        this.thumbnail_url = thumbnail;
        this.date = date;
        this.genre = genre;
        this.rating = rating;
        this.backdrop_url = backdrop;
        this.movie_id = movie_id;

    }

    public Movie(Parcel in){
        title = in.readString();
        id = in.readInt();
        overviews = in.readString();
        thumbnail_url = in.readString();
        date = in.readString();
        genre = in.readString();
        rating = in.readLong();
        backdrop_url = in.readString();
        movie_id = in.readLong();

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(id);
        parcel.writeString(overviews);
        parcel.writeString(thumbnail_url);
        parcel.writeString(date);
        parcel.writeString(genre);
        parcel.writeLong(rating);
        parcel.writeString(backdrop_url);
        parcel.writeLong(movie_id);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverviews() {
        return overviews;
    }

    public void setOverviews(String overviews) {
        this.overviews = overviews;
    }

    public String getThumnail_url() {
        return thumbnail_url;
    }

    public void setThumnail_url(String thumnail_url) {
        this.thumbnail_url = thumnail_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public void setBackdrop_url(String backdrop_url) {
        this.backdrop_url = backdrop_url;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };


}
