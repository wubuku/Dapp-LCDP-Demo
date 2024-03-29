// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::product_crud_event {

    use moveos_std::object_id::ObjectID;
    use rooch_demo::product::{Self, ProductCrudEvent};
    use std::option::Option;
    use std::string::String;

    public fun crud_type(product_crud_event: &ProductCrudEvent): u8 {
        product::product_crud_event_crud_type(product_crud_event)
    }

    public fun id(product_crud_event: &ProductCrudEvent): Option<ObjectID> {
        product::product_crud_event_id(product_crud_event)
    }

    public fun product_id(product_crud_event: &ProductCrudEvent): String {
        product::product_crud_event_product_id(product_crud_event)
    }

    public fun name(product_crud_event: &ProductCrudEvent): String {
        product::product_crud_event_name(product_crud_event)
    }

    public fun unit_price(product_crud_event: &ProductCrudEvent): u128 {
        product::product_crud_event_unit_price(product_crud_event)
    }

}
