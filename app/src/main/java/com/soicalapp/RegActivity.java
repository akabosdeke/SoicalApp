package com.soicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegActivity extends AppCompatActivity
{
    private EditText register_confirmpassword,register_password,register_email;
    private Button createAccountbutton;
    private FirebaseAuth mauth;
    private ProgressDialog loadingBar;

    @Override
    protected void onStart() {
        super.onStart ();
        FirebaseUser currentuser=mauth.getCurrentUser ();

        if (currentuser!=null)

        {

            sendusertomainActivity();
        }

    }

    private void sendusertomainActivity() {
        Intent i=new Intent (RegActivity.this,MainActivity.class);
            startActivity (i);
            finish ();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_reg);
        mauth=FirebaseAuth.getInstance ();
        loadingBar=new ProgressDialog (this);
        register_confirmpassword=findViewById (R.id.register_confirmpassword);
        register_password=findViewById (R.id.register_password);
        register_email=findViewById (R.id.register_email);
        createAccountbutton=findViewById (R.id.createAccountbutton);
        createAccountbutton.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View view) {

                createnewAccount();


            }
        });


    }
    public  void  createnewAccount()
    {
   String email=register_email.getText ().toString ();
   String password=register_password.getText ().toString ();
   String confirm_password=register_confirmpassword.getText ().toString ();

   if (TextUtils.isEmpty (email))
   {

         register_email.setError ("Enter Email");
   }
   else  if (TextUtils.isEmpty (password))
   {
      register_password.setError ("Enter the password");
   }
   else  if (TextUtils.isEmpty (confirm_password))
   {
     register_confirmpassword.setError ("Enter the Password");
   }
   else if (!password.equals (confirm_password))
   {

       Toast.makeText (this, "your password dont match.write a valid password", Toast.LENGTH_SHORT).show ();

   }
   else{
       loadingBar.setTitle ("Creating New Account");
       loadingBar.setMessage ("Please wait while we are creating new Account");
       loadingBar.show ();
       loadingBar.setCanceledOnTouchOutside (true);

       mauth.createUserWithEmailAndPassword (email,password)
               .addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful ())
                      {
                          Toast.makeText (RegActivity.this, "you are authenticated Successfully...", Toast.LENGTH_SHORT).show ();
                          loadingBar.dismiss ();
                          SendusertosetupActivity();
                      }
                      else
                          {

                              String message=task.getException ().getMessage ();
                              Toast.makeText (RegActivity.this, "Error Occured:"+message, Toast.LENGTH_SHORT).show ();
                          }

                   }
               });

         }

    }
    public  void SendusertosetupActivity()
    {
        Intent intent=new Intent (RegActivity.this,SetupActivity.class);
      intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity (intent);
        finish ();

    }


}
