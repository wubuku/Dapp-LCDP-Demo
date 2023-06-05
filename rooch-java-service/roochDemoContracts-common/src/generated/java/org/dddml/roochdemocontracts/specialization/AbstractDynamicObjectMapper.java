package org.dddml.roochdemocontracts.specialization;


public abstract class AbstractDynamicObjectMapper<TDynamicObject, TState, TCommandCreate, TCommandMergePatch> {
    public abstract TDynamicObject mapState(TState state, String fields);

    //public abstract TDynamicObject mapState(TState state);

    public abstract TCommandCreate toCommandCreate(TDynamicObject dynamicObject);

    public abstract TCommandMergePatch toCommandMergePatch(TDynamicObject dynamicObject);

}