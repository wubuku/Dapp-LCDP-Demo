# Starcoin Name Service Demo Off-Chain Service

## Generate Go source from serde-format YAML file 

```shell
mvn exec:java -Dexec.mainClass="org.starcoin.serde.format.cli.SerdeGenJava" -X -Dexec.args="--language Go -w /{_PATH_TO_}/wubuku/StarcoinNSDemo/off-chain-service --onlyRetainDependenciesOfLast 1 --targetSourceDirectoryPath . /{_PATH_TO_}/starcoinorg/starcoin/etc/starcoin_types.yml:starcoin/types /{_PATH_TO_}/starcoinorg/starcoin/etc/onchain_events.yml:starcoin/events /{_PATH_TO_}/wubuku/StarcoinNSDemo/off-chain-service/serde-format/events.yaml:events"
```

## API

### Get DomainName state & SMT proof

HTTP Get URL like this:

```
http://localhost:8099/getDomainNameStateAndSmtProof?top_level_domain=stc&second_level_domain=a
```

If DomainName exists, response like below:

```json
{
  "domain_name_state": {
    "domain_name_id": {
      "top_level_domain": "stc",
      "second_level_domain": "a"
    },
    "expiration_date": 31536039704,
    "owner": "0xb6d69dd935edf7f2054acf12eb884df8"
  },
  "sparse_merkle_proof": {
    "side_nodes": [
      "0x9804fced5710308478324a09c208dedf4a7980d2ec090f5cf1ff43dca7e4757d",
      "0x0000000000000000000000000000000000000000000000000000000000000000",
      "0x73f243772f9c77603c7dd76740a8f072bbe9c60fa2400f7242dd33ce439d594f",
      "0xd8daf8f61c3a8cee5d5b6e832eb38890463989391bd2cd465d74e6f816cb92d9",
      "0x75300b946296dba1954d227efd3f4db0aaa3d3f0f0990b8aa750995c6f895a5c"
    ],
    "non_membership_leaf_data": "0x"
  },
  "smt_root": "0xc3c31156aa20a7f6b753a4b744fd5e20c65d8001a91b0eeff5f364afc992c2ca"
}
```

If DomainName doesn't exist, response like below:

```json
{
  "domain_name_state": null,
  "sparse_merkle_proof": {
    "side_nodes": [
      "0x95284c2578815c59ef7706635216ba7a837aa09c77406e850db825d0169ac4f4",
      "0x532df1b6c190890e5a4bad41908931b7b4b3b52346aae06fc267b2d0e694c570"
    ],
    "non_membership_leaf_data": "0x007b8767d0cedca549b2d6744e61196a4c00125032c5bcb951955a61e9e5802613f617f900a3259b7c90a648c41f314a64622c4f44b5653b56c327896422d2fe11"
  },
  "smt_root": "0xc3c31156aa20a7f6b753a4b744fd5e20c65d8001a91b0eeff5f364afc992c2ca"
}
```

Can also get DomainName state and SMT proof by specific root:

```
http://localhost:8099/getDomainNameStateAndSmtProof?top_level_domain=stc&second_level_domain=a&smt_root=0xc371efcbd4fe75ac4ec21d74b2368048505304a58d38047bce7a8f935fe35a9d
```
