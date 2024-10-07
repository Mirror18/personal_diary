package com.mirror.dto;

import com.mirror.result.BaseUserInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author mirror
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminDTO extends BaseUserInfoDTO {
    private List<Integer> permissions;
}
