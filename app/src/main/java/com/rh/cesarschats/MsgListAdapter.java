package com.rh.cesarschats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MsgListAdapter extends ArrayAdapter<Msg> {
    private static final String TAG = "MsgListAdapter";

    private final Context mContext;
    private final int mResource;
    private int lastPosition = -1;


    private static class ViewHolder {
        TextView user_name;
        TextView msg;

    }
    /**
     * Default constructor for the OrderListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MsgListAdapter(Context context, int resource, ArrayList<Msg> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the order information
        String user_name = getItem(position).getUser_name();
        String msg = getItem(position).getMsg();


        //Create the order object with the information
        Msg msgs = new Msg(user_name,msg);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.user_name = (TextView) convertView.findViewById(R.id.textView1);
            holder.msg = (TextView) convertView.findViewById(R.id.textView2);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.user_name.setText(msgs.getUser_name());
        holder.msg.setText(msgs.getMsg());


        return convertView;
    }
}



