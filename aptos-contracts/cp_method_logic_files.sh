#!/bin/bash

source_dir="/Users/yangjiefeng/Documents/wubuku/Dapp-LCDP-Demo/aptos-contracts/sources"
target_dir="/Users/yangjiefeng/Documents/dddappp/A-Aptos-Demo/aptos-contracts/sources"

old_keyword="aptos_demo"
new_keyword="aptos_test_proj1"

for file in "${source_dir}"/*_logic.move; do
  if [[ -f "$file" ]] && grep -q "$old_keyword" "$file"; then
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
