package tools

import (
	"encoding/hex"
	"fmt"
	"math/big"
	"strings"
	//"github.com/leekchan/accounting"
)

// encode big int to hex string
func EncodeBigInt(b *big.Int) string {
	if b.Uint64() == 0 {
		return "00"
	}
	return hex.EncodeToString(b.Bytes())
}

// encode bytes to hex with prefix
func EncodeToHex(b []byte) string {
	return "0x" + hex.EncodeToString(b)
}

func HexWithPrefixToBytes(str string) ([]byte, error) {
	if !strings.HasPrefix(str, "0x") {
		return nil, fmt.Errorf("it does not have 0x prefix")
	}
	return hex.DecodeString(str[2:])
}

func HexToBytes(str string) ([]byte, error) {
	if !strings.HasPrefix(str, "0x") {
		return hex.DecodeString(str[:])
	}
	return hex.DecodeString(str[2:])
}

// func FormatTokenAmount(symbol string, precision int, amount *big.Int) string {
// 	ac := accounting.Accounting{
// 		Symbol:    symbol,
// 		Precision: precision,
// 	}
// 	return ac.FormatMoneyBigFloat(new(big.Float).Quo(new(big.Float).SetInt(amount), new(big.Float).SetInt(new(big.Int).Exp(big.NewInt(10), big.NewInt(int64(precision)), nil))))
// }

// func FormatUsdAmount(amount *big.Float) string {
// 	ac := accounting.Accounting{
// 		Symbol:    "$",
// 		Precision: 2,
// 	}
// 	return ac.FormatMoneyBigFloat(amount)
// }
