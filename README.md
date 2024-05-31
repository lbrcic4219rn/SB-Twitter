# SB-Twitter

This is a Spring Boot application that provides a Twitter-like service. It allows users to create, delete and query tweets based on hashtags and usernames.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Maven
- Docker

### Installing

1. Clone the repository
```bash
git clone https://github.com/lbrcic4219rn/SB-Twitter.git
```
2. Navigate into the cloned repository
```bash
cd SB-Twitter
```
3. Install the dependencies
```bash
mvn install
```
4. Run docker container
```bash
docker compose up -d
```
6. Run the application
```bash
mvn spring-boot:run
```

## Built With

- [Java](https://www.java.com) - The programming language used.
- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used.
- [Maven](https://maven.apache.org/) - Dependency Management.
