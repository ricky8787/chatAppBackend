# ====== 第一階段：用 Maven 把 Spring Boot 專案打包成 jar ======
FROM maven:3.9.9-eclipse-temurin-21 AS build

# 容器裡的工作目錄
WORKDIR /app

# 先只複製 pom.xml，讓 Maven 先下載依賴（cache 比較好用）
COPY pom.xml .

# 預先下載 dependencies（會快很多）
RUN mvn -B dependency:go-offline

# 再把 src 複製進來
COPY src ./src

# 打包，不跑測試的話可以加 -DskipTests
RUN mvn -B clean package -DskipTests


# ====== 第二階段：用精簡 JRE 執行 jar ======
FROM eclipse-temurin:21-jre-alpine

# 容器內工作目錄
WORKDIR /app

# 從 build 階段複製打好的 jar
# 如果你打包出來的檔名不是這個，改成自己的
COPY --from=build /app/target/*.jar app.jar

# 如果你的 Spring Boot 預設是 8080，就 expose 8080
EXPOSE 8080

# 如果要指定 profile，可以在這邊改
# 例如：["java","-jar","app.jar","--spring.profiles.active=prod"]
ENTRYPOINT ["java","-jar","app.jar"]
