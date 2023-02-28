package org.dddml.suidemocontracts.specialization;

import java.util.List;

/**
 * Pgae of content(list).
 */
public interface Page<T> {

    /**
     * Returns the page content as {@link List}.
     *
     * @return
     */
    List<T> getContent();

    /**
     * Returns the total amount of elements.
     *
     * @return the total amount of elements
     */
    long getTotalElements();

    /**
     * Returns the size of the page.
     *
     * @return the size of the page.
     */
    int getSize();

    /**
     * Returns the number of the current page. Is always non-negative.
     *
     * @return the number of the current page.
     */
    int getNumber();

    public static class PageImpl<T> implements Page {

        private List<T> content;
        private long totalElements;
        private int size;
        private int number;

        public PageImpl() {
        }

        public PageImpl(int size, int number) {
            this.size = size;
            this.number = number;
        }

        public PageImpl(List<T> content, long totalElements) {
            this.content = content;
            this.totalElements = totalElements;
        }

        @Override
        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }

        @Override
        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        @Override
        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

    }
}
