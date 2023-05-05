module rooch_demo::article {
    use std::string::String;
    use moveos_std::object::ObjectID;

    struct Article {
        title: String,
        author: address,
        tags: vector<ObjectID>,
    }
}
