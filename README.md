# Realtime Weather Analytics

## Project Structure

It is recommended to open separate intelliJ projects for the 
`producer` and `consumer` sub-projects , as they use different build systems.
Additionally, it is recommended that you set your jdk version through IntelliJ project structure

### [Kafka](./kafka)
A docker file. To run:
`docker compose up -d`

### [Producer](./producer)
A spring boot producer/gradle project
- requires java version 17

### [Consumer](./consumer)
A spark job/sbt project
- required java version 8
  - for installed java version 8 on m1, see [this](https://erwinschleier.medium.com/install-jdk-8-and-setup-java-home-on-mac-m1-1b0ea243b05a) link

### [UI](./ui)
UI that displays the weather data
- To run:
  - `npm run server` to start the server
  - `npm start` to start the ui