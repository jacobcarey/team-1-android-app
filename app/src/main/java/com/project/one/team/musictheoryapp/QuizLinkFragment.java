package com.project.one.team.musictheoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * <p>Represents and renders a page with a button leading to the quiz for a specific topic.</p>
 *
 * @author Team One
 *
 * @see ContentFragment
 */

public class QuizLinkFragment extends Fragment {
    private static final String ARG_TOPIC = "topic";

    public static QuizLinkFragment newInstance(String topic) {
        QuizLinkFragment fragment = new QuizLinkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOPIC, topic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String topic = getArguments().getString(ARG_TOPIC).replaceAll("content", "quiz");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quizlink, container, false);

        // Enable the quiz button
        Button quizButton = (Button) v.findViewById(R.id.quizButton);
        quizButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), QuizActivity.class);
                i.putExtra(QuizActivity.EXTRA_TOPIC, topic);
                startActivity(i);
            }
        });

        return v;
    }

}
