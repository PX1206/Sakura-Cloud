package com.sakura.generator.config.query;

import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;

public class SpringBootPlusMySqlQuery extends MySqlQuery {

    @Override
    public String[] fieldCustom() {
        return new String[]{"null", "default"};
    }

}
