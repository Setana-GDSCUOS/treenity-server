
# 🌲 Treenity (2022 Google Solution Challenge)

![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white)
![Spring-Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=Spring-Boot&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCC00?style=flat-square&logo=Firebase&logoColor=white&textCol...)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white)
![Google-Cloud](https://img.shields.io/badge/Google_Cloud-4285F4?style=flat-square&logo=Google-Cloud&logoColor=white)

video coming soon!

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

## Client Usage

- Screenshots coming soon!

### 🔭 Future Visions

**<details><summary>Expand the possibility of interaction between users.</summary>** Even though society is one of the main components of our project, related features are currently reduced than initially thought. In the next step, interactions between users will take place with trees in between as they are now. For example, we can add features like users picking fruits from another user’s tree, or cutting another user’s tree. And if the obtained item could configure the tree to grow again from the item, not only the interaction but also the completeness of the app will increase.</details>

**<details><summary>Add motivating elements by creating a rewarding system.</summary>** Currently, points that users could get from the number of steps and trees that could be purchased with points are the only ways to motivate users to walk. Suppose that we have added an achievement system that gives rewards to users when they find new kinds of trees and fruits. Users will try to discover more trees for rewards, and this could be another motivation for them to walk more. The more users walk, the fewer carbon emissions will occur than using transportation. Additionally, the quality of individual health and the overall health of society will be improved.</details>

**<details><summary> Provide users rich experience</summary>** With a variety of types of seeds such as flowers or crops. It could make users’ experience richer when planting plants.</details>

#### Contributors

[Changgu Kang](https://github.com/rxdcxdrnine)|[Jongkyu Seok](https://github.com/SHEELE41)|[Jaeuk Im](https://github.com/iju1633)|[Eunwoo Tae](https://github.com/kstew16)
|:---:|:---:|:---:|:---:|
BACKEND|ANDROID|ANDROID|ANDROID|
