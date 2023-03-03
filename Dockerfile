FROM openjdk:18-jdk-alpine AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM openjdk:18-jdk-alpine AS layers
RUN jlink --compress=2 --no-header-files --no-man-pages \
           --strip-debug --add-modules $(jdeps --print-module-deps /app/build/libs/*.jar) \
           --output /jlinked


FROM alpine:3.14
RUN apk --no-cache add curl
ENV LANG C.UTF-8
ENV PATH "$PATH:/jlinked/bin"
COPY --from=layers /jlinked /jlinked
COPY --from=builder /app/build/libs/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
