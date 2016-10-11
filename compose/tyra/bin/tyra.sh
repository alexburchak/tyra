#!/bin/sh

export TYRA_HOME=`dirname $0`/..
CLASSPATH=$TYRA_HOME/conf
CLASSPATH=$CLASSPATH:`find $TYRA_HOME/lib/ -name tyra*.jar -exec echo -n ':'{} \;`

java -Djava.security.egd=file:/dev/./urandom -classpath $CLASSPATH $@ org.springframework.boot.loader.JarLauncher
