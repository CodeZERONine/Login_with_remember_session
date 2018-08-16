package com.ebwebtech.vidhya;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity  implements View.OnClickListener{
   private ImageView mUserNameStatus,mPasswordStatus,mRocketAnim;
   private TextView mFgtPass;
   private TextInputLayout mUsername_TIL,mPassword_TIL;
   private TextInputEditText mUsername_TIE, mPassword_TIE;
   private Button mSigninButton;
   private Switch mRemember;
   private RememberSession mSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        mUserNameStatus=findViewById(R.id.signin_usernameStatus);
        mPasswordStatus=findViewById(R.id.signin_passwordStatus);
        mRocketAnim = findViewById(R.id.imagerocketAnimation);
        mUsername_TIL = findViewById(R.id.textinputLayout_username);
        mPassword_TIL = findViewById(R.id.textinputLayout_password);
        mUsername_TIE = findViewById(R.id.textinputeditText_username);
        mPassword_TIE = findViewById(R.id.textinputeditText_password);
        mSigninButton = findViewById(R.id.signin_button);
        mFgtPass = findViewById(R.id.signin_forgetpass_textView);
        mRemember = findViewById(R.id.signin_switch);
        mSession =new RememberSession(this);
       // Glide.with(this).load(R.mipmap.tick).into(mPasswordStatus);
        Glide.with(this).load(R.mipmap.rocket).into(mRocketAnim);

        mSigninButton.setOnClickListener(this);
        mFgtPass.setOnClickListener(this);


        mUsername_TIE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    mUserNameStatus.setVisibility(View.VISIBLE);
                    Glide.with(SignInActivity.this).load(R.mipmap.bubbles).into(mUserNameStatus);
                }
                else if(!b && isUserNameValid(mUsername_TIE.getText()) ){
                    Glide.with(SignInActivity.this).load(R.mipmap.tick).into(mUserNameStatus);
                }
                else{
                    mUserNameStatus.setVisibility(View.GONE);
                }
            }
        });



        mPassword_TIE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                  if(b){
                      mPasswordStatus.setVisibility(View.VISIBLE);
                      Glide.with(SignInActivity.this).load(R.mipmap.bubbles).into(mPasswordStatus);
                  }
                  else if(!b && isPasswordValid(mPassword_TIE.getText()) ){
                      Glide.with(SignInActivity.this).load(R.mipmap.tick).into(mPasswordStatus);
                  }
                 else{
                      mPasswordStatus.setVisibility(View.GONE);
                  }

            }
        });
        mPassword_TIE.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER) && isPasswordValid(mPassword_TIE.getText()) )
                {
                    Glide.with(SignInActivity.this).load(R.mipmap.tick).into(mPasswordStatus);
                    showButton();
                    return true;
                }
                else if(isPasswordValid(mPassword_TIE.getText()))
                {
                    Glide.with(SignInActivity.this).load(R.mipmap.tick).into(mPasswordStatus);
                    showButton();
                }
                if(mPassword_TIE.getText().length()<8){
                    hideButton();
                }
                if(isPasswordValid(mPassword_TIE.getText())){
                    mPassword_TIL.setError(null);//Clear Error
                }
                return false;
            }
        });

        mUsername_TIE.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(isUserNameValid(mUsername_TIE.getText())){
                    mUsername_TIL.setError(null);//clear Error
                }
                return false;
            }
        });
        //Checking for presence of previous Session
          HashMap<String ,String> userData=mSession.getUserData();
        if(mSession.isSessionCreated()){
                    mUsername_TIE.setText(userData.get("username"));
                    mPassword_TIE.setText(userData.get("password"));
                    mRemember.setChecked(true);
                    if(isPasswordValid(mPassword_TIE.getText())){
                        showButton();
                    }
        }
        else
        {
            mRemember.setChecked(false);
        }

    }

    @Override
    public void onClick(View view) {
           switch(view.getId())
           {
               case R.id.signin_button:  if(CheckFields())
                                                                {
                                                                       rememberSession();
                                                                       startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                                                   }
                                                                   break;
               case R.id.signin_forgetpass_textView:
                                                                           hideButton();
                                                                           startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
                                                     break;
           }
    }

    private void rememberSession()
    {
        if(mRemember.isChecked()){
            //Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
            mSession.CreateSession(true,mUsername_TIE.getText().toString().trim(),mPassword_TIE.getText().toString().trim());
        }
        else
        {
           // Toast.makeText(this, "Not Checked", Toast.LENGTH_SHORT).show();
            mSession.DeleteSession();
            mPassword_TIE.setText("");
            mUsername_TIE.setText("");
        }

    }

    private boolean CheckFields() {
        if(!isPasswordValid(mPassword_TIE.getText())){
            mPassword_TIL.setError("Password must contain at least 8 characters");
            return false;
        }
        else if (!isUserNameValid(mUsername_TIE.getText())){
            mUsername_TIL.setError("Username cannot be empty");
            return false;
        }
        else{
            return true;
        }
        }

    private boolean isPasswordValid(@Nullable Editable textView){
        return textView!=null && textView.length()>=8;
    }
    private boolean isUserNameValid(@Nullable Editable text){
          return text!=null && text.length()>=2;
    }

    private void showButton(){
        mSigninButton.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(mSigninButton,"translationY", 130f);
        anim.setDuration(2000);
        anim.start();
    }
    private void hideButton(){
        mSigninButton.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator.ofFloat(mSigninButton,"translationY", -130f);
        anim.setDuration(2000);
        anim.start();
    }

}
