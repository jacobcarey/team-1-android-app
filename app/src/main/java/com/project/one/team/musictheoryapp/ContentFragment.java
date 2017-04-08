package com.project.one.team.musictheoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>Represents and renders a page of tutorial content generated from a topic's JSON content file.</p>
 *
 * <p>A ContentFragment can contain a Title, an Image and a block of textual content.</p>
 *
 * @author Team One
 *
 * @see QuizLinkFragment
 */

public class ContentFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_IMAGE = "image";
    private static final String ARG_CONTENT = "content";

    /**
     * <p>Creates a new ContentFragment instance with the provided title, image and content.</p>
     * @param title The title to be displayed at the top of the page.
     * @param image The image to display in the middle of the page. (Set as <code>null</code> if no image
     *              is available for the page.)
     * @param content The content text to be displayed at the bottom of the page.
     * @return The constructed ContentFragment with the specified parameters.
     */
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

        ImageView contentImageView = (ImageView) v.findViewById(R.id.contentImageView);

        if(image != "null")
        {
            contentImageView.setImageResource(getResources().getIdentifier(image, "drawable", getContext().getPackageName()));
        }
        else
        {
            contentImageView.setLayoutParams(new LinearLayout.LayoutParams(contentImageView.getLayoutParams().width, 200));
        }

        TextView contentTextView = (TextView) v.findViewById(R.id.contentTextView);
        contentTextView.setText(content);

        return v;
    }

}
