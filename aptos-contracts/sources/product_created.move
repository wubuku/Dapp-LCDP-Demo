// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module aptos_demo::product_created {

    use aptos_demo::product::{Self, ProductCreated};
    use std::string::String;

    public fun product_id(product_created: &ProductCreated): String {
        product::product_created_product_id(product_created)
    }

    public fun name(product_created: &ProductCreated): String {
        product::product_created_name(product_created)
    }

    public fun unit_price(product_created: &ProductCreated): u128 {
        product::product_created_unit_price(product_created)
    }

}
