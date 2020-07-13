package com.example.im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im.R;
import com.example.im.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailAdapter extends BaseAdapter {

    private Context mContext;
    private boolean mIsCanModify;
    private List<UserInfo> mUsers = new ArrayList<>();
    private boolean mIsDeleteModel; //删除模式 true表示可以删除 false 表示不可以删除
    private OnGroupDetailListener mOnGroupDetailListener;

    public boolean ismIsDeleteModel(){
        return mIsDeleteModel;
    }
    public void setmIsDeleteModel(boolean mIsDeleteModel){
        this.mIsDeleteModel = mIsDeleteModel;
    }

    public GroupDetailAdapter(Context context, boolean isCanModify,OnGroupDetailListener onGroupDetailListener){
        mContext = context;
        mIsCanModify=isCanModify;
        mOnGroupDetailListener = onGroupDetailListener;
    }

    //刷新数据
    public void refresh(List<UserInfo> users){
        if(users != null && users.size() >= 0){
            mUsers.clear();

            //添加加号和减号
            initUsers();

            mUsers.addAll(0,users);
        }
        notifyDataSetChanged();

    }
    private void initUsers() {
        UserInfo add = new UserInfo("add");
        UserInfo delete = new UserInfo("delete");

        mUsers.add(delete);
        mUsers.add(0,add);
    }

    @Override
    public int getCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return mUsers.get(i);
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
            view = View.inflate(mContext, R.layout.item_groupdetail,null);
            holder.photo = (ImageView) view.findViewById(R.id.iv_group_detail_photo);
            holder.delete = (ImageView) view.findViewById(R.id.iv_group_detail_delete);
            holder.name = (TextView) view.findViewById(R.id.tv_group_detail_name);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        UserInfo userInfo = mUsers.get(i);

        if(mIsCanModify){

            if(i == getCount() - 1){//减号的处理
                //删除模式判断
                if(mIsDeleteModel){
                    view.setVisibility(View.INVISIBLE);
                }else{
                    view.setVisibility(View.VISIBLE);
                    holder.photo.setImageResource(R.drawable.em_smiley_minus_btn_pressed);
                    holder.delete.setVisibility(View.GONE);
                    holder.name.setVisibility(View.INVISIBLE);
                }
            }else if(i == getCount() - 2){//加号的处理
                if(mIsDeleteModel){
                    view.setVisibility(View.INVISIBLE);
                }else {
                    view.setVisibility(View.VISIBLE);
                    holder.photo.setImageResource(R.drawable.em_smiley_add_btn_pressed);
                    holder.delete.setVisibility(View.GONE);
                    holder.name.setVisibility(View.INVISIBLE);
                }
            }else {//群成员
                view.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);

                holder.photo.setImageResource(R.drawable.em_default_avatar);
                holder.name.setText(userInfo.getName());

                if(mIsDeleteModel){
                    holder.delete.setVisibility(View.VISIBLE);
                }else{
                    holder.delete.setVisibility(View.GONE);
                }
            }

            //点击事件的处理
            if(i == getCount() - 1){//减号的位置
                holder.photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mIsDeleteModel){
                            mIsDeleteModel = true;
                            notifyDataSetChanged();
                        }
                    }
                });

            }else if(i == getCount() - 2){//加号的位置
                holder.photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnGroupDetailListener.onAddMembers();
                    }
                });
            }else{
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnGroupDetailListener.onDeleteMembers(userInfo);
                    }
                });
            }

        }else {

            if(i == getCount() - 1  || i== getCount() - 2){
                view.setVisibility(View.GONE);
            }else{
                view.setVisibility(View.VISIBLE);
                //名称
                holder.name.setText(userInfo.getName());
                //头像
                holder.photo.setImageResource(R.drawable.em_default_avatar);
                //删除
                holder.delete.setVisibility(View.GONE);
            }

        }

        return view;
    }

    private class ViewHolder{
        private ImageView photo ;
        private ImageView delete;
        private TextView name;
    }

    public interface OnGroupDetailListener{
        //添加群成员
        void onAddMembers();

        //删除群成员方法
        void onDeleteMembers(UserInfo user);
    }

}
