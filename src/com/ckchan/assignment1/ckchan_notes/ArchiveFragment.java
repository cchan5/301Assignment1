package com.ckchan.assignment1.ckchan_notes;

import com.ckchan.assignment1.ckchan_notes.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class ArchiveFragment extends Fragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_archive, container, false);
         
        return rootView;
    }
}