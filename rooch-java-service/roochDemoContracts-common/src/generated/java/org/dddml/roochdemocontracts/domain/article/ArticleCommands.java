// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.domain.article;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.roochdemocontracts.domain.*;

public class ArticleCommands {
    private ArticleCommands() {
    }

    public static class Create extends AbstractArticleCommand implements ArticleCommand {

        public String getCommandType() {
            return "Create";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * Title
         */
        private String title;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Author
         */
        private String author;

        public String getAuthor() {
            return this.author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        /**
         * Content
         */
        private String content;

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        /**
         * References
         */
        private ReferenceVO[] references;

        public ReferenceVO[] getReferences() {
            return this.references;
        }

        public void setReferences(ReferenceVO[] references) {
            this.references = references;
        }

        /**
         * Tags
         */
        private String[] tags;

        public String[] getTags() {
            return this.tags;
        }

        public void setTags(String[] tags) {
            this.tags = tags;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class AddReference extends AbstractArticleCommand implements ArticleCommand {

        public String getCommandType() {
            return "AddReference";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * Reference Number
         */
        private BigInteger referenceNumber;

        public BigInteger getReferenceNumber() {
            return this.referenceNumber;
        }

        public void setReferenceNumber(BigInteger referenceNumber) {
            this.referenceNumber = referenceNumber;
        }

        /**
         * Title
         */
        private String title;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Url
         */
        private String url;

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class UpdateReference extends AbstractArticleCommand implements ArticleCommand {

        public String getCommandType() {
            return "UpdateReference";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * Reference Number
         */
        private BigInteger referenceNumber;

        public BigInteger getReferenceNumber() {
            return this.referenceNumber;
        }

        public void setReferenceNumber(BigInteger referenceNumber) {
            this.referenceNumber = referenceNumber;
        }

        /**
         * Title
         */
        private String title;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Url
         */
        private String url;

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Author
         */
        private String author;

        public String getAuthor() {
            return this.author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

    public static class RemoveReference extends AbstractArticleCommand implements ArticleCommand {

        public String getCommandType() {
            return "RemoveReference";
        }

        public void setCommandType(String commandType) {
            //do nothing
        }

        /**
         * Id
         */
        private String id;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * Reference Number
         */
        private BigInteger referenceNumber;

        public BigInteger getReferenceNumber() {
            return this.referenceNumber;
        }

        public void setReferenceNumber(BigInteger referenceNumber) {
            this.referenceNumber = referenceNumber;
        }

        /**
         * Off Chain Version
         */
        private Long offChainVersion;

        public Long getOffChainVersion() {
            return this.offChainVersion;
        }

        public void setOffChainVersion(Long offChainVersion) {
            this.offChainVersion = offChainVersion;
        }

    }

}

