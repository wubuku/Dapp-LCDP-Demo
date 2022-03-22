package transactoins

import diemtypes "github.com/starcoinorg/starcoin-go/types"

func EncodeDomainNameRegisterTxPaylaod(module_addr string,
	domain_name_id_top_level_domain string,
	domain_name_id_second_level_domain string,
	registration_period uint64,
	smt_root []byte,
	smt_non_membership_leaf_data []byte,
	smt_side_nodes []byte,
) diemtypes.TransactionPayload {
	module_accont_addr, err := diemtypes.ToAccountAddress(module_addr)
	if err != nil {
		panic("module_addr format error: " + module_addr)
	}
	return &diemtypes.TransactionPayload__ScriptFunction{
		Value: diemtypes.ScriptFunction{
			Module: diemtypes.ModuleId{
				Address: *module_accont_addr,
				Name:    diemtypes.Identifier("DomainNameScripts"),
			},
			Function: diemtypes.Identifier("register"),
			TyArgs:   []diemtypes.TypeTag{},
			Args: [][]byte{
				encode_u8vector_argument([]byte(domain_name_id_top_level_domain)),    // 	domain_name_id_top_level_domain: vector<u8>,
				encode_u8vector_argument([]byte(domain_name_id_second_level_domain)), // 	domain_name_id_second_level_domain: vector<u8>,
				encode_u64_argument(registration_period),                             // 	registration_period: u64,
				encode_u8vector_argument(smt_root),                                   // 	smt_root: vector<u8>,
				encode_u8vector_argument(smt_non_membership_leaf_data),               // 	smt_non_membership_leaf_data: vector<u8>,
				encode_u8vector_argument(smt_side_nodes),                             // 	smt_side_nodes: vector<u8>,
			},
		},
	}
}

func EncodeDomainNameRenewTxPaylaod(module_addr string,
	domain_name_id_top_level_domain string,
	domain_name_id_second_level_domain string,
	renew_period uint64,
	state_expiration_date uint64,
	state_owner [16]uint8,
	smt_root []byte,
	smt_side_nodes []byte,
) diemtypes.TransactionPayload {
	module_accont_addr, err := diemtypes.ToAccountAddress(module_addr)
	if err != nil {
		panic("module_addr format error: " + module_addr)
	}
	return &diemtypes.TransactionPayload__ScriptFunction{
		Value: diemtypes.ScriptFunction{
			Module: diemtypes.ModuleId{
				Address: *module_accont_addr,
				Name:    diemtypes.Identifier("DomainNameScripts"),
			},
			Function: diemtypes.Identifier("renew"),
			TyArgs:   []diemtypes.TypeTag{},
			Args: [][]byte{
				encode_u8vector_argument([]byte(domain_name_id_top_level_domain)),
				encode_u8vector_argument([]byte(domain_name_id_second_level_domain)),
				encode_u64_argument(renew_period),
				encode_u64_argument(state_expiration_date),
				encode_array16_u8_argument(state_owner),
				encode_u8vector_argument(smt_root),
				encode_u8vector_argument(smt_side_nodes),
			},
		},
	}
}

func ParseSmtLeaf(data []byte) ([]byte, []byte) {
	return data[len(SmtLeafPrefix()) : SmtPathSize()+len(SmtLeafPrefix())], data[len(SmtLeafPrefix())+SmtPathSize():]
}

func SmtLeafPrefix() []byte {
	leafPrefix := []byte{0}
	//nodePrefix = []byte{1}
	return leafPrefix
}

func SmtPathSize() int {
	return 32
}
