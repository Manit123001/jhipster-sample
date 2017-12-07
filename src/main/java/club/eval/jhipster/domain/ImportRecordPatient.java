package club.eval.jhipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ImportRecordPatient.
 */
@Entity
@Table(name = "import_record_patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "importrecordpatient")
public class ImportRecordPatient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "gmt_create", nullable = false)
    private LocalDate gmtCreate;

    @NotNull
    @Column(name = "gmt_modified", nullable = false)
    private LocalDate gmtModified;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    @Column(name = "send_id")
    private String sendId;

    @Column(name = "touser")
    private String touser;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "url")
    private String url;

    @Column(name = "miniprogram")
    private String miniprogram;

    @Column(name = "page")
    private String page;

    @Column(name = "form_id")
    private String formId;

    @Column(name = "data")
    private String data;

    @Column(name = "emphasiskeyword")
    private String emphasiskeyword;

    @Column(name = "status")
    private String status;

    @Column(name = "result")
    private String result;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getGmtCreate() {
        return gmtCreate;
    }

    public ImportRecordPatient gmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public void setGmtCreate(LocalDate gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDate getGmtModified() {
        return gmtModified;
    }

    public ImportRecordPatient gmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public void setGmtModified(LocalDate gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getType() {
        return type;
    }

    public ImportRecordPatient type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSendId() {
        return sendId;
    }

    public ImportRecordPatient sendId(String sendId) {
        this.sendId = sendId;
        return this;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getTouser() {
        return touser;
    }

    public ImportRecordPatient touser(String touser) {
        this.touser = touser;
        return this;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public ImportRecordPatient templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public ImportRecordPatient url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMiniprogram() {
        return miniprogram;
    }

    public ImportRecordPatient miniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
        return this;
    }

    public void setMiniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
    }

    public String getPage() {
        return page;
    }

    public ImportRecordPatient page(String page) {
        this.page = page;
        return this;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFormId() {
        return formId;
    }

    public ImportRecordPatient formId(String formId) {
        this.formId = formId;
        return this;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getData() {
        return data;
    }

    public ImportRecordPatient data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmphasiskeyword() {
        return emphasiskeyword;
    }

    public ImportRecordPatient emphasiskeyword(String emphasiskeyword) {
        this.emphasiskeyword = emphasiskeyword;
        return this;
    }

    public void setEmphasiskeyword(String emphasiskeyword) {
        this.emphasiskeyword = emphasiskeyword;
    }

    public String getStatus() {
        return status;
    }

    public ImportRecordPatient status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public ImportRecordPatient result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
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
        ImportRecordPatient importRecordPatient = (ImportRecordPatient) o;
        if (importRecordPatient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), importRecordPatient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImportRecordPatient{" +
            "id=" + getId() +
            ", gmtCreate='" + getGmtCreate() + "'" +
            ", gmtModified='" + getGmtModified() + "'" +
            ", type='" + getType() + "'" +
            ", sendId='" + getSendId() + "'" +
            ", touser='" + getTouser() + "'" +
            ", templateId='" + getTemplateId() + "'" +
            ", url='" + getUrl() + "'" +
            ", miniprogram='" + getMiniprogram() + "'" +
            ", page='" + getPage() + "'" +
            ", formId='" + getFormId() + "'" +
            ", data='" + getData() + "'" +
            ", emphasiskeyword='" + getEmphasiskeyword() + "'" +
            ", status='" + getStatus() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }
}
