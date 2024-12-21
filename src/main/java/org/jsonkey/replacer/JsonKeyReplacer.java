package org.jsonkey.replacer;

import java.util.List;

public interface JsonKeyReplacer {
    /**
     * rules for JSON Key replace。
     *
     * @param jsonStr        origin JSON String
     * @param replaceRules replace rule list
     * @return modified json string
     * @throws Exception if JSON parse or modify failed
     */
    String modifyKeys(String jsonStr, List<ReplaceRule> replaceRules) throws Exception;
}
