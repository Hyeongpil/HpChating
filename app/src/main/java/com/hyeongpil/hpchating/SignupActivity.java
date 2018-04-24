package com.hyeongpil.hpchating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.hyeongpil.hpchating.model.UserModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hyeongpil on 2018-04-09.
 */

public class SignupActivity extends AppCompatActivity {
    final static String TAG = SignupActivity.class.getSimpleName();
    private static final int PICK_FROM_ALBUM = 10;
    private Activity mActivity;

    @Bind(R.id.et_signup_email)
    EditText et_email;
    @Bind(R.id.et_signup_name)
    EditText et_name;
    @Bind(R.id.et_signup_pwd)
    EditText et_pwd;
    @Bind(R.id.btn_signup_signup)
    Button btn_signup;
    @Bind(R.id.iv_signup_profile)
    ImageView iv_profile;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mActivity = this;

        init();

    }

    private void init(){
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "성공 :"+task, Toast.LENGTH_SHORT).show();
                                final String uid = task.getResult().getUser().getUid();

                                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        @SuppressWarnings("VisibleForTests")
                                        String imageUrl = task.getResult().getDownloadUrl().toString();
                                        UserModel userModel = new UserModel();
                                        userModel.userName = et_name.getText().toString();
                                        userModel.profileImageUrl = imageUrl;
                                        userModel.userName = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mActivity.finish();
                                            }
                                        });
                                    }
                                });
                            }
                        });
            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_FROM_ALBUM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){
            iv_profile.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData(); // 이미지 경로 원본
        }
    }
}
