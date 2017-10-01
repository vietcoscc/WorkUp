package com.example.viet.workup.ui.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viet.workup.R;
import com.example.viet.workup.base.BaseActivity;
import com.example.viet.workup.model.UserInfo;
import com.example.viet.workup.utils.ApplicationUtils;
import com.example.viet.workup.utils.DataUtils;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileMvpView, View.OnClickListener {
    private static final int RC_PICK_IMAGE = 1;
    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tvDisplayName)
    TextView tvDisplayName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvJoinedDate)
    TextView tvJoinedDate;
    @BindView(R.id.tvPhotoUrl)
    TextView tvPhotoUrl;
    @BindView(R.id.tvUid)
    TextView tvUid;
    @BindView(R.id.layoutDisplayName)
    LinearLayout layoutDisplayName;

    @Inject
    ProfilePresenter<ProfileMvpView> mPresenter;
    private Uri mCropImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getmActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mPresenter.onReceiveUserInfo();
        ivAvatar.setOnClickListener(this);
        layoutDisplayName.setOnClickListener(this);
    }


    @Override
    public void showUserInfo(UserInfo userInfo) {
        if (userInfo.getPhotoUrl() == null || userInfo.getPhotoUrl().isEmpty()) {
            Picasso.with(this).load(R.drawable.man)
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(ivAvatar);
        } else {
            Picasso.with(this).load(userInfo.getPhotoUrl())
                    .placeholder(android.R.drawable.screen_background_light)
                    .error(android.R.drawable.screen_background_dark)
                    .into(ivAvatar);
        }
        tvDisplayName.setText(userInfo.getDisplayName());
        tvEmail.setText(userInfo.getEmail());
        tvJoinedDate.setText(userInfo.getJoinedDate());
        tvPhotoUrl.setText(userInfo.getPhotoUrl() + " ");
        tvUid.setText(userInfo.getUid());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.layoutDisplayName) {
            final EditText edt = new EditText(this);
            final Dialog dialog = ApplicationUtils.buildInputDialog(this, "Enter display name", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ProfileActivity.this, edt.getText(), Toast.LENGTH_SHORT).show();
                    if (DataUtils.isDisplayNameValid(edt.getText().toString())) {
                        mPresenter.onChangeDisplayName(edt.getText().toString());
                    }
                }
            }, edt);
            dialog.show();
        } else if (id == R.id.ivAvatar) {
            onSelectImageClick(null);
        }
    }


    private void startCropImageActivity(Uri uri) {
        CropImage.activity(uri)
                .setMaxCropResultSize(150, 150)
                .setMinCropWindowSize(500, 500)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            try {
                mPresenter.onChangeAvatar(getContentResolver().openInputStream(result.getUri()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }
}
