//! account: admin, 0x18351d311d32201149a4df2a9fc2db8a, 10000000000 0x1::STC::STC
//! account: alice, 0xb6D69DD935EDf7f2054acF12eb884df8, 10000000000 0x1::STC::STC
//! account: bob, 0x49156896A605F092ba1862C50a9036c9, 10000000000 0x1::STC::STC

//! new-transaction
//! sender: admin
address admin = {{admin}};
script {
    use 0x1::Debug;
    //use 0x1::Vector;
    use 0x18351d311d32201149a4df2a9fc2db8a::DomainNameAggregate;
    use 0x18351d311d32201149a4df2a9fc2db8a::SMTreeHasher;

    fun test_init_genesis(signer: signer) {
        DomainNameAggregate::init_genesis(&signer);
        let smt_root = DomainNameAggregate::get_smt_root();
        assert(SMTreeHasher::placeholder() == *&smt_root, 1001);
        Debug::print<vector<u8>>(&smt_root)
    }
}
// check: EXECUTED

