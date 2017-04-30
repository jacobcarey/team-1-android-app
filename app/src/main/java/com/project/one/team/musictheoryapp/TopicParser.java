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
        if (topicID.substring(0, 7) == "basic/q")
            topicID = topicID.substring(11, topicID.length());

        topicID = topicID.replace("basic/quiz/", "");

        if (topicID.length() > 6) {
            if (topicID.substring(0, 7) == "basic/c")
                topicID = topicID.replace("basic/content/", "");
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

            default:
                return "Invalid topic ID!";
        }

    }

    public static String TopicIDToDifficulty(String topicID) {
        return topicID.split("/")[0];
    }

}
