package org.minbox.framework.api.boot.cbrc.stateverify.Interceptor.formatter;

/**
@author: xiongfei
@date: 2022/9/6 20:06
*/
public class JSONFormatter {

    static final JSONFormatter FORMATTER = findJSONFormatter();

    public static String formatJSON(String source) {
        try {
            return FORMATTER.format(source);
        } catch (Exception e) {
            return "";
        }
    }

    String format(String source) {
        return "";
    }

    private static JSONFormatter findJSONFormatter() {

        JSONFormatter fastjsonFormatter = FastjsonFormatter.buildIfSupported();
        if (fastjsonFormatter != null) {
            return fastjsonFormatter;
        }

        return new JSONFormatter();
    }
}

