#!/bin/bash

source_dir="../sui-java-service/suiDemoContracts-service-rest/src/main/resources"
target_dir="./roochDemoContracts-service-rest/src/main/resources"

if [ ! -d "$target_dir" ]; then
  mkdir -p "$target_dir"
fi

old_keyword="suidemocontracts"
new_keyword="roochdemocontracts"

for file in "${source_dir}"/*.yml; do
  if [[ -f "$file" ]]; then # && grep -q "$old_keyword" "$file"
    cp "$file" "${target_dir}/$(basename "$file")"
    sed -i "" "s/$old_keyword/$new_keyword/g" "${target_dir}/$(basename "$file")"
  fi
done
