package com.example.ruchs.realtimedatabasedemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextName;
    Button buttonAddArtist;
    Spinner spinnerGenre;
    ListView listViewArtist;
    DatabaseReference databaseArtist;
    List<Artist> artistList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
    }
    private void initActivity(){
        editTextName=(EditText)findViewById(R.id.editTextName);
        buttonAddArtist=(Button)findViewById(R.id.buttonAddArtist);
        spinnerGenre=(Spinner)findViewById(R.id.spinnerGenre);
        listViewArtist=(ListView)findViewById(R.id.listViewArtist);
        databaseArtist= FirebaseDatabase.getInstance().getReference("artist");
        artistList=new ArrayList<>();
    }

    public void getArtistDetails(View view) {
        String artistName = editTextName.getText().toString().trim();
        String artistGenre=spinnerGenre.getSelectedItem().toString().trim();
        saveArtistDetails(artistName,artistGenre);
    }

    private void saveArtistDetails(String artistName, String artistGenre) {
        if(isValid(artistName)){
            String artistId= databaseArtist.push().getKey();
            Artist artist= new Artist(artistId,artistName,artistGenre);
            databaseArtist.child(artistId).setValue(artist);
            showToast("Artist Added");
            viewArtistDetails();
        }
        else
        {
            editTextName.setError("ENTER VALID NAME");
        }
    }

    private void viewArtistDetails() {
        final ValueEventListener valueEventListener = databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                ArtistList adapter=new ArtistList(MainActivity.this,artistList);
                listViewArtist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private boolean isValid(String artistName){
        if(artistName.isEmpty()){
            return false;
        }
        return true;
    }

    private void showToast(String toastMessage){
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}
