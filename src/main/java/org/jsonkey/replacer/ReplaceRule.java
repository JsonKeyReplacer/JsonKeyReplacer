package org.jsonkey.replacer;

public class ReplaceRule {
    private final String jsonPath; // JSONPath 表达式
    private final String newKey;   // 新的键名

    public ReplaceRule(String jsonPath, String newKey) {
        this.jsonPath = jsonPath;
        this.newKey = newKey;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public String getNewKey() {
        return newKey;
    }
}