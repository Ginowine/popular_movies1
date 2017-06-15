package challenge.github.alc.com.popularmoveapp2.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import challenge.github.alc.com.popularmoveapp2.R;
import challenge.github.alc.com.popularmoveapp2.model.Review;

/**
 * Created by Gino Osahon on 11/06/2017.
 */
public class ReviewAdapter extends BaseAdapter {

    private  Context mContext;
    private  LayoutInflater mInflater;
    private  Review mReview;

    public ReviewAdapter(Context context, Review object) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mReview = object;

        mReview = new Review();
    }

    public ReviewAdapter(){

    }

    public static class ReviewAdapterViewHolder{
        public  TextView authorView;
        public  TextView contentView;

        public ReviewAdapterViewHolder(View view){
            authorView = (TextView) view.findViewById(R.id.review_author);
            contentView = (TextView) view.findViewById(R.id.review_content);

        }

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        ReviewAdapterViewHolder viewHolder;

        if (view == null){
            view = mInflater.inflate(R.layout.movie_review, viewGroup, false);
            viewHolder = new ReviewAdapterViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ReviewAdapterViewHolder) view.getTag();

        viewHolder.authorView.setText(mReview.getAuthor());
        viewHolder.contentView.setText(Html.fromHtml(mReview.getContent()));

        return view;
    }
}
