package org.jsonkey.replacer.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.jsfr.json.*;
import org.jsfr.json.provider.JacksonProvider;
import org.jsfr.json.provider.JsonProvider;
import org.jsonkey.replacer.JsonKeyReplacer;
import org.jsonkey.replacer.ReplaceRule;

import java.util.List;

public class JacksonKeyReplacer implements JsonKeyReplacer {
    private final ObjectMapper objectMapper;
    private final JsonSurfer jsonSurfer;

    public JacksonKeyReplacer() {
        this.objectMapper = new ObjectMapper();
        JsonProvider provider = new JacksonProvider(objectMapper);
        jsonSurfer = new JsonSurfer(JacksonParser.INSTANCE, provider);
    }

    @Override
    public String modifyKeys(String jsonStr, List<ReplaceRule> replaceRules) throws Exception {
        // 解析 JSON 字符串为 JsonNode
        JsonNode rootNode = objectMapper.readTree(jsonStr);

        SurfingConfiguration.Builder configBuilder = jsonSurfer.configBuilder();

        // 遍历替换规则并应用
        for (ReplaceRule rule : replaceRules) {
            String jsonPath = rule.getJsonPath();
            String newKey = rule.getNewKey();

            configBuilder.bind(jsonPath, new JsonPathListener() {
                @Override
                public void onValue(Object value, ParsingContext context) {
                    modifyKeyInNode(rootNode, context.getJsonPath(), newKey);
                }
            });
        }

        SurfingConfiguration config = configBuilder.build();
        jsonSurfer.surf(jsonStr, config);

        // 将修改后的 JsonNode 转换回 JSON 字符串
        return objectMapper.writeValueAsString(rootNode);
    }

    /**
     * modify JsonNode inside key name
     *
     * @param rootNode   jsonNode of root
     * @param jsonPath   jsonPath
     * @param newKey     replace result newKey
     */
    private void modifyKeyInNode(JsonNode rootNode, String jsonPath, String newKey) {
        // 获取目标节点的路径
        String path = jsonPath2PointPath(jsonPath);

        // 找到目标节点的父节点
        String parentPath = path.substring(0, path.lastIndexOf('/'));
        JsonNode parentNode = rootNode.at(parentPath);

        if (parentNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) parentNode;

            // 获取旧键名
            String oldKey = path.substring(path.lastIndexOf('/') + 1);

            // 获取旧值
            JsonNode value = objectNode.get(oldKey);

            // 删除旧键并添加新键
            objectNode.remove(oldKey);
            objectNode.set(newKey, value);
        }
    }

    private String jsonPath2PointPath(String jsonPath) {
        if (StringUtils.isBlank(jsonPath)) {
            return "";
        }

        jsonPath = StringUtils.remove(jsonPath, '$');
        jsonPath = StringUtils.remove(jsonPath, ']');
        return jsonPath.replace('.', '/').replace('[', '/');
    }
}
