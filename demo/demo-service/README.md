# demo-service

打包命令

mvn clean install -U -Plocal  -D“maven.test.skip”=true -D“scope.type”=compile -Drepo=SNAPSHOT
mvn clean install -U -Ptest -Dmaven.test.skip=true -Dscope.type=compile -Drepo=SNAPSHOT
mvn clean install -U -Ppro  -Dmaven.test.skip=true -Dscope.type=provided -Drepo=RELEASE
