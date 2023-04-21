/// This module provides a solution for sorted set, that is it has the properties that
/// 1) A value can be found within O(Log N) time
/// 2) The data is stored
/// 3) Adds and removals take O(N) time
module hello_table::simple_set {
    use std::error;
    use std::option;
    use std::vector;

    use aptos_std::comparator;

    /// Value already exists
    const EVALUE_ALREADY_EXISTS: u64 = 1;
    /// Value is not found
    const EVALUE_NOT_FOUND: u64 = 2;

    struct SimpleSet<Value> has copy, drop, store {
        data: vector<Value>,
    }

    public fun length<Value: store>(set: &SimpleSet<Value>): u64 {
        vector::length(&set.data)
    }

    public fun create<Value: store>(): SimpleSet<Value> {
        SimpleSet {
            data: vector::empty(),
        }
    }

    public fun borrow<Value: store>(
        set: &SimpleSet<Value>,
        value: &Value,
    ): &Value {
        let (maybe_idx, _) = find(set, value);
        assert!(option::is_some(&maybe_idx), error::invalid_argument(EVALUE_NOT_FOUND));
        let idx = option::extract(&mut maybe_idx);
        vector::borrow(&set.data, idx)
    }

    public fun borrow_at_index<Value: store>(
        set: &SimpleSet<Value>,
        idx: u64,
    ): &Value {
        vector::borrow(&set.data, idx)
    }

    public fun contains<Value: store>(
        set: &SimpleSet<Value>,
        value: &Value,
    ): bool {
        let (maybe_idx, _) = find(set, value);
        option::is_some(&maybe_idx)
    }

    public fun destroy_empty<Value: store>(set: SimpleSet<Value>) {
        let SimpleSet { data } = set;
        vector::destroy_empty(data);
    }

    public fun add<Value: store>(
        set: &mut SimpleSet<Value>,
        value: Value,
    ) {
        let (maybe_idx, maybe_placement) = find(set, &value);
        assert!(option::is_none(&maybe_idx), error::invalid_argument(EVALUE_ALREADY_EXISTS));

        // Append to the end and then swap elements until the list is ordered again
        vector::push_back(&mut set.data, value);

        let placement = option::extract(&mut maybe_placement);
        let end = vector::length(&set.data) - 1;
        while (placement < end) {
            vector::swap(&mut set.data, placement, end);
            placement = placement + 1;
        };
    }

    public fun remove<Value: store>(
        set: &mut SimpleSet<Value>,
        value: &Value,
    ): Value {
        let (maybe_idx, _) = find(set, value);
        assert!(option::is_some(&maybe_idx), error::invalid_argument(EVALUE_NOT_FOUND));

        let placement = option::extract(&mut maybe_idx);
        let end = vector::length(&set.data) - 1;

        while (placement < end) {
            vector::swap(&mut set.data, placement, placement + 1);
            placement = placement + 1;
        };

        let value = vector::pop_back(&mut set.data);
        value
    }

    fun find<Value: store>(
        set: &SimpleSet<Value>,
        value: &Value,
    ): (option::Option<u64>, option::Option<u64>) {
        let length = vector::length(&set.data);

        if (length == 0) {
            return (option::none(), option::some(0))
        };

        let left = 0;
        let right = length;

        while (left != right) {
            let mid = left + (right - left) / 2;
            let potential_value = vector::borrow(&set.data, mid);
            if (comparator::is_smaller_than(&comparator::compare(potential_value, value))) {
                left = mid + 1;
            } else {
                right = mid;
            };
        };

        if (left != length && value == vector::borrow(&set.data, left)) {
            (option::some(left), option::none())
        } else {
            (option::none(), option::some(left))
        }
    }

    #[test]
    public fun add_remove_many() {
        let map = create<u64>();

        assert!(length(&map) == 0, 0);
        assert!(!contains(&map, &3), 1);
        add(&mut map, 3);
        assert!(length(&map) == 1, 2);
        assert!(contains(&map, &3), 3);
        assert!(borrow(&map, &3) == &3, 4);

        assert!(!contains(&map, &2), 6);
        add(&mut map, 2);
        assert!(length(&map) == 2, 7);
        assert!(contains(&map, &2), 8);
        assert!(borrow(&map, &2) == &2, 9);

        remove(&mut map, &2);
        assert!(length(&map) == 1, 11);
        assert!(!contains(&map, &2), 12);
        assert!(borrow(&map, &3) == &3, 13);

        remove(&mut map, &3);
        assert!(length(&map) == 0, 14);
        assert!(!contains(&map, &3), 15);

        destroy_empty(map);
    }

    #[test]
    public fun test_several() {
        let map = create<u64>();
        add(&mut map, 6);
        add(&mut map, 1);
        add(&mut map, 5);
        add(&mut map, 2);
        add(&mut map, 3);
        add(&mut map, 0);
        add(&mut map, 7);
        add(&mut map, 4);

        let idx = 0;
        while (idx < vector::length(&map.data)) {
            assert!(idx == *vector::borrow(&map.data, idx), idx);
            assert!(idx == *borrow_at_index(&map, idx), idx);
            idx = idx + 1;
        };

        remove(&mut map, &0);
        remove(&mut map, &1);
        remove(&mut map, &2);
        remove(&mut map, &3);
        remove(&mut map, &4);
        remove(&mut map, &5);
        remove(&mut map, &6);
        remove(&mut map, &7);

        destroy_empty(map);
    }

    #[test]
    #[expected_failure]
    public fun add_twice() {
        let set = create<u64>();
        add(&mut set, 3);
        add(&mut set, 3);

        remove(&mut set, &3);
        destroy_empty(set);
    }

    #[test]
    #[expected_failure]
    public fun remove_twice() {
        let set = create<u64>();
        add(&mut set, 3);
        remove(&mut set, &3);
        remove(&mut set, &3);

        destroy_empty(set);
    }
}
