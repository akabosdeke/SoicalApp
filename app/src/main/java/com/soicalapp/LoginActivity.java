//login Activity.java

package com.soicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button loginbutton;
    private EditText useremail,userpassword;
    private TextView neednewAccountlink;
    private  FirebaseAuth mauth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        neednewAccountlink=findViewById (R.id.need_Account);
        useremail=findViewById (R.id.login_email);
        userpassword=findViewById (R.id.login_password);
        loginbutton=findViewById (R.id.loginbutton);
        mauth=FirebaseAuth.getInstance ();
        loadingbar=new ProgressDialog (this);

        neednewAccountlink.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                sendusertoregisterActivity();
            }
        });
        loginbutton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                allowusertologin();

            }

            private void allowusertologin() {
                String email=useremail.getText ().toString ();
                String password=userpassword.getText ().toString ();
                if (email.isEmpty ())
                {
                   useremail.setError ("enter email");

                }
                else  if (password.isEmpty ())
                {
                    userpassword.setError ("Enter password");

                }
                else
                {
                    loadingbar.setTitle ("Login");
                    loadingbar.setMessage ("Please Wait!we are checking your credentital to logged in The Account");
                    loadingbar.setCanceledOnTouchOutside (true);
                    loadingbar.show ();
                    mauth.signInWithEmailAndPassword (email,password)
                            .addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful ())
                                    {
                                        sendusertomainActivity();
                                        Toast.makeText (LoginActivity.this, "You are logged In Successfully", Toast.LENGTH_SHORT).show ();
                                        loadingbar.dismiss ();
                                    }
                                    else
                                    {
                                        String message=task.getException ().getMessage ();
                                        Toast.makeText (LoginActivity.this, "Error occoured.the Error is:"+message, Toast.LENGTH_SHORT).show ();
                                        loadingbar.dismiss ();
                                    }
                                }

                                private void sendusertomainActivity()
                                {
                                    Intent mainintent=new Intent (LoginActivity.this,MainActivity.class);
                                    mainintent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity (mainintent);
                                    finish ();
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart ();
        FirebaseUser currentuser=mauth.getCurrentUser ();

        if (currentuser!=null)

        {

            sendusertomainActivity();
        }

    }

    private void sendusertomainActivity()
    {
        Intent i=new Intent (LoginActivity.this,MainActivity.class);
        startActivity (i);
        finish ();
    }

    public  void  sendusertoregisterActivity()
    {
        Intent i=new Intent (LoginActivity.this,RegActivity.class);
        startActivity (i);
        finish ();
    }
}
