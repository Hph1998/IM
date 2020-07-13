package com.example.im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.im.R;
import com.example.im.model.bean.PickContactInfo;

import java.util.ArrayList;
import java.util.List;

public class PickContactAdapter extends BaseAdapter {

    private Context mContext;
    private List<PickContactInfo> mPicks = new ArrayList<>();
    private List<String> mExistMembers = new ArrayList<>();

    public PickContactAdapter(Context context, List<PickContactInfo> picks,List<String> existMembers){
        mContext = context;
        if(picks != null && picks.size()>=0){
            mPicks.clear();
            mPicks.addAll(picks);
        }
        mExistMembers.clear();
        mExistMembers.addAll(existMembers);
    }
    @Override
    public int getCount() {
        return mPicks == null ? 0 : mPicks.size();
    }

    @Override
    public Object getItem(int i) {
        return mPicks.get(i);
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
            view = View.inflate(mContext, R.layout.item_pick,null);
            holder.cb = view.findViewById(R.id.cb_pick);
            holder.tv_name = view.findViewById(R.id.tv_pick_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        PickContactInfo pickContactInfo = mPicks.get(i);
        holder.tv_name.setText(pickContactInfo.getUser().getName());
        holder.cb.setChecked(pickContactInfo.isChecked());


        if(mExistMembers.contains(pickContactInfo.getUser().getHxid())){
            holder.cb.setChecked(true);
            pickContactInfo.setChecked(true);
        }
        return view;
    }

    public List<String> getPickContacts(){
        List<String> picks = new ArrayList<>();

        //判断是否选中
        for(PickContactInfo pick : mPicks){
            if(pick.isChecked()){
                picks.add(pick.getUser().getName());
            }
        }

        return picks;
    }

    private class ViewHolder{
        private CheckBox cb;
        private TextView tv_name;
    }

}
