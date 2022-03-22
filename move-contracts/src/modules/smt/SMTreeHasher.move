address 0x18351d311d32201149a4df2a9fc2db8a {
module SMTreeHasher {

    use 0x1::Vector;
    use 0x1::Errors;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTHash;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTUtils;

    /// sparse merkle tree leaf(node) prefix.
    const LEAF_PREFIX: vector<u8> = x"00";
    /// sparse merkle tree (internal)node prefix.
    const NODE_PREFIX: vector<u8> = x"01";

    /// Leaf node data include: prefix + leaf_path + leaf_value_hash
    //const LEAF_DATA_LENGTH: u64 = 65;
    //const NODE_LEFT_RIGHT_DATA_LENGTH: u64 = 32;
    //const LEAF_PATH_LENGTH: u64 = 32;

    const ERROR_INVALID_LEAF_DATA: u64 = 102;
    const ERROR_INVALID_NODE_DATA: u64 = 103;
    const ERROR_INVALID_LEAF_DATA_LENGTH: u64 = 104;
    const ERROR_INVALID_NODE_DATA_LENGTH: u64 = 105;

    /// Parse leaf data.
    /// Return values:
    ///     leaf node path.
    ///     leaf node value.
    public fun parse_leaf(data: &vector<u8>): (vector<u8>, vector<u8>) {
        let data_len = Vector::length(data);

        let prefix_len = Vector::length(&LEAF_PREFIX);
        assert(data_len >= prefix_len + path_size(), Errors::invalid_argument(ERROR_INVALID_LEAF_DATA));

        let start = 0;
        let end = prefix_len;
        _ = start;//let prefix = SMTUtils::sub_u8_vector(data, start, end);

        start = end;
        end = start + path_size();
        let leaf_node_path = SMTUtils::sub_u8_vector(data, start, end);

        start = end;
        end = Vector::length(data);
        let leaf_node_value = SMTUtils::sub_u8_vector(data, start, end);
        (leaf_node_path, leaf_node_value)
    }

    public fun parse_node(data: &vector<u8>): (vector<u8>, vector<u8>) {
        let data_len = Vector::length(data);
        let prefix_len = Vector::length(&LEAF_PREFIX);
        assert(data_len == prefix_len + path_size()*2, Errors::invalid_argument(ERROR_INVALID_NODE_DATA));

        let start = 0;
        let end = prefix_len;
        _ = start;//let prefix = SMTUtils::sub_u8_vector(data, start, end);

        start = end;
        end = start + path_size();
        let left_data = SMTUtils::sub_u8_vector(data, start, end);

        start = end;
        end = Vector::length(data);
        let right_data = SMTUtils::sub_u8_vector(data, start, end);
        (left_data, right_data)
    }

    public fun digest_leaf(path: &vector<u8>, leaf_value: &vector<u8>): (vector<u8>, vector<u8>) {
        let value = LEAF_PREFIX;
        value = SMTUtils::concat_u8_vectors(&value, *path);
        value = SMTUtils::concat_u8_vectors(&value, *leaf_value);
        (SMTHash::hash(&value), value)
    }

    public fun create_leaf_data(path: &vector<u8>, leaf_value: &vector<u8>): vector<u8> {
        let value = LEAF_PREFIX;
        value = SMTUtils::concat_u8_vectors(&value, *path);
        value = SMTUtils::concat_u8_vectors(&value, *leaf_value);
        value
    }

    /// Digest leaf data. The parameter `data` includes leaf key and value.
    public fun digest_leaf_data(data: &vector<u8>): vector<u8> {
        let data_len = Vector::length(data);
        let prefix_len = Vector::length(&LEAF_PREFIX);
        assert(data_len >= prefix_len + path_size(), Errors::invalid_state(ERROR_INVALID_LEAF_DATA_LENGTH));
        SMTHash::hash(data)
    }

    public fun digest_node(left_data: &vector<u8>, right_data: &vector<u8>): (vector<u8>, vector<u8>) {
        let node_left_right_data_length = SMTHash::size();
        assert(Vector::length(left_data) == node_left_right_data_length, Errors::invalid_state(ERROR_INVALID_NODE_DATA_LENGTH));
        assert(Vector::length(right_data) == node_left_right_data_length, Errors::invalid_state(ERROR_INVALID_NODE_DATA_LENGTH));

        let value = NODE_PREFIX;
        value = SMTUtils::concat_u8_vectors(&value, *left_data);
        value = SMTUtils::concat_u8_vectors(&value, *right_data);
        (SMTHash::hash(&value), value)
    }

    public fun path(key: &vector<u8>): vector<u8> {
        digest(key)
    }

    public fun digest(data: &vector<u8>): vector<u8> {
        SMTHash::hash(data)
    }

    public fun path_size(): u64 {
        SMTHash::size()
    }

    public fun path_size_in_bits(): u64 {
        SMTHash::size()*8
    }

    public fun placeholder(): vector<u8> {
        SMTHash::size_zero_bytes()
    }

}
}