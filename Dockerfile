FROM maven:3.8.3-openjdk-17
WORKDIR /drivemeto-rp
COPY . .
RUN mvn clean package -DskipTests=false
CMD mvn spring-boot:run


