package com.example.vasuchand.feedgen;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasu Chand on 10/22/2016.
 */

public class pageradapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<search_item_getter_setter> list;
    public pageradapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public pageradapter(Context context,String []heading,String[] url,String[]desc ){

        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        list = new ArrayList<search_item_getter_setter>();
        for(int i =0; i<heading.length; i++)
        {
            search_item_getter_setter item = new search_item_getter_setter();
            item.setHeading(heading[i]);
            item.setIntenturl(url[i]);
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = mLayoutInflater.inflate(R.layout.items_layout, container, false);

        final search_item_getter_setter move = list.get(position);

        TextView heading = (TextView) itemView.findViewById(R.id.desc);
        ImageView image = (ImageView)itemView.findViewById(R.id.bg);
        Button b1  = (Button)itemView.findViewById(R.id.b6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Uri uri = Uri.parse(move.getDesc());
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
//                browserIntent.setDataAndType(uri, "text/html");
//                browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
//                mContext.startActivity(browserIntent);
                new FinestWebView.Builder(mContext).show(move.getDesc());

            }
        });

        heading.setText(move.getHeading());

        if(list.get(position).getIntenturl()!=null) {
            ImageLoader.getInstance().displayImage(list.get(position).getIntenturl(), image);
        }

        // label.setText(mResources[position]);
        container.addView(itemView);

        return itemView;
    }
}
