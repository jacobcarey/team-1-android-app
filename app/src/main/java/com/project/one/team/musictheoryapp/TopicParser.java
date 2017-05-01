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

    /**
     * Convert a provided topic identifier to a topic name.
     * @param topicID The topic identifier to convert.
     * @return Returns the name of the topic.
     */
    public static String topicIDToName(String topicID)
    {
        return topicName(topicIDToTopic(topicID));
    }

    /**
     * Converts a provided topic identifier to it's filename.
     * @param topicID The topic identifier to convert.
     * @return Returns the filename of the topic's content files.
     */
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
            case "scaleconmin":
                return "Minor Scale Construction";
            case "cconstruction":
                return "Chord Construction";
            case "sheetmusic":
                return "Sheet Music";
            case "scaledegrees":
                return "Scale Degrees";
            default:
                return "Invalid topic ID!";
        }

    }

    /**
     * Converts a provided topic identifier to it's topic difficulty classification.
     * @param topicID The topic identifier to convert.
     * @return Returns the difficulty of the topic. (<code>basic</code>, <code>intermediate</code> or
     * <code>advanced</code>.)
     */
    public static String topicIDToDifficulty(String topicID) {
        return topicID.split("/")[0];
    }

    /**
     * Returns whether the provider topic identifier has a quiz content file.
     * @param context The Context of the Activity calling this method.
     * @param topic The topic identifier to check.
     * @return Returns <code>true</code> if the topic has a quiz, <code>false</code> otherwise.
     */
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
