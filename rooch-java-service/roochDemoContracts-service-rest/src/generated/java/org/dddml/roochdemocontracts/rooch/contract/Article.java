// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.rooch.bean.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Article {

    private Long offChainVersion;

    private String title;

    private String author;

    private String content;

    private String[] tags;

    private BigInteger version;

    private AnnotatedMoveTableView references;

    public Long getOffChainVersion() {
        return offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion) {
        this.offChainVersion = offChainVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public AnnotatedMoveTableView getReferences() {
        return references;
    }

    public void setReferences(AnnotatedMoveTableView references) {
        this.references = references;
    }

    @Override
    public String toString() {
        return "Article{" +
                ", offChainVersion=" + offChainVersion +
                ", title=" + '\'' + title + '\'' +
                ", author=" + '\'' + author + '\'' +
                ", content=" + '\'' + content + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", version=" + version +
                ", references=" + references +
                '}';
    }
}
