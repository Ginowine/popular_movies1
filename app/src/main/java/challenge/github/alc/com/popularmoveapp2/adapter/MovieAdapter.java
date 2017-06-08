package challenge.github.alc.com.popularmoveapp2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import challenge.github.alc.com.popularmoveapp2.DetailsActivity;
import challenge.github.alc.com.popularmoveapp2.R;
import challenge.github.alc.com.popularmoveapp2.model.Movie;

/**
 * Created by Gino Osahon on 07/06/2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    public List<Movie> movieList;
    private int rowLayout;
    private Context context;

    public MovieAdapter(List<Movie> movies, int rowLayout, Context context){
        this.movieList = movies;
        this.rowLayout = rowLayout;
        this.context = context;

    }

    public MovieAdapter(){

    }


    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView backdropImage;

        public MovieAdapterViewHolder(View view){
            super(view);

            backdropImage = (ImageView) view.findViewById(R.id.backdrop_image);

        }

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder holder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + movieList.get(position).getPosterPath();
        //final Movie movies = movieList.get(position);

        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.backdropImage);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                //intent.putExtra("movie", movies);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
