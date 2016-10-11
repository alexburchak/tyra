FROM ubuntu:16.04

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk-headless && \
    apt-get install -y iproute2

COPY bin/tyra.sh /usr/lib/tyra/bin/tyra.sh
RUN chmod a+rx /usr/lib/tyra/bin/tyra.sh

RUN mkdir /usr/lib/tyra/logs

COPY conf/application.yml /usr/lib/tyra/conf/application.yml

COPY lib/tyra.jar /usr/lib/tyra/lib/tyra.jar

COPY docker-entrypoint.sh /

ENTRYPOINT /docker-entrypoint.sh

EXPOSE 8081
