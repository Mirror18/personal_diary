package com.mirror.mapper.core;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mirror
 */
@Getter
@Setter
public class FieldResult {
    private DbField field;
    private Object value;
    private List<?> values;

    public FieldResult(DbField field, List<?> values) {
        this.field = field;
        this.values = values;
        if (values.size() == 1) {
            this.value = values.getFirst();
        }
    }
}