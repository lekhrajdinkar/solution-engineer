#!/bin/bash
my_function() {
  echo "Hello, $1!"
}
my_function World  # Output: Hello, World!

add_numbers() {
  echo $(( $1 + $2 ))
}
result=$(add_numbers 5 3)
echo $result  # Output: 8
