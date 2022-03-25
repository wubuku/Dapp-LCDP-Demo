module starcoin-ns-demo

go 1.16

require (
	github.com/celestiaorg/smt v0.2.1
	github.com/go-sql-driver/mysql v1.6.0
	github.com/novifinancial/serde-reflection/serde-generate/runtime/golang v0.0.0-20220114180008-bde96ef4a0a2
	github.com/pkg/errors v0.9.1
	github.com/starcoinorg/starcoin-go v0.0.0-20220121050438-0e951f5e0a17
	golang.org/x/crypto v0.0.0-20220315160706-3147a52a75dd
	gorm.io/driver/mysql v1.3.2
	gorm.io/gorm v1.23.3
)

replace github.com/celestiaorg/smt v0.2.1 => github.com/wubuku/smt v0.2.1-0.20220325130446-661bc96c92f5
