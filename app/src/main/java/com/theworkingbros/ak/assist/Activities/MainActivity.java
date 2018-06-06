package com.theworkingbros.ak.assist.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.theworkingbros.ak.assist.Data.BlogRecyclerAdapter;
import com.theworkingbros.ak.assist.Model.Blog;
import com.theworkingbros.ak.assist.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


    public class MainActivity extends AppCompatActivity {
    ImageButton cgpa,cal,map,club;
    TextView greeting;
    String name,uid;
    private DatabaseReference mDatabasereference,mdbref;
    private FirebaseDatabase mDatabase;
    private RecyclerView recyclerView;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private List<Blog> bloglist;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabasereference = mDatabase.getReference().child("AssistBlog");
        mDatabasereference.keepSynced(true);
        mdbref=mDatabase.getReference().child("AssistUsers");
        mdbref.keepSynced(true);

        cgpa = findViewById(R.id.cgpa);
        map = findViewById(R.id.map);
        bloglist = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        club = findViewById(R.id.club);
        cal = findViewById(R.id.cal);
        greeting = findViewById(R.id.greeting);
        uid=mUser.getUid();
        final Blog blog=new Blog();

        mdbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name= dataSnapshot.child(uid).child("name").getValue(String.class);
                greeting.setText(getString(R.string.welcome)+name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //////////////////////////////////////////////////////
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                if (mUser != null && mAuth != null) {
                    Intent post = new Intent(MainActivity.this, Addpost.class);
                    startActivity(post);
                    finish();
                }
                break;
            case R.id.action_signout:
                if (mUser != null && mAuth != null) {
                    mAuth.signOut();
                    Intent post = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(post);
                    finish();
                }


                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabasereference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Blog blog=dataSnapshot.getValue(Blog.class);

                bloglist.add(blog);
                Collections.reverse(bloglist);

                blogRecyclerAdapter= new BlogRecyclerAdapter(MainActivity.this,bloglist);
                recyclerView.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

    public void clickcgpa(View v){
        Intent main=new Intent(MainActivity.this,CGPA.class);
        startActivity(main);
    }
    public void clickmap(View v)
    {
        Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();
    }
    public void clickclub(View v)
    {
        Intent main=new Intent(MainActivity.this,club.class);
        startActivity(main);
    }
    public void clickcal(View v)
    {
        Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();
    }

    public void libbtn(View v)
    {
        Intent main=new Intent(MainActivity.this,library.class);
        startActivity(main);
    }
    public void dowbtn(View v)
    { Intent main=new Intent(MainActivity.this,CourseMaterial.class );
      startActivity(main);



    }


    }
