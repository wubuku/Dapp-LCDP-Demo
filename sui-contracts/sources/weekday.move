module sui_contracts::weekday {
    use std::vector;

    public fun monday(): u8 {
        1
    }

    public fun tuesday(): u8 {
        2
    }

    public fun wednesday(): u8 {
        3
    }

    public fun thursday(): u8 {
        4
    }

    public fun friday(): u8 {
        5
    }

    public fun saturday(): u8 {
        6
    }

    public fun sunday(): u8 {
        7
    }

    public fun is_valid(v: u8): bool {
        v == monday() || v == tuesday() || v == wednesday() || v == thursday() || v == friday() || v == saturday() || v == sunday()
    }

    public fun are_all_valid(vs: &vector<u8>): bool {
        let i = 0;
        let l = vector::length(vs);
        while (i < l) {
            if (!is_valid(*vector::borrow(vs, i))) {
                return false
            };
            i = i + 1;
        };
        true
    }
}
