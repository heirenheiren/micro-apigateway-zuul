#基础镜像,这个路径是Jenkins服务器（或者容器）JDK存放路径
FROM /usr/local/openjdk-8
#作者
MAINTAINER demo <demo@qq.com>

VOLUME /tmp
#指定配置文件，以及jar包在服务器上的路径
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/var/jenkins_home/workspace/micro-apigateway-zuul/target/micro-apigateway-zuul-0.0.1-SNAPSHOT.jar"]
#暴露端口
EXPOSE 8084