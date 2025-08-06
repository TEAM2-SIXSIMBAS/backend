package org.example.schoolallianceinfor.dto;

import org.example.schoolallianceinfor.entity.Partnership;
import org.example.schoolallianceinfor.entity.Store;

public class PartnershipInfoDto {
    private String content;
    private String storeName;
    private String organization;
    private String category;
    private String type;

    public PartnershipInfoDto(Partnership p) {
        this.content = p.getContent();
        this.type = p.getType();

        Store s = p.getStore();
        this.storeName = s.getStoreName();
        this.organization = s.getOrganization();
        this.category = s.getCategory();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
