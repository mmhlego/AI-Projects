@ECHO OFF
g++ main.cpp -o out.exe
out.exe > out.txt
del out.exe
