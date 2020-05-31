package com.bage.tutorials;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bage.tutorials.cache.UserCache;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.repository.UserRepository;
import com.bage.tutorials.ui.profile.ProfileActivity;
import com.bage.tutorials.ui.settting.SettingsActivity;
import com.bage.tutorials.utils.ContextUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.PicassoUtils;
import com.bage.tutorials.view.CircleImageView;
import com.bage.tutorials.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private UserRepository userRepository;
    private CircleImageView userIcon;
    private UserViewModel mainViewModel;
    private TextView userName;
    private TextView userMail;
    private TextView userSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tv,R.id.nav_deliver, R.id.nav_settings, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (navigationView.getHeaderCount() > 0) {
            View headerView = navigationView.getHeaderView(0);
            userIcon = headerView.findViewById(R.id.nav_menu_user_icon);
            userName = headerView.findViewById(R.id.nav_menu_user_name);
            userMail = headerView.findViewById(R.id.nav_menu_user_mail);
            userSignature = headerView.findViewById(R.id.nav_menu_user_signature);
            userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
            });
        }
        userRepository = new UserRepository(this);

        String jwt = userRepository.getJwt();
        if (Objects.nonNull(jwt) && jwt.length() > 0) {
            mainViewModel = new UserViewModel();
            mainViewModel.getHttpResult().observe(this, new Observer<HttpResult>() {
                @Override
                public void onChanged(HttpResult httpResult) {
                    if (httpResult.isOk()) {
                        String data = httpResult.getData();
                        User user = JsonUtils.fromJson(data, User.class);
                        // 缓存
                        UserCache.cacheUser(user);
                        // 初始化
                        initUserInfo(user);
                    }
                }
            });
            mainViewModel.queryProfile();
        }

        // todo 换成背景线程操作
        ContextUtils.initPicassoDownloader(MainActivity.this);
    }

    private void initUserInfo(User user) {
        // 头像
        PicassoUtils.loadImage(MainActivity.this, user.getIconUrl(), userIcon);
        userName.setText(user.getUsername());
        userMail.setText(user.getMail());
        userSignature.setText(user.getSignature());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 有可能编辑了用户信息，需要重新加载
        User user = UserCache.getUser();
        if (user != null) {
            initUserInfo(user);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
