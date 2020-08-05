FROM tomcat:7.0.99-jdk8-openjdk
#FROM tomcat:8.5.38-jre8-alpine
ARG WAR_FILE

# Enable JPDA
ENV JPDA_ADDRESS="0.0.0.0:8000"
ENV JPDA_TRANSPORT="dt_socket"

# Prepair tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
ADD target/mytomcat.war /usr/local/tomcat/webapps/
CMD chmod +x /usr/local/tomcat/bin/catalina.sh

EXPOSE 8080 

CMD ["catalina.sh", "run"]