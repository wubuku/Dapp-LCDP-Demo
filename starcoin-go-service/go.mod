module starcoin-ns-demo

go 1.16

require (
	github.com/celestiaorg/smt v0.2.1
	github.com/gin-gonic/gin v1.7.7
	github.com/go-playground/validator/v10 v10.10.1 // indirect
	github.com/go-sql-driver/mysql v1.6.0
	github.com/golang/protobuf v1.5.2 // indirect
	github.com/google/uuid v1.3.0
	github.com/json-iterator/go v1.1.12 // indirect
	github.com/mattn/go-isatty v0.0.14 // indirect
	github.com/modern-go/concurrent v0.0.0-20180306012644-bacd9c7ef1dd // indirect
	github.com/novifinancial/serde-reflection/serde-generate/runtime/golang v0.0.0-20220114180008-bde96ef4a0a2
	github.com/pkg/errors v0.9.1
	github.com/starcoinorg/starcoin-go v0.0.0-20220324132534-1ecb04f29836
	github.com/ugorji/go v1.2.7 // indirect
	golang.org/x/crypto v0.0.0-20220321153916-2c7772ba3064
	golang.org/x/sys v0.0.0-20220325203850-36772127a21f // indirect
	golang.org/x/xerrors v0.0.0-20200804184101-5ec99f83aff1 // indirect
	google.golang.org/protobuf v1.28.0 // indirect
	gopkg.in/yaml.v2 v2.4.0 // indirect
	gorm.io/driver/mysql v1.3.2
	gorm.io/gorm v1.23.3
)

replace github.com/celestiaorg/smt v0.2.1 => github.com/wubuku/smt v0.2.1-0.20220325130446-661bc96c92f5
