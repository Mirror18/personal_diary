package com.mirror.mapper.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mirror
 */
@Setter
@Getter
public class Criteria<T> extends GeneratedCriteria<T> {
    //true 表示and false表示or
    private boolean andOrOr = true;

    public Criteria() {
        super();
    }

}