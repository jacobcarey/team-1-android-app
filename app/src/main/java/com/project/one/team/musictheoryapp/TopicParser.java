package com.project.one.team.musictheoryapp;

/**
 * <p>Helper class for manipulating Topic IDs into usable formats.</p>
 *
 * <p>Used for displaying human-friendly topic names when sharing on Facebook, or  for retrieving
 * the difficulty of a topic when updating {@link Progression} stats.</p>
 *
 * @author Team One
 */

public class TopicParser {

    public static String TopicIDToName(String topicID)
    {
        return TopicName(TopicIDToTopic(topicID));
    }

    public static String TopicIDToTopic(String topicID) {
        //Remove any file path prefixes
        if (topicID.contains("basic/q")) {
            topicID = topicID.replace("basic/quiz/", "");

            if (topicID.length() > 6) {
                if (topicID.contains("basic/c"))
                    topicID = topicID.replace("basic/content/", "");
            }

        } else if (topicID.contains("intermediate/q")) {
            topicID = topicID.replace("intermediate/quiz/", "");

            if (topicID.length() > 6) {
                if (topicID.contains("intermediate/c"))
                    topicID = topicID.replace("intermediate/content/", "");
            }

        } else if (topicID.contains("advanced/q")) {
            topicID = topicID.substring(14, topicID.length());

            topicID = topicID.replace("advanced/quiz/", "");

            if (topicID.length() > 6) {
                if (topicID.contains("advanced/c"))
                    topicID = topicID.replace("advanced/content/", "");
            }
        }

        return topicID;
    }

    public static String TopicName(String topic) {
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

    public static String TopicIDToDifficulty(String topicID) {
        return topicID.split("/")[0];
    }

}
