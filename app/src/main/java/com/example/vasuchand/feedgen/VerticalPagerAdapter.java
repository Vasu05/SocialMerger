package com.example.vasuchand.feedgen;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasu Chand on 10/20/2016.
 */

class VerticlePagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<search_item_getter_setter> list;
    public VerticlePagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public VerticlePagerAdapter(Context context,String []heading,String[] url,String [] Category,String []time,String[]desc ){

        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        list = new ArrayList<search_item_getter_setter>();
        for(int i =0; i<heading.length; i++)
        {
           search_item_getter_setter item = new search_item_getter_setter();
           item.setHeading(heading[i]);
           item.setIntenturl(url[i]);
           item.setCategory(Category[i]);
           item.setTime(time[i]);
           item.setDesc(desc[i]);
           list.add(item);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = mLayoutInflater.inflate(R.layout.vertical_layout, container, false);

        search_item_getter_setter move = list.get(position);
        TextView desc = (TextView) itemView.findViewById(R.id.desc2);
        TextView heading = (TextView) itemView.findViewById(R.id.desc);
        TextView category = (TextView) itemView.findViewById(R.id.category_desc_type);
        TextView time = (TextView) itemView.findViewById(R.id.time);
        ImageView image = (ImageView)itemView.findViewById(R.id.bg);
        TextView read = (TextView)itemView.findViewById(R.id.readmore);

        heading.setText(move.getHeading());
        desc.setText(move.getDesc());
        category.setText(move.getCategory());
        time.setText(move.getTime());
        if(list.get(position).getIntenturl()!=null) {
            ImageLoader.getInstance().displayImage(list.get(position).getIntenturl(), image);
        }

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  new FinestWebView.Builder(mContext).show(move.getDesc());

            }
        });

       // label.setText(mResources[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}
