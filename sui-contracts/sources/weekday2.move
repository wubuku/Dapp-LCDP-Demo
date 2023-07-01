module sui_contracts::weekday2 {
    use std::string::{Self, String};
    use std::vector;

    public fun monday(): String {
        string::utf8(b"Mon")
    }

    public fun tuesday(): String {
        string::utf8(b"Tue")
    }

    public fun wednesday(): String {
        string::utf8(b"Wed")
    }

    public fun thursday(): String {
        string::utf8(b"Thu")
    }

    public fun friday(): String {
        string::utf8(b"Fri")
    }

    public fun saturday(): String {
        string::utf8(b"Sat")
    }

    public fun sunday(): String {
        string::utf8(b"Sun")
    }

    public fun is_valid(v: String): bool {
        v == monday() || v == tuesday() || v == wednesday() || v == thursday() || v == friday() || v == saturday() || v == sunday()
    }

    public fun are_all_valid(vs: &vector<String>): bool {
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
