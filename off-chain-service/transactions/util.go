package transactions

import (
	"strings"

	"github.com/novifinancial/serde-reflection/serde-generate/runtime/golang/bcs"
	"github.com/novifinancial/serde-reflection/serde-generate/runtime/golang/serde"
	diemtypes "github.com/starcoinorg/starcoin-go/types"
)

func EncodeEmptyArgsTxPaylaod(module string, function string) diemtypes.TransactionPayload {
	return &diemtypes.TransactionPayload__ScriptFunction{
		diemtypes.ScriptFunction{
			Module:   *ParseModuleId(module),
			Function: diemtypes.Identifier(function),
			TyArgs:   []diemtypes.TypeTag{},
			Args:     [][]byte{},
		},
	}
}

func EncodeU64AndU8TxPaylaod(module string, function string, u1 uint64, u2 uint8) diemtypes.TransactionPayload {
	return &diemtypes.TransactionPayload__ScriptFunction{
		diemtypes.ScriptFunction{
			Module:   *ParseModuleId(module),
			Function: diemtypes.Identifier(function),
			TyArgs:   []diemtypes.TypeTag{},
			Args:     [][]byte{encode_u64_argument(u1), encode_u8_argument(u2)},
		},
	}
}

func EncodeTwoTypeArgsAndU128TxPaylaod(module string, function string, t1, t2 diemtypes.TypeTag, u serde.Uint128) diemtypes.TransactionPayload {
	return &diemtypes.TransactionPayload__ScriptFunction{
		diemtypes.ScriptFunction{
			Module:   *ParseModuleId(module),
			Function: diemtypes.Identifier(function),
			TyArgs:   []diemtypes.TypeTag{t1, t2},
			Args:     [][]byte{encode_u128_argument(u)},
		},
	}
}

func EncodeTwoTypeArgsAndTwoU128TxPaylaod(module string, function string, t1, t2 diemtypes.TypeTag, u1, u2 serde.Uint128) diemtypes.TransactionPayload {
	return &diemtypes.TransactionPayload__ScriptFunction{
		diemtypes.ScriptFunction{
			Module:   *ParseModuleId(module),
			Function: diemtypes.Identifier(function),
			TyArgs:   []diemtypes.TypeTag{t1, t2},
			Args:     [][]byte{encode_u128_argument(u1), encode_u128_argument(u2)},
		},
	}
}

func EncodeTwoTypeArgsAndThreeU128TxPaylaod(module string, function string, t1, t2 diemtypes.TypeTag, u1, u2, u3 serde.Uint128) diemtypes.TransactionPayload {
	return &diemtypes.TransactionPayload__ScriptFunction{
		diemtypes.ScriptFunction{
			Module:   *ParseModuleId(module),
			Function: diemtypes.Identifier(function),
			TyArgs:   []diemtypes.TypeTag{t1, t2},
			Args:     [][]byte{encode_u128_argument(u1), encode_u128_argument(u2), encode_u128_argument(u3)},
		},
	}
}

func EncodeTwoTypeArgsAndFourU128TxPaylaod(module string, function string, t1, t2 diemtypes.TypeTag, u1, u2, u3, u4 serde.Uint128) diemtypes.TransactionPayload {
	return &diemtypes.TransactionPayload__ScriptFunction{
		diemtypes.ScriptFunction{
			Module:   *ParseModuleId(module),
			Function: diemtypes.Identifier(function),
			TyArgs:   []diemtypes.TypeTag{t1, t2},
			Args:     [][]byte{encode_u128_argument(u1), encode_u128_argument(u2), encode_u128_argument(u3), encode_u128_argument(u4)},
		},
	}
}

func encode_u128_argument(arg serde.Uint128) []byte {

	s := bcs.NewSerializer()
	if err := s.SerializeU128(arg); err == nil {
		return s.GetBytes()
	}

	panic("Unable to serialize argument of type u128")
}

func encode_u8vector_argument(arg []byte) []byte {

	s := bcs.NewSerializer()
	if err := s.SerializeBytes(arg); err == nil {
		return s.GetBytes()
	}

	panic("Unable to serialize argument of type u8vector")
}

func encode_vecbytes_argument(arg diemtypes.VecBytes) []byte {

	if val, err := arg.BcsSerialize(); err == nil {
		{
			return val
		}
	}

	panic("Unable to serialize argument of type vecbytes")
}

func encode_u64_argument(arg uint64) []byte {

	s := bcs.NewSerializer()
	if err := s.SerializeU64(arg); err == nil {
		return s.GetBytes()
	}

	panic("Unable to serialize argument of type u64")
}

func encode_u8_argument(arg uint8) []byte {

	s := bcs.NewSerializer()
	if err := s.SerializeU8(arg); err == nil {
		return s.GetBytes()
	}

	panic("Unable to serialize argument of type u64")
}

func encode_array16_u8_argument(arg [16]uint8) []byte {
	serializer := bcs.NewSerializer()
	for _, item := range arg {
		if err := serializer.SerializeU8(item); err != nil {
			panic("Unable to serialize argument of type [16]uint8")
		}
	}
	return serializer.GetBytes()
}

// Parse string to ModuleId
func ParseModuleId(str string) *diemtypes.ModuleId {
	ss := strings.Split(str, "::")
	if len(ss) < 2 {
		panic("module Id string format error")
	}
	addr, err := diemtypes.ToAccountAddress(ss[0])
	if err != nil {
		panic("module Id string address format error")
	}
	return &diemtypes.ModuleId{
		Address: *addr,                       //[16]uint8{164, 216, 175, 70, 82, 187, 53, 191, 210, 134, 211, 71, 12, 28, 90, 61},
		Name:    diemtypes.Identifier(ss[1]), //"CrossChainScript",
	}
}
