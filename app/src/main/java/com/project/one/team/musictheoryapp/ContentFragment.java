package com.project.one.team.musictheoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_IMAGE = "image";
    private static final String ARG_CONTENT = "content";

    public static ContentFragment newInstance(String title, String image, String content) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        if(image == null)
            args.putString(ARG_IMAGE, "null");
        else
            args.putString(ARG_IMAGE, image);

        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String title = getArguments().getString(ARG_TITLE);
        String image = getArguments().getString(ARG_IMAGE);
        String content = getArguments().getString(ARG_CONTENT);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content, container, false);

        TextView titleTextView = (TextView) v.findViewById(R.id.titleTextView);
        titleTextView.setText(title);

        if(image != "null")
        {
            ImageView contentImageView = (ImageView) v.findViewById(R.id.contentImageView);
            contentImageView.setImageResource(getResources().getIdentifier(image, "drawable", getContext().getPackageName()));
        }

        TextView contentTextView = (TextView) v.findViewById(R.id.contentTextView);
        contentTextView.setText(content);

        return v;
    }

}
