address 0x18351d311d32201149a4df2a9fc2db8a {
module BCSDeserializer {
    use 0x1::Errors;
    use 0x1::Vector;
    //use 0x1::BitOperators;

    const ERR_INPUT_NOT_LARGE_ENOUGH: u64 = 201;
    const ERR_UNEXPECTED_BOOL_VALUE: u64 = 205;
    const ERR_OVERFLOW_PARSING_ULEB128_ENCODED_UINT32: u64 = 206;
    const ERR_INVALID_ULEB128_NUMBER_UNEXPECTED_ZERO_DIGIT: u64 = 207;
    const INTEGER_MAX_VALUE: u64 = 2147483647;


    public fun deserialize_bytes(input: &vector<u8>, offset: u64): (vector<u8>, u64) {
        let (len, new_offset) = deserialize_len(input, offset);
        assert(((new_offset + len) <= Vector::length(input)) && (new_offset < new_offset + 1), Errors::invalid_state(ERR_INPUT_NOT_LARGE_ENOUGH));
        let i = 0;
        let content = Vector::empty<u8>();
        while (i < len) {
            let b = *Vector::borrow(input, new_offset + i);
            Vector::push_back(&mut content, b);
            i = i + 1;
        };
        (content, new_offset + len)
    }

    public fun deserialize_option_tag(input: &vector<u8>, offset: u64): (bool, u64) {
        deserialize_bool(input, offset)
    }

    public fun deserialize_len(input: &vector<u8>, offset: u64): (u64, u64) {
        deserialize_uleb128_as_u32(input, offset)
    }

    public fun deserialize_bool(input: &vector<u8>, offset: u64): (bool, u64) {
        let b = get_byte(input, offset);
        if (b == 1) {
            return (true, offset + 1)
        } else if (b == 0) {
            return (false, offset + 1)
        } else {
            abort ERR_UNEXPECTED_BOOL_VALUE
        }
    }

    public fun deserialize_uleb128_as_u32(input: &vector<u8>, offset: u64): (u64, u64) {
        let value: u64 = 0;
        let shift = 0;
        let new_offset = offset;
        while (shift < 32) {
            let x = get_byte(input, new_offset);
            new_offset = new_offset + 1;
            let digit: u8 = x & 0x7F;
            value = value | (digit as u64) << shift;
            if ((value < 0) || (value > INTEGER_MAX_VALUE)) {
                abort ERR_OVERFLOW_PARSING_ULEB128_ENCODED_UINT32
            };
            if (digit == x) {
                if (shift > 0 && digit == 0) {
                    abort ERR_INVALID_ULEB128_NUMBER_UNEXPECTED_ZERO_DIGIT
                };
                return (value, new_offset)
            };
            shift = shift + 7
        };
        abort ERR_OVERFLOW_PARSING_ULEB128_ENCODED_UINT32
    }

    fun get_byte(input: &vector<u8>, offset: u64): u8 {
        assert(((offset + 1) <= Vector::length(input)) && (offset < offset + 1), Errors::invalid_state(ERR_INPUT_NOT_LARGE_ENOUGH));
        *Vector::borrow(input, offset)
    }

}
}
