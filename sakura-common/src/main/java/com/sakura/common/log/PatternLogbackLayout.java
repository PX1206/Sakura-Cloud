package com.sakura.common.log;

import ch.qos.logback.classic.PatternLayout;

public class PatternLogbackLayout extends PatternLayout {

    static {
        defaultConverterMap.put("logId", TraceIdPatternConverter.class.getName());
    }
}
