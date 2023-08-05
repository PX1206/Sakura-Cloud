package com.sakura.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TableConfig {

    /**
     * 生成的表名称
     */
    private String tableName;

    /**
     * 主键数据库列名称
     */
    private String pkIdName = "id";

}
