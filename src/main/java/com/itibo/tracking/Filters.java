package com.itibo.tracking;

import java.util.regex.*;

public class Filters {


    public static String filterComments(String text)
    {
        Pattern pattern = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }

    public static String filterHTMLtags(String text)
    {
        Pattern pattern = Pattern.compile("<(.)+?>");
        Matcher matcher = pattern.matcher(text);
        text = matcher.replaceAll("");
        pattern = Pattern.compile("(\n){2,}");
        matcher = pattern.matcher(text);
        text = matcher.replaceAll("\n");
        return text;
    }
    //RD503242720CN
}
