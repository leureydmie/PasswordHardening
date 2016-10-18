#/bin/sh

mkdir bin
javac -d bin src/com/rpecebou/**/*.java
java -cfe Project1.jar Main bin/com/rpecebou/main/Main.class
rm -r bin