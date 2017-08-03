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
import challenge.github.alc.com.popularmoveapp2.model.Movie;
import challenge.github.alc.com.popularmoveapp2.model.Review;

/**
 * Created by Gino Osahon on 11/06/2017.
 */
public class ReviewAdapter extends BaseAdapter {

    private  Context mContext;
    private  LayoutInflater mInflater;
    public List<Review> mReview;

    public ReviewAdapter(Context context, List<Review> review) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setReviewsData(review);
        //mReview = review;

        //mReview = new Review();
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
        return mReview.size();
    }

    @Override
    public Object getItem(int i) {
        return mReview.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(mReview.get(i).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final Review review = mReview.get(position);

        View view = convertView;
        ReviewAdapterViewHolder viewHolder;

        if (view == null){
            view = mInflater.inflate(R.layout.movie_review, viewGroup, false);
            viewHolder = new ReviewAdapterViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ReviewAdapterViewHolder) view.getTag();

        viewHolder.authorView.setText(review.getAuthor());
        viewHolder.contentView.setText(Html.fromHtml(review.getContent()));

        return view;
    }

    /**
     * Setter method for the Review List Object
     *
     * @param reviewList the List containing Review Objects
     */
    public void setReviewsData(List<Review> reviewList) {
        if (null == mReview) {
            mReview = reviewList;
        } else {
            mReview.addAll(reviewList);
        }
        notifyDataSetChanged();
    }

    /**
     * Getter method for the Review List Object
     *
     * @return the Review List
     */
    public List<Review> getReviewsData() {
        return mReview;
    }
}
