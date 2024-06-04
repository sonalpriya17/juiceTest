FROM eclipse-temurin:latest AS base

WORKDIR /app

FROM base AS gradle

COPY . /app

RUN ./gradlew assemble

FROM gradle

CMD ["./juiceTestrun.sh"]