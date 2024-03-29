// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.article;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.roochdemocontracts.rooch.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReferenceUpdated {
    private String id;

    private BigInteger version;

    private BigInteger referenceNumber;

    private String title;

    private com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> url;

    private com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(BigInteger referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> getUrl() {
        return url;
    }

    public void setUrl(com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> url) {
        this.url = url;
    }

    public com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> getAuthor() {
        return author;
    }

    public void setAuthor(com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ReferenceUpdated{" +
                "id=" + '\'' + id + '\'' +
                ", version=" + version +
                ", referenceNumber=" + referenceNumber +
                ", title=" + '\'' + title + '\'' +
                ", url=" + url +
                ", author=" + author +
                '}';
    }

}
