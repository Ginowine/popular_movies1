package challenge.github.alc.com.popularmoveapp2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import challenge.github.alc.com.popularmoveapp2.DetailsActivity;
import challenge.github.alc.com.popularmoveapp2.R;
import challenge.github.alc.com.popularmoveapp2.data.MovieContract;
import challenge.github.alc.com.popularmoveapp2.model.Movie;

/**
 * Created by Gino Osahon on 29/06/2017.
 */
public class FavouritesMoviesAdapter extends RecyclerView.Adapter<FavouritesMoviesAdapter.FavouriteMovieViewHolder>  {

    private Cursor cursor;
    private LayoutInflater inflater;
    private Context context;
   // private ItemClickListener clickListener;

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";


    public FavouritesMoviesAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public FavouriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteMovieViewHolder(inflater.inflate(R.layout.favorites_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final FavouriteMovieViewHolder holder, int position) {

        ImageView moviePoster = holder.favoritesMoviePoster;
        TextView movieTitle = holder.favoritesMovieTitle;
        //TextView movieRating = holder.favoritesMovieRating;

        final Movie movie = getFavouriteMoviesDataFromCursor(cursor, position);
        holder.itemView.setTag(movie.getId());
        movieTitle.setText(movie.getOriginalTitle());
        //movieRating.setText(String.valueOf(movie.getRating()));

        String image_url = IMAGE_URL_BASE_PATH + movie.getPosterPath();
        // load the movie poster into the imageView
        Picasso.with(context)
                .load(image_url) //get the low resolution image
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(moviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Bundle bundle = new Bundle();

                bundle.putInt(Movie.MOVIE_ID, movie.getId());
                bundle.putString(Movie.MOVIE_TITLE, movie.getOriginalTitle());
                bundle.putString(Movie.MOVIE_OVERVIEW, movie.getOverview());
                bundle.putString(Movie.POSTER_URL, movie.getPosterPath());
                bundle.putString(Movie.MOVIE_RELEASE_DATE, movie.getReleaseDate());
                bundle.putDouble(Movie.MOVIE_RATING, movie.getRating());
                bundle.putBoolean(Movie.MOVIE_FAVOURITE, movie.isFavourite());

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Movie.MOVIE_FAVOURITE, bundle);
                //intent.putExtra("movie", movies);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null){
            return  0;
        }
        return cursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (cursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = cursor;
        this.cursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public Movie getFavouriteMoviesDataFromCursor(Cursor cursor, int position){
        Movie favouriteMovie = new Movie();
        cursor.moveToPosition(position);

        //set the movie's title
        favouriteMovie.setOriginalTitle(cursor
                .getString(cursor.getColumnIndex(MovieContract.Favorites.FAVORITE_COLUMN_TITLE)));

        //set the movie's poster path
        favouriteMovie.setPosterPath(cursor
                .getString(cursor.getColumnIndex(MovieContract.Favorites.FAVORITE_COLUMN_IMAGE)));

        //set the movie's release date
        favouriteMovie.setReleaseDate(cursor
                .getString(cursor.getColumnIndex(MovieContract.Favorites.FAVORITE_COLUMN_DATE)));

        //set the movie's overview
        favouriteMovie.setOverview(cursor
                .getString(cursor.getColumnIndex(MovieContract.Favorites.FAVORITE_COLUMN_OVERVIEW)));

        //set the movie's rating
        favouriteMovie.setRating(cursor
                .getDouble(cursor.getColumnIndex(MovieContract.Favorites.FAVORITE_COLUMN_RATING)));

        //set the id
        favouriteMovie.setId(cursor
                .getInt(cursor.getColumnIndex(MovieContract.Favorites._ID)));

        return favouriteMovie;
    }

    class FavouriteMovieViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout full_layout;
        protected LinearLayout movieHolder;
        protected LinearLayout movieTitleHolder;

        private ImageView favoritesMoviePoster;
        private TextView favoritesMovieTitle;
        //private TextView favoritesMovieRating;
        //private View favouritesContainer;
        FavouriteMovieViewHolder(View itemView) {
            super(itemView);
            this.full_layout = (LinearLayout) itemView.findViewById(R.id.favorite_movie_layout);
            this.movieHolder = (LinearLayout) itemView.findViewById(R.id.favorite_movieHolder);
            this.movieTitleHolder = (LinearLayout) itemView.findViewById(R.id.favorite_movieTitleHolder);
            favoritesMoviePoster = (ImageView) itemView.findViewById(R.id.favourites_movie_poster);
            //favoritesMovieRating = (TextView) itemView.findViewById(R.id.favourites_movie_ratings);
            favoritesMovieTitle = (TextView) itemView.findViewById(R.id.favorites_movie_title);
            //favouritesContainer = itemView.findViewById(R.id.favourites_container);
            //itemView.setOnClickListener(this);

        }
    }
}
