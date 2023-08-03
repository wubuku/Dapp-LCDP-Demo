package org.dddml.aptosdemocontracts.specialization;

/**
 * Digest-based (hash-based) ID generator interface.
 * Generates a digest-based ID from the command information used to create an object.
 * Example usage:
 * <pre>{@code
 *     public String createWithoutId(AttributeSetInstanceCommand.CreateAttributeSetInstance c) {
 *         String idObj = getIdGenerator().generateId(c);
 *         AttributeSetInstanceState state = getStateRepository().get(idObj, true);
 *         if (state != null) {
 *             if (getIdGenerator().equals(c, state)) {
 *                 return state.getAttributeSetInstanceId();
 *             }
 *
 *             if (getIdGenerator().isArbitraryIdEnabled()) {
 *                 idObj = getIdGenerator().getNextId();
 *             } else {
 *                 throw DomainError.named("instanceExist", "the instance already exist, Id: %1$s , aggreate name: %2$s ", idObj, "AttributeSetInstance");
 *             }
 *         }
 *         c.setAttributeSetInstanceId(idObj);
 *         when(c);
 *         return idObj;
 *
 *     }
 * }</pre>
 *
 * @param <TId>      ID type.
 * @param <TCommand> Command type.
 * @param <TState>   State type.
 */
public interface IdGenerator<TId, TCommand, TState> {

    /**
     * Generate digest-based ID form command info.
     *
     * @param command
     * @return Digest-based (hash-based) ID.
     */
    TId generateId(TCommand command);

    /**
     * Generate an (arbitrary) ID.
     *
     * @return
     */
    TId getNextId();

    /**
     * Checks whether the existing object state is "equal" to the command used to create an object,
     * i.e., whether the existing object was created with that command.
     *
     * @param command
     * @param state
     * @return
     */
    boolean equals(TCommand command, TState state);

    /**
     * Determines if the generator can fall back to generating an arbitrary ID
     * if the generated ID conflicts with the ID of a saved object.
     *
     * @return true if falling back is allowed.
     */
    boolean isArbitraryIdEnabled();


}