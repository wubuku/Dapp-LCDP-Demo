#!/bin/bash

source_dir="{PATH_TO}/Dapp-LCDP-Demo/rooch-contracts/sources"
target_dir="{PATH_TO}/A-Rooch-Demo/rooch-contracts/sources"

old_keyword="rooch_demo"
new_keyword="rooch_test_proj1"

for file in "${source_dir}"/*_logic.move; do
  if [[ -f "$file" ]] && grep -q "$old_keyword" "$file"; then
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
