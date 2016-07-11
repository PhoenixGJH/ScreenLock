package com.phoenixgjh.screenlock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Process;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 判断当前的语言环境
     *
     * @return
     */
    private boolean isChina() {
        String str = Locale.getDefault().getCountry();
        if (!TextUtils.isEmpty(str)) {
            if (str.equalsIgnoreCase("cn")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 主体功能
     */
    private void init() {
        DevicePolicyManager manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName name = new ComponentName(this, LockScreenReceiver.class);
        if (manager.isAdminActive(name)) {
            manager.lockNow();
            finish();
            android.os.Process.killProcess(Process.myPid());
        }
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", name);
        if (isChina()) {
            intent.putExtra("android.app.extra.ADD_EXPLANATION"
                    , "支持虚拟按键上滑和一键锁屏;\n首次使用需激活设备管理器，卸载需先取消激活设备管理器(系统设置-安全-设备管理器)");
        } else {
            intent.putExtra("android.app.extra.ADD_EXPLANATION"
                    , "Slide up or click to lock the screen;\nFirst time use the app you need activate the Device administrators.To unload the app you should unactivate the Device administrators(Settings-Security-Device administrators)");
        }
        startActivity(intent);
        finish();
    }
}
