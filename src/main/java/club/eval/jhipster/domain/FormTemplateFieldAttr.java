package club.eval.jhipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FormTemplateFieldAttr.
 */
@Entity
@Table(name = "form_template_field_attr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "formtemplatefieldattr")
public class FormTemplateFieldAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fieldid")
    private Long fieldid;

    @Column(name = "attribute")
    private String attribute;

    @Column(name = "attributevalue")
    private String attributevalue;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldid() {
        return fieldid;
    }

    public FormTemplateFieldAttr fieldid(Long fieldid) {
        this.fieldid = fieldid;
        return this;
    }

    public void setFieldid(Long fieldid) {
        this.fieldid = fieldid;
    }

    public String getAttribute() {
        return attribute;
    }

    public FormTemplateFieldAttr attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributevalue() {
        return attributevalue;
    }

    public FormTemplateFieldAttr attributevalue(String attributevalue) {
        this.attributevalue = attributevalue;
        return this;
    }

    public void setAttributevalue(String attributevalue) {
        this.attributevalue = attributevalue;
    }

    public String getDescription() {
        return description;
    }

    public FormTemplateFieldAttr description(String description) {
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
        FormTemplateFieldAttr formTemplateFieldAttr = (FormTemplateFieldAttr) o;
        if (formTemplateFieldAttr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formTemplateFieldAttr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormTemplateFieldAttr{" +
            "id=" + getId() +
            ", fieldid='" + getFieldid() + "'" +
            ", attribute='" + getAttribute() + "'" +
            ", attributevalue='" + getAttributevalue() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
