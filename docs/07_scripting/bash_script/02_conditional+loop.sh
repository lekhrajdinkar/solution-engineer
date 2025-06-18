#!/bin/bash
#!/bin/zsh
#!/bin/python

echo "------- #1 if elif else fi -------"
num=$1
num=${num:-7}
if [ $num -lt 5 ]; then  # square bracket
    echo "Number is less than 5"
elif [ $num -eq 7 ]; then
    echo "Number is equal to 7"
else
    echo "Number is greater than 5"
fi

name=$2
name=${name:-lekhraj}
# keep space around =, then its becomes equal operator. 
# == also works
# Single square bracker
if [ $name == "lekhraj" ] || [ $name = "lekhraj" ] ; then
    echo "Hello lekhraj"
else
    echo "you are NOT lekhraj"
fi

# double square bracker - supports wildcard, regex with =~, ||, &&, !
if [[ $name = l*  || $name == m* ]] ; then
    echo "Hello lekhraj"
else
    echo "you are NOT lekhraj"
fi

echo "------- #2 case  -------"
case $name in
    "lekhraj")
        echo "hello lekhraj"
        ;;
    "lekhu")
        echo "hello lekhu"
        ;;
    *)
        echo "who are you $name ?"
        ;;
esac

case $1 in
  start) echo "Starting...";;
  stop) echo "Stopping...";;
  *) echo "Invalid option";;
esac

echo "------- #3 for loop  -------"
for num in $(seq 1 3); do echo "Number: $num" ; done

for item in apple banana cherry; do
    echo "Fruit: $item"
done

# Array
arr=("array-item-1" "array-item-2" "array-item-3")
arr_size=${#arr[@]}

count=0
for item in "${arr[@]}"; do 
    count=$(( count + 1 ));
    echo "item_${count} : $item" ; 
done


for ((i = 1; i <= arr_size; i++)); do 
    echo "C-style loop, index is $i" 
done

# directory file
for item in $(ls .); do
    echo "File in curr dir : $item"
done

# read file content
if [ -f ./items.txt ] ; then
    for item in $(cat ./items.txt); do
        echo "item : $item"
    done
else
    echo "item file not present"
fi












