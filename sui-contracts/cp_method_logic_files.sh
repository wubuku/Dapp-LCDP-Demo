#!/bin/bash

source_dir="/Users/yangjiefeng/Documents/wubuku/Dapp-LCDP-Demo/sui-contracts/sources"
target_dir="/Users/yangjiefeng/Documents/wubuku/test/sui-contracts/sources"

old_keyword="sui_contracts"
new_keyword="sui_test_proj1"

for file in "${source_dir}"/*_logic.move; do
  if [[ -f "$file" ]] && grep -q "$old_keyword" "$file"; then
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
