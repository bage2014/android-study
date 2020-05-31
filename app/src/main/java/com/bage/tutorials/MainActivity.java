package com.bage.tutorials;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.bage.tutorials.utils.PicassoUtils;
import com.bage.tutorials.view.CircleImageView;
import com.bage.tutorials.viewmodel.UserViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private AppBarConfiguration mAppBarConfiguration;
    private UserRepository userRepository;
    private CircleImageView userIcon;
    private UserViewModel mainViewModel;
    private TextView userName;
    private TextView userMail;
    private TextView userSignature;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    public void setSearchViewVisibility(boolean visibility) {
        if(Objects.nonNull(searchView)){
            searchView.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }
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

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        LoggerUtils.info(MainActivity.class,"query:" + query);
        LoggerUtils.info(MainActivity.class,"query-tag:" + getSupportFragmentManager().getPrimaryNavigationFragment().getTag());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        LoggerUtils.info(MainActivity.class,"newText:" + newText);
        return false;
    }
}
