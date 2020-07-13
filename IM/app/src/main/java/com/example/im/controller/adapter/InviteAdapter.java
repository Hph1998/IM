package com.example.im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.im.R;
import com.example.im.model.bean.InvationInfo;
import com.example.im.model.bean.UserInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InviteAdapter extends BaseAdapter {

    private Context mContext;
    private List<InvationInfo> mInvitationIndos = new ArrayList<>();
    private OnInviteListener mOnInviteListener;
    private InvationInfo invationInfo;

    public InviteAdapter(Context context, OnInviteListener onInviteListener){
        mContext=context;
        mOnInviteListener=onInviteListener;
    }

    public void refresh(List<InvationInfo> invationInfos){
        if(invationInfos != null && invationInfos.size() >= 0){
            mInvitationIndos.clear();
            mInvitationIndos.addAll(invationInfos);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvitationIndos == null?0:mInvitationIndos.size();
    }

    @Override
    public Object getItem(int i) {
        return mInvitationIndos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHodler hodler = null;
        if(view == null){
            hodler = new ViewHodler();
            view = View.inflate(mContext, R.layout.item_invite, null);
            hodler.name = view.findViewById(R.id.tv_invite_name);
            hodler.reason = view.findViewById(R.id.tv_invite_reason);
            hodler.accept = view.findViewById(R.id.bt_invite_accept);
            hodler.reject = view.findViewById(R.id.bt_invite_reject);
            view.setTag(hodler);
        }else {
            hodler = (ViewHodler)view.getTag();
        }

        invationInfo = mInvitationIndos.get(i);

        UserInfo user = invationInfo.getUser();
        if(user!=null){

            hodler.name.setText(invationInfo.getUser().getName());

            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);

            if(invationInfo.getStatus()==InvationInfo.InvitationStatus.NEW_INVITE){

                if(invationInfo.getReason() == null){
                    hodler.reason.setText("添加好友");
                }else{
                    hodler.reason.setText(invationInfo.getReason());
                }

                hodler.accept.setVisibility(View.VISIBLE);
                hodler.reject.setVisibility(View.VISIBLE);

            }else if(invationInfo.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT){

                if(invationInfo.getReason() == null){
                    hodler.reason.setText("接受邀请");
                }else{
                    hodler.reason.setText(invationInfo.getReason());
                }

            }else if(invationInfo.getStatus() == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER){

                if(invationInfo.getReason() == null){
                    hodler.reason.setText("邀请被接受");
                }else{
                    hodler.reason.setText(invationInfo.getReason());
                }

            }

            hodler.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnInviteListener.onAccept(invationInfo);
                }
            });
            hodler.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnInviteListener.onReject(invationInfo);
                }
            });

        }else {

            hodler.name.setText(invationInfo.getGroup().getInvatePerson());
            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);

            //显示原因
            switch (invationInfo.getStatus()) {
                //你的群申请已经被接受
                case GROUP_APPLICATION_ACCEPTED:
                    hodler.reason.setText("你的群申请已经被接受");
                    break;
                //你的群邀请已经被接受
                case GROUP_INVITE_ACCEPTED:
                    hodler.reason.setText("你的群邀请已经被接受");
                    break;
                //你的群申请已经被拒绝
                case GROUP_APPLICATION_DECLINED:
                    hodler.reason.setText("你的群申请已经被拒绝");
                    break;
                //你的群邀请已经被拒绝
                case GROUP_INVITE_DECLINED:
                    hodler.reason.setText("你的群邀请已经被拒绝");
                    break;
                //你收到了群邀请
                case NEW_GROUP_INVITE:
                    hodler.accept.setVisibility(View.VISIBLE);
                    hodler.reject.setVisibility(View.VISIBLE);
                    //接受邀请
                    hodler.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onInviteAccept(invationInfo);
                        }
                    });
                    //拒绝邀请
                    hodler.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onInviteReject(invationInfo);
                        }
                    });
                    hodler.reason.setText("你收到了群邀请");
                    break;
                //你收到了群申请
                case NEW_GROUP_APPLICATION:
                    hodler.accept.setVisibility(View.VISIBLE);
                    hodler.reject.setVisibility(View.VISIBLE);
                    //接受申请
                    hodler.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationAccept(invationInfo);
                        }
                    });
                    //拒绝申请
                    hodler.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnInviteListener.onApplicationReject(invationInfo);
                        }
                    });
                    hodler.reason.setText("你收到了群申请");
                    break;
                //你接受了群邀请
                case GROUP_ACCEPT_INVITE:
                    hodler.reason.setText("你接受了群邀请");
                    break;
                //你批准了群申请
                case GROUP_ACCEPT_APPLICATION:
                    hodler.reason.setText("你批准了群申请");
                    break;
//                //你拒绝了群邀请
//                case GROUP_REJECT_INVITE:
//                    holder.reason.setText("你拒绝了群邀请");
//                    break;
//                //你拒绝了群申请
//                case GROUP_REJECT_APPLICATION:
//                    holder.reason.setText("你拒绝了群申请");
//                    break;
            }

        }

        return view;
    }

    private class ViewHodler{
        private TextView name;
        private TextView reason;
        private Button accept;
        private Button reject;
    }

    public interface OnInviteListener{

        void onAccept(InvationInfo invatationInfo);

        void onReject(InvationInfo invatationInfo);

        void onApplicationReject(InvationInfo invatationInfo);

        void onApplicationAccept(InvationInfo invatationInfo);

        void onInviteAccept(InvationInfo invatationInfo);

        void onInviteReject(InvationInfo invatationInfo);

    }
}
