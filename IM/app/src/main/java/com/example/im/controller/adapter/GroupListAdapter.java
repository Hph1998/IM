package com.example.im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.im.R;
import com.hyphenate.chat.EMGroup;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends BaseAdapter {

    private Context mContext;
    private List<EMGroup> mGroups = new ArrayList<>();

    public GroupListAdapter(Context context){
        mContext=context;
    }


    public void refresh(List<EMGroup> groups){
        if(groups != null && groups.size() >=0){
            mGroups.clear();

            mGroups.addAll(groups);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mGroups==null?0:mGroups.size();
    }

    @Override
    public Object getItem(int i) {
        return mGroups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();

            view = View.inflate(mContext, R.layout.item_grouplist,null);

            holder.name = (TextView) view.findViewById(R.id.tv_grouplist_name);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        EMGroup emGroup = mGroups.get(i);

        holder.name.setText(emGroup.getGroupName());

        return view;
    }

    private class ViewHolder{
        TextView name;
    }

}
