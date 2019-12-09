package com.example.finalproject_cooktutor.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        NIMClient.init(this, loginInfo(), options());
        if (NIMUtil.isMainProcess(this)) {
        }
        super.onCreate();
    }

    private SDKOptions options() {
        MixPushConfig mixPushConfig = new MixPushConfig();
        mixPushConfig.hwCertificateName = "C1:DE:25:DB:F4:11:BB:74:15:37:E4:CD:5F:B4:51:EE:DA:F9:82:FC:B0:18:67:EB:88:CC:2C:93:5C:1C:6E:F1";
        SDKOptions options = new SDKOptions();


        StatusBarNotificationConfig config = new StatusBarNotificationConfig();

        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;

        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        options.mixPushConfig = mixPushConfig;


        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim"; // 可以不设置，那么将采用默认路径

        options.sdkStorageRootPath = sdkPath;

        options.preloadAttach = true;

        options.thumbnailSize = 480 / 2;
        options.userInfoProvider = new UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionType, String sessionId) {
                return null;
            }
        };

        return options;
    }
    private LoginInfo loginInfo() {
        return null;
    }
}
