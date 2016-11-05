package com.example.vasuchand.feedgen.youtubefetch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.vasuchand.feedgen.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Vasu Chand on 11/2/2016.
 */

public class youadapter  extends RecyclerView.Adapter<youadapter.MyViewHolder> {


    private List<VideoData> moviesList;
    private Context mContext;
    private com.android.volley.toolbox.ImageLoader mImageLoader;



    public class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView t1,t2,t22,t4;
        public NetworkImageView n1;

        protected RecyclerView recycler_view_list;
        private AdapterView.OnItemClickListener listener;
        public CardView cardView;


        public MyViewHolder(View view) {
            super(view);

            mContext = view.getContext();
            t1 =(TextView)view.findViewById(R.id.t1);
            t2 = (TextView)view.findViewById(R.id.youtubeid);
            t22 = (TextView)view.findViewById(R.id.t2);
            t4 = (TextView)view.findViewById(R.id.t4);
            n1 = (NetworkImageView)view.findViewById(R.id.n1);
            mContext = view.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, PlayActivity.class);
                    intent.putExtra("youtubeId", t2.getText());
                    mContext.startActivity(intent);
                }
            });
            // this.recycler_view_list = (RecyclerView)view.findViewById(R.id.recycler_view);



        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_layout, parent, false);


            return new MyViewHolder(itemView);
    }


    public youadapter(Context context,List<VideoData> moviesList)
    {
        this.moviesList = moviesList;
        this.mContext = context;
        //this.mImageLoader =i;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VideoData movie = moviesList.get(position);
        holder.t1.setText(movie.getTitle());
        holder.t2.setText(movie.getYouTubeId());
        holder.t22.setText(movie.getViewCount());
        holder.t4.setText(movie.getLikes());
        mImageLoader = CustomVolleyRequest.getInstance(mContext)
                        .getImageLoader();
      //  holder.n1.setImageUrl(movie.getThumbUri(),);
        //ImageLoader.getInstance().displayImage(movie.getThumbUri(), holder.n1);
        //(NetworkImageView) convertView.findViewById(R.id.thumbnail)).setImageUrl(video.getThumbUri(), mImageLoader);
         holder.n1.setImageUrl(movie.getThumbUri(),mImageLoader);




    }
    @Override
    public long getItemId(int i) {
        return moviesList.get(i).getYouTubeId().hashCode();
    }



    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
