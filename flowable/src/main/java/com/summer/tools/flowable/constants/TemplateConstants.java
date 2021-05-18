package com.summer.tools.flowable.constants;

import lombok.Getter;

public interface TemplateConstants {

    @Getter
    enum IdPrefixEnum implements TemplateConstants{
        TEMPLATE("template", "流程模板表id前缀"),
        NODE("node", "节点表id前缀"),
        LINE("line", "连线表id前缀");

        private final String value;
        private final String name;

        IdPrefixEnum(String value, String name){
            this.value = value;
            this.name = name;
        }
    }

    @Getter
    enum TemplateStatusEnum implements TemplateConstants{
        DRAFT(1, "草稿状态"),
        DEPLOY(2, "发布状态"),
        DELETE(3, "删除状态");

        private final int value;
        private final String name;

        TemplateStatusEnum(int value, String name){
            this.value = value;
            this.name = name;
        }
    }
}
