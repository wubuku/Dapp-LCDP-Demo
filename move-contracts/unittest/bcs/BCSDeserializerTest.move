address 0x18351d311d32201149a4df2a9fc2db8a {
module BCSDeserializerTest {
    use 0x1::BCS;
    use 0x1::Vector;
    use 0x1::Debug;
    use 0x1::Option;
    use 0x18351d311d32201149a4df2a9fc2db8a::BCSDeserializer;

    struct AccountState has store, drop, copy {
        storage_roots: vector<Option::Option<vector<u8>>>,
    }

    fun deserialize_account_state(data: &vector<u8>, offset: u64): (AccountState, u64) {
        let (vec, new_offset) = BCSDeserializer::deserialize_option_bytes_vector(data, offset);
        (AccountState{
            storage_roots: vec
        }, new_offset)
    }

    #[test]
    fun test_deserialize_address() {
        let addr = @0x18351d311d32201149a4df2a9fc2db8a;
        let bs = BCS::to_bytes<address>(&addr);
        let (r, offset) = BCSDeserializer::deserialize_address(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        //_ = r;
        Debug::print<address>(&r);
        assert(addr == r, 111);
        assert(offset == 16, 111);
    }

    #[test]
    fun test_deserialize_u8() {
        let u: u8 = 128;
        let bs = BCS::to_bytes<u8>(&u);
        let (r, offset) = BCSDeserializer::deserialize_u8(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        //_ = r;
        Debug::print<u8>(&r);
        assert(u == r, 111);
        assert(offset == 1, 111);
    }

    #[test]
    fun test_deserialize_u32() {
        let u: u64 = 1281111;
        let bs = BCS::to_bytes<u64>(&u);
        let (r, offset) = BCSDeserializer::deserialize_u32(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        _ = r;
        Debug::print<u64>(&r);
        assert(u == r, 111);
        assert(offset == 4, 111);
    }

    #[test]
    fun test_deserialize_u64() {
        let u: u64 = 12811111111111;
        let bs = BCS::to_bytes<u64>(&u);
        let (r, offset) = BCSDeserializer::deserialize_u64(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        _ = r;
        Debug::print<u64>(&r);
        assert(u == r, 111);
        assert(offset == 8, 111);
    }

    #[test]
    fun test_deserialize_u128() {
        let max_int128 = 170141183460469231731687303715884105727;
        let u: u128 = max_int128;
        let bs = BCS::to_bytes<u128>(&u);
        let (r, offset) = BCSDeserializer::deserialize_u128(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        _ = r;
        Debug::print<u128>(&r);
        assert(u == r, 111);
        assert(offset == 16, 111);
    }

    #[test]
    public fun test_deserialize_account_state() {
        let bs = x"020001200f30a41872208c6324fa842889315b14f9be6f3dd0d5050686317adfdd0cda60";
        Debug::print<vector<u8>>(&bs);
        let (acc_state, _) = deserialize_account_state(&bs, 0);
        let rbs = BCS::to_bytes<AccountState>(&acc_state);
        Debug::print<vector<u8>>(&rbs);
        assert(bs == rbs, 111);
    }

    #[test]
    public fun test_deserialize_bytes_array() {
        let hello = b"hello";
        let world = b"world";
        let hello_world = Vector::empty<vector<u8>>();
        Vector::push_back(&mut hello_world, hello);
        Vector::push_back(&mut hello_world, world);
        let bs = BCS::to_bytes<vector<vector<u8>>>(&hello_world);
        let (r, offset) = BCSDeserializer::deserialize_bytes_vector(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        //Vector::push_back(&mut hello_world, b".");
        assert(hello_world == r, 111);
    }

    #[test]
    public fun test_deserialize_u128_array() {
        let hello:u128 = 1111111;
        let world:u128 = 2222222;
        let hello_world = Vector::empty<u128>();
        Vector::push_back(&mut hello_world, hello);
        Vector::push_back(&mut hello_world, world);
        let bs = BCS::to_bytes<vector<u128>>(&hello_world);
        let (r, offset) = BCSDeserializer::deserialize_u128_vector(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        //Vector::push_back(&mut hello_world, b".");
        assert(hello_world == r, 111);
    }

    #[test]
    public fun test_deserialize_bytes() {
        let hello = b"hello world";
        let bs = BCS::to_bytes<vector<u8>>(&hello);
        let (r, offset) = BCSDeserializer::deserialize_bytes(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        Debug::print<u64>(&offset);
        assert(hello == r, 111);
    }

    #[test]
    public fun test_deserialize_bool() {
        let t = true;
        let bs = BCS::to_bytes<bool>(&t);
        let (d, _) = BCSDeserializer::deserialize_bool(&bs, 0);
        Debug::print<bool>(&d);
        Debug::print<vector<u8>>(&bs);
        assert(d, 111);

        let f = false;
        bs = BCS::to_bytes<bool>(&f);
        (d, _) = BCSDeserializer::deserialize_bool(&bs, 0);
        Debug::print<vector<u8>>(&bs);
        assert(!d, 111);
    }

    #[test]
    public fun test_deserialize_uleb128_as_u32() {
        let i: u64 = 0x7F;
        let bs = serialize_u32_as_uleb128(i);
        let (len, offset) = BCSDeserializer::deserialize_uleb128_as_u32(&bs, 0);
        Debug::print<u64>(&len);
        Debug::print<u64>(&offset);
        Debug::print<vector<u8>>(&bs);
        assert(len == i, 111);

        let i2: u64 = 0x8F;
        let bs2 = serialize_u32_as_uleb128(i2);
        (len, offset) = BCSDeserializer::deserialize_uleb128_as_u32(&bs2, 0);
        Debug::print<u64>(&len);
        Debug::print<u64>(&offset);
        Debug::print<vector<u8>>(&bs2);
        assert(len == i2, 111);
    }


    #[test]
    public fun test_deserialize_uleb128_as_u32_max_int() {
        let max_int: u64 = 2147483647;

        let bs = serialize_u32_as_uleb128(max_int);
        let (len, offset) = BCSDeserializer::deserialize_uleb128_as_u32(&bs, 0);
        Debug::print<u64>(&len);
        Debug::print<u64>(&offset);
        Debug::print<vector<u8>>(&bs);
        assert(len == max_int, 111);
    }

    #[test]
    #[expected_failure(abort_code = 206)]
    public fun test_deserialize_uleb128_as_u32_exceeded_max_int() {
        let max_int: u64 = 2147483647;
        let exceeded_max_int: u64 = max_int + 1;

        let bs = serialize_u32_as_uleb128(exceeded_max_int);
        let (len, offset) = BCSDeserializer::deserialize_uleb128_as_u32(&bs, 0);
        Debug::print<u64>(&len);
        Debug::print<u64>(&offset);
    }


    fun serialize_u32_as_uleb128(value: u64): vector<u8> {
        let output = Vector::empty<u8>();
        while ((value >> 7) != 0) {
            Vector::push_back(&mut output, (((value & 0x7f) | 0x80) as u8));
            value = value >> 7;
        };
        Vector::push_back(&mut output, (value as u8));
        output
    }
}
}