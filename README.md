# Tra(nnosaurus) project

The project is a working demo of Web application which allows monitoring HTTP requests, including headers, parameters and cookies, coming to certain URL generated
by the application.

First of all, you obtain a *SID* (a.k.a. session ID) generated from the index page, or you may simply open browser page */tyra?sid=<ANY SID YOU WISH>* on the Web
server and see all incoming *POST* or *GET* requests coming to URL */hook?sid=<YOUR SID>*, i.e. for the same session ID.

This project is hosted on [GitHub](https://github.com/), can be deployed locally, to Docker containers, or to [Heroku](https://www.heroku.com/).

## Building the project

To build the project run

```sh
$ gradle clean build
```

Gradle will compile sources, process resources, create *tyra-<VERSION>.jar* file in *build/libs* directory, run unit, integration and mutation tests.

Generated *JAR* artifact has manifest entries like these:

    Manifest-Version: 1.0
    Implementation-Vendor: Alexander Burchak
    Implementation-Title: Tyra
    Implementation-Version: 1.0.0-SNAPSHOT
    Main-Class: org.springframework.boot.loader.JarLauncher
    Spring-Boot-Classes: BOOT-INF/classes/
    Spring-Boot-Lib: BOOT-INF/lib/
    Spring-Boot-Version: 1.4.0.RELEASE
    Start-Class: TyraApplication

If you want to make *ZIP* distribution, run this command instead:

```sh
$ gradle clean zip
```

It will create *tyra-<VERSION>.zip* file in *build/distributions* directory.

To generate *jacoco* test coverage reports, execute

```sh
$ gradle jacocoTestReport
```

then open in browser *build/jacoco/html/index.html* page.

## Deploying the project

### Deployment to local host

Unpackage *build/distributions/tyra-<VERSION>.zip* archive to *$TYRA_HOME* directory.

The application should be run using *tyra.sh* script located in *bin* directory:

```sh
$ $TYRA/bin/tyra.sh
```

The application is available on local port HTTP *8081*.

### Deployment to Docker containers

To use this approach, you need to have Docker version *1.11.0* and Docker Compose version *1.7.0* or later.

In the project root directory, execute

```sh
$ docker-compose build
```

This will download needed Docker images, from [Docker Hub](https://hub.docker.com/), will create new image for Tyra application.

Next, run the containers with

```sh
$ docker-compose up
```

The application is available on local port HTTP *8081*.

When containers are not needed anymore, execute

```sh
$ docker-compose down
```

### Deployment to Heroku

```sh
$ heroku create
$ heroku config:set GRADLE_TASK="zip -x test"
$ heroku git:remote -a radiant-peak-78397
```

```sh
$ git commit
$ git push origin heroku
```

```sh
heroku open
```

## Monitoring the application

### Watching log files

Tyra application creates log file *tyra.log* in *$TYRA/logs* directory. The file is rolled over on daily basis.

### Using management console

Tyra application management tools are available under */mgmt* Web context: [http://localhost:8081/mgmt]().
