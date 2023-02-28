package org.dddml.suidemocontracts.specialization;

public interface TreeNode<T> {
    T getContent();

    Iterable<TreeNode<T>> getChildren();
}
