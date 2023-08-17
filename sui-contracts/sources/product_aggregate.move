// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module sui_contracts::product_aggregate {
    use std::string::String;
    use sui::tx_context;
    use sui_contracts::product;
    use sui_contracts::product_create_logic;
    use sui_contracts::product_delete_logic;
    use sui_contracts::product_update_logic;

    public entry fun create(
        name: String,
        unit_price: u128,
        owner: address,
        product_id_generator: &mut product::ProductIdGenerator,
        ctx: &mut tx_context::TxContext,
    ) {
        let product_created = product_create_logic::verify(
            name,
            unit_price,
            owner,
            product_id_generator,
            ctx,
        );
        let product = product_create_logic::mutate(
            &product_created,
            product_id_generator,
            ctx,
        );
        product::set_product_crud_event_id(&mut product_created, product::id(&product));
        product::share_object(product);
        product::emit_product_created(product_created);
    }

    public entry fun update(
        product: product::Product,
        name: String,
        unit_price: u128,
        owner: address,
        ctx: &mut tx_context::TxContext,
    ) {
        let product_updated = product_update_logic::verify(
            name,
            unit_price,
            owner,
            &product,
            ctx,
        );
        let updated_product = product_update_logic::mutate(
            &product_updated,
            product,
            ctx,
        );
        product::update_version_and_transfer_object(updated_product, tx_context::sender(ctx));
        product::emit_product_updated(product_updated);
    }

    public entry fun delete(
        product: product::Product,
        ctx: &mut tx_context::TxContext,
    ) {
        let product_deleted = product_delete_logic::verify(
            &product,
            ctx,
        );
        let updated_product = product_delete_logic::mutate(
            &product_deleted,
            product,
            ctx,
        );
        product::drop_product(updated_product);
        product::emit_product_deleted(product_deleted);
    }

}
