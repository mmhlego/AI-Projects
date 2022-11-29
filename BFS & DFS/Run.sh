#! /bin/bash

s=$1
t=$2

if [ -z $t ] || [ -z $s ]
then
    echo "Two Arguments needed"
    exit 1
fi

java Algorithm.java $s $t
java Algorithm.java $s $t > result.txt

python3 Algorithm.py $s $t
python3 Algorithm.py $s $t >> result.txt

echo Answers generated \(result.txt\)
