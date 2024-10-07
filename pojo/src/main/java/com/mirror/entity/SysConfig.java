package com.mirror.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 系统配置（表：sys_config）
 *
 * @author mirror
 */
@Setter
@Getter
public class SysConfig {
    /**
     *
     */
    private Integer id;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 类型
     */
    private String type;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * 配置内容
     */
    private String configValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 修改用户id
     */
    private Long updateMemberId;

    /**
     * 是否删除，0：删除，1：未删除
     */
    private Boolean delFlag;

    /**
     * 是否禁用
     */
    private Boolean disable;

    public void initDefault() {
        if (this.getConfigName() == null) {
            this.setConfigName("");
        }
        if (this.getType() == null) {
            this.setType("");
        }
        if (this.getConfigKey() == null) {
            this.setConfigKey("");
        }
        if (this.getCreateTime() == null) {
            this.setCreateTime(new Date());
        }
        if (this.getUpdateTime() == null) {
            this.setUpdateTime(new Date());
        }
        if (this.getMemberId() == null) {
            this.setMemberId(0L);
        }
        if (this.getUpdateMemberId() == null) {
            this.setUpdateMemberId(0L);
        }
        if (this.getDelFlag() == null) {
            this.setDelFlag(false);
        }
        if (this.getDisable() == null) {
            this.setDisable(false);
        }
    }

    public void initUpdate() {
    }
}