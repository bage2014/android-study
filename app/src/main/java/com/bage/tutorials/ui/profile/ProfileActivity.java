package com.bage.tutorials.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bage.tutorials.R;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.repository.UserRepository;
import com.bage.tutorials.utils.JsonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGE = 1;
    ImageView imageView;
    private UserRepository userRepository;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.profile_user_icon_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                Intent removeIntent = new Intent(Intent.ACTION_DELETE);
//                Intent chooserIntent = Intent.createChooser(removeIntent, getString(R.string.choose_an_option));
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{galleryIntent});
//                startActivityForResult(chooserIntent, RESULT_PICK_IMAGE);
                startActivityForResult(galleryIntent, RESULT_PICK_IMAGE);
            }
        });

        imageView = findViewById(R.id.profile_user_icon);

        userRepository = new UserRepository(this);
        String jwt = userRepository.getLoginedUser();
        if (Objects.nonNull(jwt) && jwt.length() > 0) {
            profileViewModel = new ProfileViewModel();
            profileViewModel.getHttpResult().observe(this, new Observer<HttpResult>() {
                @Override
                public void onChanged(HttpResult httpResult) {
                    String data = httpResult.getData();
                    User user = JsonUtils.fromJson(data, User.class);
                    Picasso.with(ProfileActivity.this).load(Uri.parse(user.getIcon())).into(imageView);
                }
            });
            profileViewModel.queryProfile(jwt);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        //After user has picked the image
        if (requestCode == RESULT_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            //startCropIntent(selectedImage);
            CropImage.activity(selectedImage).start(this);
        }
        //After user has cropped the image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedImage = result.getUri();
                Picasso.with(this).load(croppedImage).into(imageView);
                System.out.println("croppedImage::::::::::::::::" + croppedImage.toString());
//                getUrlFromCloudinary(croppedImage);
            }
        }
    }
}
