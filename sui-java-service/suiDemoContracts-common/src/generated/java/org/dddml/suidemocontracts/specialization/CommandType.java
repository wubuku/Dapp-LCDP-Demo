package org.dddml.suidemocontracts.specialization;

/**
 * Created by Yang on 2016/7/19.
 */
public interface CommandType {

    String CREATE = "Create";

    String MERGE_PATCH = "MergePatch";

    String DELETE = "Delete";

    String REMOVE = "Remove";

}
