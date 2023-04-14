# Hello World with Payara Micro and Platform.sh

![Hello World Platform.sh and Payara](https://pbs.twimg.com/media/ES1yxFiWoAQnjIg?format=jpg&name=small)

In this section we will cover how to create your first REST project with Payara Micro, and then move that project to Platform.sh  using the Maven Archetype, which  is a Maven project templating toolkit. An archetype is defined as an original pattern or model from which all other things of the same kind are made. 

```shell
mvn archetype:generate -DarchetypeGroupId=sh.platform.archetype   -DarchetypeArtifactId=payara  -DarchetypeVersion=0.0.1  -DgroupId=my.company  -DartifactId=hello -Dversion=0.0.1 -DinteractiveMode=false
```


## How to Execute

```shell
mvn clean package payara-micro:bundle
java -jar -Xmx512m target/microprofile-microbundle.jar --port 9999 --autoBindHttp --sslPort 9998 --autoBindSsl

keytool -export -alias server-alias -storepass changeit -file server.cer -keystore keystore.jks
keytool -import -alias example -keystore  "C:\Program Files\Java\jdk ..\jre\lib\security\cacerts" -file example.cer
```
java-home/bin/keytool -genkey -alias server-alias -keyalg RSA -keypass changeit -storepass changeit -keystore keystore.jks

-export -alias server-alias -storepass changeit -file server.cer -keystore keystore.jks

-import -v -trustcacerts -alias server-alias -file server.cer -keystore cacerts.jks -keypass changeit -storepass changeit



java-home/bin/keytool -genkey -alias server-alias -keyalg RSA -keypass changeit -storepass changeit -keystore keystore.jks