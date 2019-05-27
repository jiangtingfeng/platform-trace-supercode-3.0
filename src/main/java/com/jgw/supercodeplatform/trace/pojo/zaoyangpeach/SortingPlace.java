package com.jgw.supercodeplatform.trace.pojo.zaoyangpeach;

import java.util.Date;

public class SortingPlace {
    private Integer id;

    private String sortingPlaceName;

    private Date createTime;

    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSortingPlaceName() {
        return sortingPlaceName;
    }

    public void setSortingPlaceName(String sortingPlaceName) {
        this.sortingPlaceName = sortingPlaceName == null ? null : sortingPlaceName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}