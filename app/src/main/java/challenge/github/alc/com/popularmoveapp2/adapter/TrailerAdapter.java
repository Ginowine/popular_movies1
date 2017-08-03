package challenge.github.alc.com.popularmoveapp2.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import challenge.github.alc.com.popularmoveapp2.R;
import challenge.github.alc.com.popularmoveapp2.model.Videos;

/**
 * Created by Gino Osahon on 11/06/2017.
 */
public class TrailerAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    //private Trailer mTrailer = new Trailer();
    List<Videos> mVideo;

    public TrailerAdapter(Context context, List<Videos> video){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setVideosData(video);
        //mVideo = video;
    }

    public TrailerAdapter(){

    }

    @Override
    public int getCount() {
        return mVideo.size();
    }

    @Override
    public Object getItem(int i) {
        return mVideo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class TrailerAdapterViewHolder{
        public ImageView imageView;
        public TextView nameView;
        public TextView siteView;
        public TextView typeView;

        public TrailerAdapterViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.trailer_image);
            nameView = (TextView) view.findViewById(R.id.trailer_name);
        }
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        final Videos videos = mVideo.get(i);

        View view = convertView;
        TrailerAdapterViewHolder viewHolder;

        if (view == null){
            view = mInflater.inflate(R.layout.movie_trailer, viewGroup, false);
            viewHolder = new TrailerAdapterViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (TrailerAdapterViewHolder) view.getTag();
        viewHolder.nameView.setText(videos.getName());

        String yt_thumbnail_url = "http://img.youtube.com/vi/" + videos.getKey() + "/0.jpg";

        Picasso.with(mContext)
                .load(yt_thumbnail_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Uri imageVideoLink = Uri.parse("http://www.youtube.com/watch?v=" + videos.getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW, imageVideoLink);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Setter method for the Video List Object
     *
     * @param videoList the List containing Video Objects
     */
    public void setVideosData(List<Videos> videoList) {
        if (null == mVideo) {
            mVideo = videoList;
        } else {
            mVideo.addAll(videoList);
        }
        notifyDataSetChanged();
    }

    /**
     * Getter method for the Video List Object
     *
     * @return the Video List
     */
    public List<Videos> getVideosData() {
        return mVideo;
    }
}
