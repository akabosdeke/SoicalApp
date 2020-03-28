package com.soicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView postList;
    private Toolbar mtoolbar;
    private FirebaseAuth mauth;
    private DatabaseReference userreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mauth=FirebaseAuth.getInstance ();
        userreference= FirebaseDatabase.getInstance ().getReference ().child ("Users");

        drawerLayout=findViewById (R.id.drwaer_layout);
        navigationView=findViewById (R.id.navigatio_view);
        View navviwe= navigationView.inflateHeaderView (R.layout.navigation_header);
        navigationView.setNavigationItemSelectedListener (new NavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //access navigation menu in navigation drwaer
            UserMenuSelector(menuItem);
                return false;
            }
        });
    }
    public  void   UserMenuSelector(MenuItem menuItem )
    {
     switch (menuItem.getItemId ())
        {
            case  R.id.nav_profile:
                Toast.makeText (this, "Profile clucked", Toast.LENGTH_SHORT).show ();

                break;

            case  R.id.nav_home:
                Toast.makeText (this, "home clucked", Toast.LENGTH_SHORT).show ();
                break;
            case  R.id.nav_friend:
                Toast.makeText (this, "friend clucked", Toast.LENGTH_SHORT).show ();
                break;
            case  R.id.nav_find_friend:
                Toast.makeText (this, "find friend clucked", Toast.LENGTH_SHORT).show ();
                break;
            case  R.id.nav_messages:
                Toast.makeText (this, "message clucked", Toast.LENGTH_SHORT).show ();
                break;
            case  R.id.nav_settings:
                Toast.makeText (this, "setting clucked", Toast.LENGTH_SHORT).show ();
                break;
            case  R.id.nav_logout:
                Toast.makeText (this, "logout clucked", Toast.LENGTH_SHORT).show ();
                break;





        }
    }

    @Override
    protected void onStart() {
        super.onStart ();
        FirebaseUser currentuser=mauth.getCurrentUser ();

        if (currentuser==null)

        {
            SendUserToLoginActivity();

        }
        else {
            checkuserexistance();
        }
    }




    private void SendUserToLoginActivity() {
        Intent loginintent=new Intent (MainActivity.this,LoginActivity.class);
        loginintent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity (loginintent);
        finish ();


    }
    public  void checkuserexistance()
    {
     final String currrent_usere_id= mauth.getCurrentUser ().getUid ();
     //create firebase reference parent node
        userreference.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!dataSnapshot.hasChild (currrent_usere_id))
                {
                    SendusertosetupActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }
    public  void SendusertosetupActivity()
    {
       Intent setupintent=new Intent (MainActivity.this,SetupActivity.class);
       setupintent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity (setupintent);
       finish ();
    }
}
