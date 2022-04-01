
# 🌲 Treenity (2022 Google Solution Challenge)

![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white)
![Spring-Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=Spring-Boot&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCC00?style=flat-square&logo=Firebase&logoColor=white&textCol...)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white)
![Google-Cloud](https://img.shields.io/badge/Google_Cloud-4285F4?style=flat-square&logo=Google-Cloud&logoColor=white)

</br>

Client Repo Link (You can see our demo video and usage)

<https://github.com/Setana-GDSCUOS/treenity-android>

</br>

This is an application that provides an AR-based social networking/exercising experience.

## 📖 Explanation

 We propose a way to reduce your carbon footprint through fun-filled walking.
As users walk while using the app, the app records their steps.
The number of steps recorded is converted into points for buying seeds and buckets of water,
Users can plant seeds and water trees in places they frequently visit through AR technology and build up their efforts toward nature and health.
Not just this! Users can share their hard work with other users and also interact with the world directly from their Android smartphones!

By walking and watering, you will be filled with the power of steady exercise.

👣Let's just plant your walks!👣


## 💎 Main Features

- HTTP REST API Server Configured With Spring Boot Application
- Google social login With Spring Secuirty and Firebase Authentication
- Build with Docker and run containers at Google Cloud VM
- Mysql Spatial Index for storing/fetching coordinates from database


## 📐 Deployment/Diagram

![image](https://user-images.githubusercontent.com/50660684/160948402-a88b532a-0075-4532-8865-aa731c4a8f00.png)


- Build with Gradle & Docker build system.
- Push/Pull from Docker Hub and run containers in Google Cloud Copute Engine.  
- Use Google Cloud Load Balancer for Adapting Https Protocol.
- Authenticate User with firebase for `Sign in with Google`.


## 🖥️ Build Environment

This project uses Gradle & Docker build system.  
To build and run this project, first build `.jar` with Gradle, and then build docker image with Docker within bash shell.

Using bash shell file in `/docker`, you could push to/pull from Docker Hub.

### gradle build (Local, Windows)

```shell
$ gradlew build
```

### docker build & push (Local, WSL)

```shell
$ bash docker/push.sh YOUR_DOCKER_HUB_ID
```

### docker pull & run (Prod, Linux)

```shell
$ bash docker/pull.sh YOUR_DOCKER_HUB_ID
```

## 📃 API Specification

This project utilize OpenAPI Specification 3.0 and Swagger UI for communication with client.

Below is our project's HTTP REST API server swagger UI endpoint.

<https://gsc.oasisfores.com/swagger-ui/>

<img src="https://user-images.githubusercontent.com/50660684/160949553-e29c1c25-013c-449d-b7be-f8d99de3e39f.png" width=700 />

## 🏛️ Depedency Used

- Spring Boot
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`

- Database
  - `querydsl-jpa`
  - `hibernate-spatial`

- Swagger
  - `springfox-boot-starter`

#### Contributors

[Changgu Kang](https://github.com/rxdcxdrnine)|[Jongkyu Seok](https://github.com/SHEELE41)|[Jaeuk Im](https://github.com/iju1633)|[Eunwoo Tae](https://github.com/kstew16)
|:---:|:---:|:---:|:---:|
BACKEND|ANDROID|ANDROID|ANDROID|
