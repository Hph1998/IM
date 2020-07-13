package com.example.im.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im.R;
import com.example.im.model.Model;
import com.example.im.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PersonMessageActivity extends Activity {

    private EditText et_newnick;
    private Button bt_newmessage;
    private TextView hxid;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        initView();
        initListener();
    }

    private void initView() {
        et_newnick = findViewById(R.id.et_newnick);
        bt_newmessage = findViewById(R.id.bt_newmessage);
        hxid = findViewById(R.id.hxid);
        Intent intent = getIntent();
        id = intent.getStringExtra("key");
        hxid.setText("账户："+ id);
        UserInfo user = Model.getInstance().getUserAccountDao().getAccountByHxId(id);
        et_newnick.setText(user.getNick());
    }

    private void initListener() {
        //创建按钮的点击事件处理
        bt_newmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                                et_newnick.getText().toString());
                        if (!updatenick) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(PersonMessageActivity.this, "资料修改失败",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            UserInfo user = new UserInfo();
                            user.setHxid(id);
                            user.setName(id);
                            user.setNick(et_newnick.getText().toString());
                            Model.getInstance().getUserAccountDao().addAccount(user);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(PersonMessageActivity.this, "资料修改成功",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent data = new Intent();
                            data.putExtra("data",et_newnick.getText().toString());
                            setResult(2,data);
                            finish();
                        }
                    }
                }).start();
            }
        });
    }

}
