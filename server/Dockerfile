# Этап сборки (build stage)
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Копируем всё, что нужно для сборки
COPY pom.xml ./
COPY src ./src
COPY .mvn .mvn
COPY mvnw ./

# Создаём папку и копируем туда базу данных
RUN mkdir -p ./data
COPY data/kolyma.db ./data/kolyma.db

# Делаем скрипт Maven исполняемым
RUN chmod +x mvnw

# Собираем приложение без запуска тестов
RUN ./mvnw clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Копируем собранный .jar файл
COPY --from=build /app/target/*.jar app.jar

# Копируем базу данных в runtime-окружение
COPY --from=build /app/data ./data

# Открываем порт
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]