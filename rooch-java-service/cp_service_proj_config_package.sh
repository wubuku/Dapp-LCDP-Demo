#!/bin/bash

source_dir="../sui-java-service/suiDemoContracts-service/src/main/java/org/dddml/suidemocontracts/config"
target_dir="./roochDemoContracts-service/src/main/java/org/dddml/roochdemocontracts/config"

if [ ! -d "$target_dir" ]; then
  mkdir -p "$target_dir"
fi

old_keyword="suidemocontracts"
new_keyword="roochdemocontracts"

# Iterate over all java files in source_dir or its subdirectories
find "$source_dir" -name "*.java" | while read file; do
  # Check if the file contains old keywords
  if grep -q "$old_keyword" "$file"; then
    relative_path=${file#$source_dir/}
    target_file="$target_dir/$relative_path"
    mkdir -p "$(dirname "$target_file")"
    cp "$file" "$target_file"
    sed -i "" "s/$old_keyword/$new_keyword/g" "$target_file"
  fi
done
