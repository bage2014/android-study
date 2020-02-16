package com.bage.tutorials.ui.profile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.cache.UserCache;
import com.bage.tutorials.component.DialogHelper;
import com.bage.tutorials.component.datepicker.DatePickerCallbackListener;
import com.bage.tutorials.component.datepicker.DatePickerHelper;
import com.bage.tutorials.domain.DateFormat;
import com.bage.tutorials.domain.User;
import com.bage.tutorials.http.HttpCallback;
import com.bage.tutorials.http.HttpRequests;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.DateUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.PicassoUtils;
import com.bage.tutorials.view.CircleImageView;
import com.bage.tutorials.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Date;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGE = 1;
    CircleImageView userIconView;
    private UserViewModel profileViewModel;
    private TextView usernameTextView;
    private TextView phoneTextView;
    private TextView sexTextView;
    private TextView birthdayTextView;
    private TextView signatureTextView;

    private DatePickerHelper datePickerHelper;
    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dialogHelper = new DialogHelper(ProfileActivity.this);
        datePickerHelper = new DatePickerHelper(ProfileActivity.this);

        userIconView = findViewById(R.id.profile_user_iconview);
        usernameTextView = findViewById(R.id.profile_user_name_textview);
        phoneTextView = findViewById(R.id.profile_user_phone_textview);
        sexTextView = findViewById(R.id.profile_user_sex_textview);
        birthdayTextView = findViewById(R.id.profile_user_birthday_textview);
        signatureTextView = findViewById(R.id.profile_user_signature_textview);

        // 初始化
        profileViewModel = new UserViewModel();
        profileViewModel.getHttpResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(HttpResult httpResult) {
                if (httpResult.isOk()) {
                    String data = httpResult.getData();
                    User user = JsonUtils.fromJson(data, User.class);
                    UserCache.cacheUser(user);
                    // 设置
                    usernameTextView.setText(user.getUsername());
                    phoneTextView.setText(user.getPhone());
                    sexTextView.setText(user.getSex());
                    birthdayTextView.setText(user.getBirthday());
                    signatureTextView.setText(user.getSignature());
                    // 图片
                    PicassoUtils.loadImage(ProfileActivity.this,user.getIcon(),userIconView);
                }
            }
        });
        profileViewModel.queryProfile();

        // 更换头像
        findViewById(R.id.profile_user_icon_edit_iconview).setOnClickListener(userIconClickListener);
        userIconView.setOnClickListener(userIconClickListener);

        findViewById(R.id.profile_user_name_edit_iconview).setOnClickListener(userNameClickListener);
        findViewById(R.id.profile_user_sex_edit_iconview).setOnClickListener(userSexClickListener);
        findViewById(R.id.profile_user_birthday_edit_iconview).setOnClickListener(userBirthdayClickListener);
        findViewById(R.id.profile_user_signature_edit_iconview).setOnClickListener(userSignatureClickListener);

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
    protected void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        // 新选择了图片
        if (requestCode == RESULT_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            CropImage.activity(selectedImage).start(this);
        }
        // 完成对图片的裁剪
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedImage = result.getUri();
                profileViewModel.updateUserIcon(new File(croppedImage.getPath()));
            }
        }
    }

    // 更换头像
    View.OnClickListener userIconClickListener = new View.OnClickListener() {
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

    // 更新名称
    View.OnClickListener userNameClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            dialogHelper.showCustomDialog(R.layout.dialog_edit_text, "Title", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TextView input = ((AlertDialog) dialogInterface).findViewById(android.R.id.text1);
                    String userName = input.getText().toString();
                    User user = UserCache.getUser();
                    user.setUsername(userName);
                    profileViewModel.updateUser(user);
                }
            }, null);
        }
    };

    // 更新性别
    View.OnClickListener userSexClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            User user = UserCache.getUser();
            user.setSex(Objects.equals(user.getSex(), "Male") ? "Famale" : "Male");
            profileViewModel.updateUser(user);
        }
    };

    // 更新出生日期
    View.OnClickListener userBirthdayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            datePickerHelper.show(getSupportFragmentManager(), new DatePickerCallbackListener() {
                @Override
                public void onPositiveButtonClick(Date date) {
                    User user = UserCache.getUser();
                    user.setBirthday(DateUtils.format(date, DateFormat.YYYY_MM_DD));
                    profileViewModel.updateUser(user);
                }

                @Override
                public void onNegativeButtonClick() {

                }

                @Override
                public void onCancel() {

                }
            });
        }
    };

    // 更新个性签名
    View.OnClickListener userSignatureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            dialogHelper.showCustomDialog(R.layout.dialog_edit_text, "Title", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TextView input = ((AlertDialog) dialogInterface).findViewById(android.R.id.text1);
                    String signature = input.getText().toString();
                    User user = UserCache.getUser();
                    user.setSignature(signature);
                    profileViewModel.updateUser(user);
                }
            }, null);
        }
    };

}
