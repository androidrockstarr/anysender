package com.rajpriya.anysender;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by rajkumar on 4/25/14.
 */
public class ItemAdapter extends BaseAdapter{
    private Context mContext;


    public ItemAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 6;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
        switch (i) {
            case 0:
                view.findViewById(R.id.image).setBackgroundResource(R.drawable.text);
                break;
            case 1:
                view.findViewById(R.id.image).setBackgroundResource(R.drawable.file);
                break;
            case 2:
                view.findViewById(R.id.image).setBackgroundResource(R.drawable.photo);
                break;
            case 3:
                view.findViewById(R.id.image).setBackgroundResource(R.drawable.audio);
                break;
            case 4:
                view.findViewById(R.id.image).setBackgroundResource(R.drawable.video);
                break;
            case 5:
                view.findViewById(R.id.image).setBackgroundResource(R.drawable.android_app);
                break;
            default:
                break;

        }

        return view;
    }
}
