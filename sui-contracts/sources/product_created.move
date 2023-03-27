module sui_contracts::product_created {

    use std::string::String;
    use sui::object;
    use sui_contracts::product::{Self, ProductCreated};

    public fun id(product_created: &ProductCreated): object::ID {
        product::product_created_id(product_created)
    }

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
