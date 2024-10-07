package com.mirror.mapper.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author mirror
 */
@Getter
public class Criterion {
    private final String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private final String typeHandler;

    private String jdbcType;



    protected Criterion(String condition) {
        super();
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
    }

    protected Criterion(String condition, Object value, String jdbcType, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        this.jdbcType = jdbcType;
        if (value instanceof Collection<?>) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    protected Criterion(String condition, Object value, String jdbcType) {
        this(condition, value, jdbcType, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String jdbcType, String typeHandler) {
        super();
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.jdbcType = jdbcType;
        this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue, String jdbcType) {
        this(condition, value, secondValue, jdbcType, null);
    }
}