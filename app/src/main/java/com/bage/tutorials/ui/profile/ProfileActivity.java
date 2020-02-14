package com.bage.tutorials.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bage.tutorials.R;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.repository.UserRepository;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.view.CircleImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGE = 1;
    CircleImageView userIconView;
    private UserRepository userRepository;
    private ProfileViewModel profileViewModel;
    private TextView usernameTextView;
    private TextView phoneTextView;
    private TextView sexTextView;
    private TextView birthdayTextView;
    private TextView signatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        userIconView = findViewById(R.id.profile_user_iconview);
        usernameTextView = findViewById(R.id.profile_user_name_textview);
        phoneTextView = findViewById(R.id.profile_user_phone_textview);
        sexTextView = findViewById(R.id.profile_user_sex_textview);
        birthdayTextView = findViewById(R.id.profile_user_birthday_textview);
        signatureTextView = findViewById(R.id.profile_user_signature_textview);

        // 更换头像
        findViewById(R.id.profile_user_icon_edit_iconview).setOnClickListener(onClickListener);
        userIconView.setOnClickListener(onClickListener);

        userRepository = new UserRepository(this);
        String jwt = userRepository.getLoginedUser();
        if (Objects.nonNull(jwt) && jwt.length() > 0) {
            profileViewModel = new ProfileViewModel();
            profileViewModel.getHttpResult().observe(this, new Observer<HttpResult>() {
                @Override
                public void onChanged(HttpResult httpResult) {
                    if(httpResult.isOk()){
                        String data = httpResult.getData();
                        User user = JsonUtils.fromJson(data, User.class);
                        // 设置
                        usernameTextView.setText(user.getUsername());
                        phoneTextView.setText(user.getPhone());
                        sexTextView.setText(user.getSex());
                        birthdayTextView.setText(user.getBirthday());
                        signatureTextView.setText(user.getSignature());
                        // 图片
                        Picasso.with(ProfileActivity.this).load(Uri.parse(user.getIcon())).into(userIconView);
                    }
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
                Picasso.with(this).load(croppedImage).into(userIconView);
                System.out.println("croppedImage::::::::::::::::" + croppedImage.toString());
//                getUrlFromCloudinary(croppedImage);
            }
        }
    }

    // 更换头像
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                Intent removeIntent = new Intent(Intent.ACTION_DELETE);
//                Intent chooserIntent = Intent.createChooser(removeIntent, getString(R.string.choose_an_option));
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{galleryIntent});
//                startActivityForResult(chooserIntent, RESULT_PICK_IMAGE);
            startActivityForResult(galleryIntent, RESULT_PICK_IMAGE);
        }
    };
}
