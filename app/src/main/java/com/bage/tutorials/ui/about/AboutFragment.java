package com.bage.tutorials.ui.about;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.vector.update_app.UpdateAppManager;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        aboutViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setSearchViewVisibility(false);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最简方式
                new UpdateAppManager
                        .Builder()
                        //当前Activity
                        .setActivity(activity)
                        //更新地址
                        .setUpdateUrl("https://pkg1.zhimg.com/zhihu/futureve-app-zhihuwap-ca40fb89fbd4fb3a3884429e1c897fe2-release-6.42.0(2040).apk")
                        //实现httpManager接口的对象
                        .setHttpManager(new UpdateAppHttpUtil())
                        .build()
                        .update();
//                new UpdateAppManager
//                        .Builder()
//                        //必须设置，当前Activity
//                        .setActivity(activity)
//                        //必须设置，实现httpManager接口的对象
//                        .setHttpManager(new OkGoUpdateHttpUtil())
//                        //必须设置，更新地址
//                        .setUpdateUrl(mUpdateUrl)
//
//                        //以下设置，都是可选
//                        //设置请求方式，默认get
//                        .setPost(false)
//                        //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
//                        .setParams(params)
//                        //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
//                        .hideDialogOnDownloading(false)
//                        //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
//                        .setTopPic(R.mipmap.top_8)
//                        //为按钮，进度条设置颜色，默认从顶部图片自动识别。
//                        //.setThemeColor(ColorUtil.getRandomColor())
//                        //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                        .setTargetPath(path)
//                        //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                        //.setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
//                        //不显示通知栏进度条
//                        .dismissNotificationProgress()
//                        //是否忽略版本
//                        //.showIgnoreVersion()
//
//                        .build()
//                        //检测是否有新版本
//                        .checkNewApp(new UpdateCallback() {
//                            /**
//                             * 解析json,自定义协议
//                             *
//                             * @param json 服务器返回的json
//                             * @return UpdateAppBean
//                             */
//                            @Override
//                            protected UpdateAppBean parseJson(String json) {
//                                UpdateAppBean updateAppBean = new UpdateAppBean();
//                                try {
//                                    JSONObject jsonObject = new JSONObject(json);
//                                    updateAppBean
//                                            //（必须）是否更新Yes,No
//                                            .setUpdate(jsonObject.optString("update"))
//                                            //（必须）新版本号，
//                                            .setNewVersion(jsonObject.optString("new_version"))
//                                            //（必须）下载地址
//                                            .setApkFileUrl(jsonObject.optString("apk_file_url"))
//                                            //（必须）更新内容
//                                            .setUpdateLog(jsonObject.optString("update_log"))
//                                            //大小，不设置不显示大小，可以不设置
//                                            .setTargetSize(jsonObject.optString("target_size"))
//                                            //是否强制更新，可以不设置
//                                            .setConstraint(false)
//                                            //设置md5，可以不设置
//                                            .setNewMd5(jsonObject.optString("new_md51"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                return updateAppBean;
//                            }
//
//                            /**
//                             * 网络请求之前
//                             */
//                            @Override
//                            public void onBefore() {
//                                CProgressDialogUtils.showProgressDialog(JavaActivity.this);
//                            }
//
//                            /**
//                             * 网路请求之后
//                             */
//                            @Override
//                            public void onAfter() {
//                                CProgressDialogUtils.cancelProgressDialog(JavaActivity.this);
//                            }
//
//                            /**
//                             * 没有新版本
//                             */
//                            @Override
//                            public void noNewApp() {
//                                Toast.makeText(JavaActivity.this, "没有新版本", Toast.LENGTH_SHORT).show();
//                            }
//                        });
            }
        });
        return root;
    }
}