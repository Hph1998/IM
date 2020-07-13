package com.example.im.controller.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import com.example.im.R;
import com.example.im.controller.fragment.ChatFragment;
import com.example.im.controller.fragment.ContactListFragment;
import com.example.im.controller.fragment.SettingFragment;
import com.example.im.runtimepermissions.PermissionsManager;
import com.example.im.runtimepermissions.PermissionsResultAction;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private ChatFragment chatFragment;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestPermissions();

        initView();

        initDate();

        initListener();
    }

    private void initListener(){
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId){
                    case R.id.rb_main_chat:
                        fragment = chatFragment;
                        break;
                    case R.id.rb_main_contact:
                        fragment = contactListFragment;
                        break;
                    case R.id.rb_main_setting:
                        fragment = settingFragment;
                        break;
                }

                switchFragment(fragment);
            }
        });

        rg_main.check(R.id.rb_main_chat);
    }

    private void switchFragment(Fragment fragment){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.fl_main,fragment).commit();
    }

    private void initView(){
        rg_main = (RadioGroup)findViewById(R.id.rg_main);
    }

    private void initDate(){
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
    }

}
