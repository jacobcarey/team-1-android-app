package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

/**
 * <p>Helper class for manipulating Topic IDs into usable formats.</p>
 *
 * <p>Used for displaying human-friendly topic names when sharing on Facebook, or  for retrieving
 * the difficulty of a topic when updating {@link Progression} stats.</p>
 *
 * @author Team One
 */

public class TopicParser {

    public static String topicIDToName(String topicID)
    {
        return topicName(topicIDToTopic(topicID));
    }

    public static String topicIDToTopic(String topicID) {
        //Remove any file path prefixes
        if (topicID.contains("basic/q"))
            topicID = topicID.replace("basic/quiz/", "");

        if (topicID.contains("basic/c"))
            topicID = topicID.replace("basic/content/", "");


        if (topicID.contains("intermediate/q"))
            topicID = topicID.replace("intermediate/quiz/", "");

        if (topicID.contains("intermediate/c"))
            topicID = topicID.replace("intermediate/content/", "");


        if (topicID.contains("advanced/q"))
            topicID = topicID.replace("advanced/quiz/", "");

        if (topicID.contains("advanced/c"))
            topicID = topicID.replace("advanced/content/", "");


        return topicID;
    }

    private static String topicName(String topic) {
        switch(topic)
        {
            case "intro":
                return "Introduction to Music Theory";
            case "mnotes":
                return "Musical Notes";
            case "smpnotelen":
                return "Simple Note Lengths";
            case "advnotelen":
                return "Advanced Note Lengths";
            case "scaleconmaj":
                return "Major Scale Construction";
            case"scaleconmin":
                return "Minor Scale Construction";
            case"cconstruction":
                return "Chord Construction";
            default:
                return "Invalid topic ID!";
        }

    }

    public static String topicIDToDifficulty(String topicID) {
        return topicID.split("/")[0];
    }

    public static boolean topicHasQuiz(Context context, String topic) {
        String difficulty = topicIDToDifficulty(topic);
        String quizName = topicIDToTopic(topic) + "_quiz.json";
        try {
            return Arrays.asList(context.getAssets().list(difficulty + "/quiz")).contains(quizName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
