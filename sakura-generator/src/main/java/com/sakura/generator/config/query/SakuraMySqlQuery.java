package com.sakura.generator.config.query;

import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;

public class SakuraMySqlQuery extends MySqlQuery {

    @Override
    public String[] fieldCustom() {
        return new String[]{"null", "default"};
    }

}
