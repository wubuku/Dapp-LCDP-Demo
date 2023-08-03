#!/bin/bash

source_dir="../sui-java-service/suiDemoContracts-common/src/generated/java/org/dddml/suidemocontracts/specialization"
target_dir="./aptosDemoContracts-common/src/generated/java/org/dddml/aptosdemocontracts/specialization"

if [ ! -d "$target_dir" ]; then
  mkdir -p "$target_dir"
fi

old_keyword="suidemocontracts"
new_keyword="aptosdemocontracts"

for file in "${source_dir}"/*.java; do
  if [[ -f "$file" ]] && grep -q "$old_keyword" "$file"; then
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
