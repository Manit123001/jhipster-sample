package club.eval.jhipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FormTemplateFieldOptions.
 */
@Entity
@Table(name = "form_template_field_options")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "formtemplatefieldoptions")
public class FormTemplateFieldOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "field_id", nullable = false)
    private Long fieldId;

    @NotNull
    @Column(name = "option_code", nullable = false)
    private String optionCode;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public FormTemplateFieldOptions fieldId(Long fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public FormTemplateFieldOptions optionCode(String optionCode) {
        this.optionCode = optionCode;
        return this;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
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
        FormTemplateFieldOptions formTemplateFieldOptions = (FormTemplateFieldOptions) o;
        if (formTemplateFieldOptions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formTemplateFieldOptions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormTemplateFieldOptions{" +
            "id=" + getId() +
            ", fieldId='" + getFieldId() + "'" +
            ", optionCode='" + getOptionCode() + "'" +
            "}";
    }
}
