#/bin/sh

javac -d . src/com/rpecebou/**/*.java
jar -cvfe Project1.jar com/rpecebou/main/Main com/rpecebou/**/*.class
rm -r com
