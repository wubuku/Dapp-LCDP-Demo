# Starcoin NS(Name Service) Demo

## Deploy on-chain contracts on local Dev network

Start dev network:

```shell
cd move-contracts/
starcoin -n dev -d alice
```

Open console:

```shell
cd move-contracts/
starcoin -n dev -d alice console
```

Import accounts in console:

```
account import -i {PrivateKey_of_18351d311d32201149a4df2a9fc2db8a}
account import -i {PrivateKey_of_b6d69dd935edf7f2054acf12eb884df8}
dev get-coin 0x18351d311d32201149a4df2a9fc2db8a
dev get-coin 0xb6d69dd935edf7f2054acf12eb884df8
```

Compile in shell:

```shell
cd move-contracts/
move clean && move check && move publish
```

Deploy in console:

```shell
dev package -o ./build -n packaged ./storage/0x18351d311d32201149a4df2a9fc2db8a
dev deploy ./build/packaged.blob
```


## About Move contracts

### About SMT

Copy SMT code:

```shell
cd move-contracts
mkdir -p src/modules/smt
cd src/modules/smt
cp -r ../../../../../../Elements-Studio/poly-stc-contracts/src/modules/smt/* .
cd ../../../
pwd
move check
```

## Source Code

### On-Chain Contracts

```shell
% tree ./move-contracts/src/modules
```

```txt
./move-contracts/src/modules
├── domain-name
│   ├── DomainName.move
│   ├── DomainNameAggregate.move
│   ├── DomainNameRegisterLogic.move
│   ├── DomainNameRenewLogic.move
│   └── DomainNameScripts.move
└── smt
    ├── SMTHash.move
    ├── SMTProofUtils.move
    ├── SMTProofs.move
    ├── SMTUtils.move
    └── SMTreeHasher.move
```

### Off-Chain Service

```shell
% tree ./off-chain-service
```

```txt
./off-chain-service
├── README.md
├── client
│   ├── client.go
│   └── client_test.go
├── contract
│   ├── contract.go
│   └── contract_test.go
├── db
│   ├── bcs.go
│   ├── db.go
│   ├── db_test.go
│   ├── models.go
│   ├── mysqldb.go
│   └── smt_test.go
├── events
│   ├── events_test.go
│   ├── lib.go
│   └── libext.go
├── go.mod
├── go.sum
├── handlers.go
├── main.go
├── manager
│   ├── starcoinmanager.go
│   └── starcoinmanager_test.go
├── serde-format
│   └── events.yaml
├── starcoin-ns-demo
├── tools
│   ├── restclient.go
│   ├── starcoinutil.go
│   └── util.go
├── transactions
│   ├── lib.go
│   ├── transactions_test.go
│   └── util.go
└── vo
    └── vo.go    
```

