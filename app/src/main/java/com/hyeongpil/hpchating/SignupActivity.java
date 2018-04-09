package com.hyeongpil.hpchating;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hyeongpil on 2018-04-09.
 */

public class SignupActivity extends AppCompatActivity {
    final static String TAG = SignupActivity.class.getSimpleName();

    @Bind(R.id.et_signup_email)
    EditText et_email;
    @Bind(R.id.et_signup_name)
    EditText et_name;
    @Bind(R.id.et_signup_pwd)
    EditText et_pwd;
    @Bind(R.id.btn_signup_signup)
    Button btn_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

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
                            }
                        });
            }
        });
    }
}
