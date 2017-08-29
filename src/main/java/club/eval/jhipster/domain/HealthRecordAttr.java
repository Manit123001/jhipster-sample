package club.eval.jhipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HealthRecordAttr.
 */
@Entity
@Table(name = "health_record_attr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "healthrecordattr")
public class HealthRecordAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "template_field_id")
    private Long templateFieldId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_value")
    private String fieldValue;

    @Column(name = "is_exist_record")
    private Boolean isExistRecord;

    @Column(name = "record_table")
    private String recordTable;

    @Column(name = "record_field")
    private String recordField;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public HealthRecordAttr templateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateFieldId() {
        return templateFieldId;
    }

    public HealthRecordAttr templateFieldId(Long templateFieldId) {
        this.templateFieldId = templateFieldId;
        return this;
    }

    public void setTemplateFieldId(Long templateFieldId) {
        this.templateFieldId = templateFieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public HealthRecordAttr fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public HealthRecordAttr fieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Boolean isIsExistRecord() {
        return isExistRecord;
    }

    public HealthRecordAttr isExistRecord(Boolean isExistRecord) {
        this.isExistRecord = isExistRecord;
        return this;
    }

    public void setIsExistRecord(Boolean isExistRecord) {
        this.isExistRecord = isExistRecord;
    }

    public String getRecordTable() {
        return recordTable;
    }

    public HealthRecordAttr recordTable(String recordTable) {
        this.recordTable = recordTable;
        return this;
    }

    public void setRecordTable(String recordTable) {
        this.recordTable = recordTable;
    }

    public String getRecordField() {
        return recordField;
    }

    public HealthRecordAttr recordField(String recordField) {
        this.recordField = recordField;
        return this;
    }

    public void setRecordField(String recordField) {
        this.recordField = recordField;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HealthRecordAttr healthRecordAttr = (HealthRecordAttr) o;
        if (healthRecordAttr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), healthRecordAttr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HealthRecordAttr{" +
            "id=" + getId() +
            ", templateId='" + getTemplateId() + "'" +
            ", templateFieldId='" + getTemplateFieldId() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldValue='" + getFieldValue() + "'" +
            ", isExistRecord='" + isIsExistRecord() + "'" +
            ", recordTable='" + getRecordTable() + "'" +
            ", recordField='" + getRecordField() + "'" +
            "}";
    }
}
