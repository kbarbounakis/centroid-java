package com.centroid.query;

import java.util.regex.Pattern;

import com.centroid.common.ServiceContainer;

public class ObjectNameValidator extends ServiceContainer {
    static Pattern Default = Pattern.compile("([a-zA-Z0-9_]+)");
    static Pattern Latin = Pattern.compile("([\\u0030-\\u0039\\u0041-\\u005A\\u0061-\\u007A\\u005F]+)");
    static Pattern LatinExtended = Pattern.compile("([\\u0030-\\u0039\\u0041-\\u005A\\u0061-\\u007A\\u00A0-\\u024F\\u005F]+)");
    static Pattern Greek = Pattern.compile("([\\u0030-\\u0039\\u0041-\\u005A\\u0061-\\u007A\\u0386-\\u03CE\\u005F]+)");
    static Pattern Cyrillic = Pattern.compile("([\\u0030-\\u0039\\u0041-\\u007A\\u0061-\\u007A\\u0400-\\u04FF\\u005F]+)");
    static Pattern Hebrew = Pattern.compile("([\\u0030-\\u0039\\u0041-\\u005A\\u0061-\\u007A\\u05D0-\\u05F2\\u005F]+)");
    
    protected Pattern pattern;
    protected Pattern qualifiedPattern;
    
    public ObjectNameValidator(Pattern pattern) {
        super();
        this.pattern = pattern;
        String strPattern = this.pattern.pattern();
        String finalPattern = String.format("\\*$|^%s((\\.|\\/)%s)*(\\.\\*)?$", strPattern, strPattern);
        this.qualifiedPattern = Pattern.compile(finalPattern);
    }

    public ObjectNameValidator(){
        this(Default);
    }

    public boolean test(String name, boolean qualified, boolean throwError) {
        if (name == null) {
            if (throwError) {
                throw new IllegalArgumentException("Name cannot be null");
            }
            return false;
        }
        boolean result = false;
        if (qualified) {
            result = this.qualifiedPattern.matcher(name).matches();
        } else {
            result = this.pattern.matcher(name).matches();
        }
        if (!result && throwError) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        return result;
    }

    public boolean test(String name, boolean qualified) throws IllegalArgumentException {
        return test(name, qualified, true);
    }

    public boolean test(String name) throws IllegalArgumentException {
        return test(name, false, true);
    }

    public String escape(String name, String format) {
        this.test(name, true);
        return name.replaceAll(pattern.pattern(), format);
    }

    public String escape(String name) {
        this.test(name, true);
        return name.replaceAll(pattern.pattern(), "$1");
    }

}
