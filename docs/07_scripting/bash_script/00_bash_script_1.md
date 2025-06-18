# reference
- https://www.youtube.com/watch?v=yLQnpmkNFmk&list=PLBf0hzazHTGMJzHon4YXGscxUvsFpxrZT&index=3&ab_channel=HackerSploit
- All topic : https://chatgpt.com/c/674528ff-dbc8-800d-9237-9e757230df84

---
# bash
- #!/bin/{**interpretator-name**}
    - bash (linux, WSL)
    - zsh (mac)
    - python (all)
- **$( )** : command substitution
    - is used to execute a command and replace it with its output. 
    - preferred over backticks. eg: ``` current_date=`date` ```
    ```
        - env_value=$(echo $HOME)   
        - current_user=$(whoami)
        - current_date=$(date) 
        - for file in $(ls /path/to/directory); do ... done
        - file_size=$(stat -c%s "/path/to/file")
        - result=$(($((5 * 10)) + 2))
        - $(seq 1 3)
        - trimmed=$(echo "$str" | xargs) - trim
    ```
    - $() is for running and capturing the output of commands.
    - **$(( ))** is for performing arithmetic calculations.    

## A. variable
- https://chatgpt.com/c/6744f7b6-024c-800d-8f1e-cd0fab2299c1
- variables are untyped, meaning they do not have a specific data type
- All variables are treated as strings, 
- but you can perform operations that treat them as numbers when needed.
- **Array**
    - FRUITS=("F1" "F2" "F3")
- **Associated Array** like Map
    - FRUIT_COLOR("F1")="red"

## B. Conditional
- https://chatgpt.com/c/674504d4-e730-800d-b831-564a15b4b58e
- <, > are redirection operators, dont use for comparison.
- **help test**
    - run on terminal and check conditional operator list.
    - some eg:
```
# Conditional Operators
##  File Conditions
-e file : File exists.
-d file : File is a directory.
-f file : File is a regular file.

## String Conditions
[ "$a" == "$b" ] : Strings are equal.
[ -z "$str" ] : String is empty.

## Arithmetic Comparisons
[ $a -eq $b ] : Equal.
[ $a -lt $b ] : Less than.
gt, ne, ge, le
```

- **[[ ... ]]  vs	[ ... ]**
  - new, prefer it in bash.
  - Supports `wildcards`, e.g., [[ $var == a* ]]
  - Supports =~ for **regex matching**
  - [[ ... && ... ]] is valid	vs Requires if [ ... ] && [ ... ]

## C .loop
- while
- until
- break and continue
- for (;;)
- for in array

## D .String manipulation
- https://chatgpt.com/c/67454b39-b8f4-800d-8aaf-0eb72bcc568d
### with awk 
- **programming capabilities**
- pattern scanning and text processing. 
- You need to process **structured** data (like CSV or tabular data). 
- Perform calculations, conditional checks, or formatting output. 
- Extract specific fields or records.

### with sed ( stream editor) 
- You need quick `search-and-replace` 
- text editing in `files` or `streams`.
- Work with line-by-line text transformations.

## F. file

