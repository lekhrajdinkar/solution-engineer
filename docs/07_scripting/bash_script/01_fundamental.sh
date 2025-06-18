#!/bin/bash
#!/bin/zsh
#!/bin/python

echo "------- #1 variable: Dont put space before/after = -------"
NAME="LEKHRAJ Dinkar"
NAME="LEKHRAJ Dinkar2"
Name="LEKHRAJ Dinkar3"
name="LEKHRAJ Dinkar4"
NAME = "LEKHRAJ Dinkar" #invalid
sport="foot"
echo "Hello $NAME $name ! how are you. still playing $sport ball"
echo "Hello $Name ! how are you. still playing ${sport}ball"

#05_name="LEKHRAJ Dinkar 5" #invalid
echo "hello $05_name"

echo "------- #2 special variable -------"
echo "script name :  $0 "
echo "argumnet :  $1 $2 $3"
echo "argumnet count :  $#"
echo "exit status :  $?"
echo "pid :  $$"

echo "------- #3 env variable -------"
export ENV_NAME="LEKHRAJ"
echo $ENV_NAME
unset NAME

echo "------- #4 read -------"
read -p "Enter your first and last name: " FIRST_NAME LAST_NAME
FIRST_NAME=${FIRST_NAME:-Lekhraj} # default value
LAST_NAME=${LAST_NAME:-Dinkar} # default value
echo "Hello, $FIRST_NAME-$LAST_NAME "

read -s -p "Enter your password: " PASSWORD
PASSWORD=${PASSWORD:-12345} # default value
echo "Password saved securely $PASSWORD"

echo "------- #5 no-op command : << -------"
: << 'COMMENT'
This is a multi-line comment.
It can span multiple lines.
COMMENT

: << 'SKIP_ME'
This is a multi-line comment.
It can span multiple lines.
SKIP_ME

echo " multi line comment trick done."

echo "------- #6 Arithmetic  $ (( ...  )) -------"
NUM1=10
NUM2=20

SUM1=$(expr $NUM1 + $NUM2)  # Arithmetic with expr - old way
SUM2=$((NUM1 + NUM2))  # Arithmetic with $(( )) - old
 # Arithmetic with let - new way
let SUM3_1=NUM1+NUM2 
let SUM3_2 = NUM1 + NUM2

echo "Sum: $SUM1, $SUM2, $SUM3_1, $SUM3_2"

# status of previous command
if [[ $? -eq 0 ]]; then
  echo "Success"
else
  echo "Failure"
fi



