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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {
private EditText setupusername,setupuserfullname,setupcontryname;
private  Button save;
private CircleImageView setupprofileimage;
private FirebaseAuth mauth;
private DatabaseReference Userref;
private  String current_user_id;
private  ProgressDialog  loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_setup);
       setupusername = findViewById (R.id.setup_name);
        setupuserfullname = findViewById (R.id.setup_Fullname);
        setupcontryname = findViewById (R.id.setup_Countryname);
        save = findViewById (R.id.setup_information_btn);
       setupprofileimage = findViewById (R.id.setup_profile_image);
        mauth=FirebaseAuth.getInstance ();
       Userref= FirebaseDatabase.getInstance ().getReference ().child ("Users");//.child (current_user_id);

      //  current_user_id=mauth.getCurrentUser ().getUid ();
        loadingbar=new ProgressDialog (this);

        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                saveAccountsetupinformation ();
            }
        });
    }
    public  void saveAccountsetupinformation()
    {
        String username=setupusername.getText ().toString ();
        String fullname=setupuserfullname.getText().toString ();
        String country=setupcontryname.getText ().toString ();
        if (TextUtils.isEmpty (username))
        {
           setupusername.setError ("Enter User Name");
        }
        else if (TextUtils.isEmpty (fullname))
        {
            setupuserfullname.setError ("Enter User  Full Name");
        }
        else if (TextUtils.isEmpty (country))
        {
            setupcontryname.setError ("Enter Country Name");
        }
        else
            {
                loadingbar.setTitle ("Saving Information");
                loadingbar.setMessage ("Please Wait!we are creating a new  Account");
                loadingbar.setCanceledOnTouchOutside (true);
                loadingbar.show ();

                HashMap usermap=new HashMap ();
                usermap.put ("username",username);
                usermap.put ("fullname",fullname);
                usermap.put ("country",country);
                usermap.put ("status","hey there i mm usin soical network");
                usermap.put ("gender","none");
                usermap.put ("D.O.B","none");
                usermap.put ("relationshipstatus","none");

                Userref.updateChildren (usermap).addOnCompleteListener (new OnCompleteListener () {
                    @Override
                    public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful ())
                    {
                        SendusertomainActivity();
                        Toast.makeText (SetupActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show ();
                        loadingbar.dismiss ();
                    }
                    else
                    {
                        String message=task.getException ().getMessage ();
                        Toast.makeText (SetupActivity.this, "Error Occured"+message, Toast.LENGTH_SHORT).show ();
                        loadingbar.dismiss ();
                    }



                    }

                    private void SendusertomainActivity()
                    {
                        Intent sendintent=new Intent (SetupActivity.this,MainActivity.class);
                        sendintent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity (sendintent);
                    }
                });

            }

    }
}
