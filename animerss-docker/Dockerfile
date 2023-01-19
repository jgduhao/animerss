FROM openjdk:8-alpine
RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone && apk del tzdata && mkdir -p /animerss
WORKDIR /animerss
COPY . .
VOLUME /animerss/db
EXPOSE 8080
CMD java -jar animerss.jar
