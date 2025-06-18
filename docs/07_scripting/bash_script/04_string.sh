#!/bin/bash

str="Hello, World World world "
echo ${str:7:5}
echo ${#str}
echo ${str/World/Bash} #replace first occurance only
echo ${str//World/Bash} #replace all occurance only

# upper and lower case - error.
# echo ${str^^}  # Output: "HELLO, WORLD!"
# echo ${str,,}  # Output: "hello, world!"

# split
str="apple,banana,cherry"
IFS=',' read -ra arr <<< "$str"  # Split by comma
echo ${arr[0]}  # Output: apple

# concate
str1="Hello"
str2="World"
echo "$str1, $str2!"

echo "$str" | xargs # trim

echo "$str" | rev # reverse

################################
# awk
################################
echo "============= awk examples ==========="
echo "apple banana cherry" | awk '{print $2}' # delimiter - space(default)
# Output: banana
echo "apple,banana,cherry" | awk -F ',' '{print $3}' # delimiter - ,
# Output: cherry
echo "apple banana cherry" | awk '{gsub("banana", "grape"); print}'
# Output: apple grape cherry
echo "line1 - Hello lekhraj how are you\nline-2\line-3" | awk '/lekhraj/{print "new line - hello lekhraj"}' # look for line having pattern - lekhraj
# Output: new line - hello lekhraj
echo "hello world" | awk '{print toupper($0)}'
# Output: HELLO WORLD
echo "HELLO WORLD" | awk '{print tolower($0)}'
# Output: hello world
echo "Hello" | awk '{print length($0)}'
# Output: 5
echo -e "apple\nbanana\ncherry" | awk '/banana/'
# Output: banana

################################
# sed
################################
# Find and Replace (Basic)
echo "apple banana cherry" | sed 's/banana/grape/'

# Replace All Occurrences
echo "banana banana cherry" | sed 's/banana/grape/g'

# Delete Lines Containing a String
echo -e "apple\nbanana\ncherry" | sed '/banana/d'

# Insert a Line Before a Match
echo -e "apple\nbanana\ncherry" | sed '/banana/i\orange'
# Output:
# apple
# orange
# banana
# cherry


# Insert a Line After a Match
echo -e "apple\nbanana\ncherry" | sed '/banana/a\orange'
# Output:
# apple
# banana
# orange
# cherry

# Print Lines Matching a Pattern
echo -e "apple\nbanana\ncherry" | sed -n '/banana/p'
# Output: banana

# complex substitution
echo "my name is lekhraj dinkar" | sed 's/\(.*\) is.*/\1/'
# output : my name ????








