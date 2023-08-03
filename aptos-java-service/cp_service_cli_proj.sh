#!/bin/bash

source_dir="../sui-java-service/suiDemoContracts-service-cli/src/main/java/org/dddml/suidemocontracts"
target_dir="./aptosDemoContracts-service-cli/src/main/java/org/dddml/aptosdemocontracts"

if [ ! -d "$target_dir" ]; then
  mkdir -p "$target_dir"
fi

old_keyword="suidemocontracts"
new_keyword="aptosdemocontracts"

for file in "${source_dir}"/*.java; do
  if [[ -f "$file" ]]; then # && grep -q "$old_keyword" "$file"
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
