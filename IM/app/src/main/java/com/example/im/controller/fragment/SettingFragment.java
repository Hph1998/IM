package com.example.im.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im.R;
import com.example.im.controller.activity.LoginActivity;
import com.example.im.controller.activity.PersonMessageActivity;
import com.example.im.model.Model;
import com.example.im.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class SettingFragment extends Fragment {

    private Button bt_setting_out;
    private Button bt_person;
    private TextView tx_hxid;
    private TextView tx_nick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);

        initView(view);

        return view;
    }

    private void initView(View view){
        bt_setting_out = (Button)view.findViewById(R.id.bt_setting_out);
        bt_person = view.findViewById(R.id.bt_person);
        tx_hxid = view.findViewById(R.id.tx_hxid);
        tx_nick = view.findViewById(R.id.tx_nick);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData(){
        bt_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), PersonMessageActivity.class);
                        intent.putExtra("key",EMClient.getInstance().getCurrentUser());
                        startActivityForResult(intent,1);
                    }
                });
            }
        });

        UserInfo user = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());
        tx_hxid.setText("账户："+user.getHxid());
        tx_nick.setText("昵称："+user.getNick());

        bt_setting_out.setText("退出登录（"+ EMClient.getInstance().getCurrentUser()+")");
        bt_setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {

                                Model.getInstance().getDbManager().close();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),"退出成功",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);

                                        getActivity().finish();
                                    }
                                });
                            }

                            @Override
                            public void onError(int code, String error) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),"退出失败"+error,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode ,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==2){//当请求码是1&&返回码是2进行下面操作
            String content=data.getStringExtra("data");
            tx_nick.setText("昵称："+content);
        }
    }

}
