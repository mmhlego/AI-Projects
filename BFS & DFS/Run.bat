@ECHO OFF

set /P s="Start Node (1-10) : "
set /P t="Target Node (1-10) (enter 0 for full traversal) : "

java Algorithm.java %s% %t%
java Algorithm.java %s% %t% > result.txt

python Algorithm.py %s% %t%
python Algorithm.py %s% %t% >> result.txt

echo Answers generated (result.txt)

PAUSE