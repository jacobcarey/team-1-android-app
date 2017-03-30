package com.project.one.team.musictheoryapp;

/**
 * Created by Cogythea on 30/03/2017.
 */

public class TopicParser {

    public static String TopicIDToName(String topicID)
    {
        //Remove any file path prefixes
        if(topicID.substring(0, 7) == "basic/q")
            topicID = topicID.substring(11, topicID.length());

        topicID = topicID.replace("basic/quiz/", "");

        if(topicID.length() > 6) {
            if (topicID.substring(0, 7) == "basic/c")
                topicID = topicID.replace("basic/content/", "");
        }

        switch(topicID)
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

}
