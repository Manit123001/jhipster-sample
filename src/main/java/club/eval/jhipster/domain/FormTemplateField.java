package club.eval.jhipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FormTemplateField.
 */
@Entity
@Table(name = "form_template_field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "formtemplatefield")
public class FormTemplateField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @NotNull
    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @NotNull
    @Column(name = "array_id", nullable = false)
    private Long arrayId;

    @NotNull
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "dict_type", nullable = false)
    private String dictType;

    @NotNull
    @Column(name = "jhi_sort", nullable = false)
    private Integer sort;

    @NotNull
    @Column(name = "is_exist_record", nullable = false)
    private Boolean isExistRecord;

    @NotNull
    @Column(name = "record_id", nullable = false)
    private String recordId;

    @Column(name = "record_table")
    private String recordTable;

    @Column(name = "record_field")
    private String recordField;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public FormTemplateField parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public FormTemplateField templateId(Long templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getArrayId() {
        return arrayId;
    }

    public FormTemplateField arrayId(Long arrayId) {
        this.arrayId = arrayId;
        return this;
    }

    public void setArrayId(Long arrayId) {
        this.arrayId = arrayId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public FormTemplateField groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public FormTemplateField name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public FormTemplateField label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDictType() {
        return dictType;
    }

    public FormTemplateField dictType(String dictType) {
        this.dictType = dictType;
        return this;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public Integer getSort() {
        return sort;
    }

    public FormTemplateField sort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean isIsExistRecord() {
        return isExistRecord;
    }

    public FormTemplateField isExistRecord(Boolean isExistRecord) {
        this.isExistRecord = isExistRecord;
        return this;
    }

    public void setIsExistRecord(Boolean isExistRecord) {
        this.isExistRecord = isExistRecord;
    }

    public String getRecordId() {
        return recordId;
    }

    public FormTemplateField recordId(String recordId) {
        this.recordId = recordId;
        return this;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordTable() {
        return recordTable;
    }

    public FormTemplateField recordTable(String recordTable) {
        this.recordTable = recordTable;
        return this;
    }

    public void setRecordTable(String recordTable) {
        this.recordTable = recordTable;
    }

    public String getRecordField() {
        return recordField;
    }

    public FormTemplateField recordField(String recordField) {
        this.recordField = recordField;
        return this;
    }

    public void setRecordField(String recordField) {
        this.recordField = recordField;
    }

    public String getDescription() {
        return description;
    }

    public FormTemplateField description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        FormTemplateField formTemplateField = (FormTemplateField) o;
        if (formTemplateField.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formTemplateField.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormTemplateField{" +
            "id=" + getId() +
            ", parentId='" + getParentId() + "'" +
            ", templateId='" + getTemplateId() + "'" +
            ", arrayId='" + getArrayId() + "'" +
            ", groupId='" + getGroupId() + "'" +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            ", dictType='" + getDictType() + "'" +
            ", sort='" + getSort() + "'" +
            ", isExistRecord='" + isIsExistRecord() + "'" +
            ", recordId='" + getRecordId() + "'" +
            ", recordTable='" + getRecordTable() + "'" +
            ", recordField='" + getRecordField() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
