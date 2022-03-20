# Starcoin Name Service Demo Off-Chain Service

## Generate Go source from serde-format YAML file 

```shell
mvn exec:java -Dexec.mainClass="org.starcoin.serde.format.cli.SerdeGenJava" -X -Dexec.args="--language Go -w /{_PATH_TO_}/wubuku/StarcoinNSDemo/off-chain-service --onlyRetainDependenciesOfLast 1 --targetSourceDirectoryPath . /{_PATH_TO_}/starcoinorg/starcoin/etc/starcoin_types.yml:starcoin/types /{_PATH_TO_}/starcoinorg/starcoin/etc/onchain_events.yml:starcoin/events /{_PATH_TO_}/wubuku/StarcoinNSDemo/off-chain-service/serde-format/events.yaml:events"
```

