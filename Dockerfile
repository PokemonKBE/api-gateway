FROM maven:3.8-openjdk-17
WORKDIR /app
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
