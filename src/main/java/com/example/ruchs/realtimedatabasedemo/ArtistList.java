package com.example.ruchs.realtimedatabasedemo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistList extends ArrayAdapter<Artist>{

    private Activity context;
    private List<Artist> artistList;

    public ArtistList(Activity context,List<Artist> artistList){
        super(context,R.layout.artist_list_layout,artistList);
        this.context=context;
        this.artistList=artistList;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.artist_list_layout,null,true);
        TextView textViewArtistName = (TextView)listViewItem.findViewById(R.id.textViewArtistName);
        TextView textViewArtistGenre= (TextView)listViewItem.findViewById(R.id.textViewArtistGenre);
        Artist artist=artistList.get(position);
        textViewArtistName.setText(artist.getArtistName());
        textViewArtistGenre.setText(artist.getArtistGenre());
        return listViewItem;
    }
}
