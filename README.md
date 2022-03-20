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

