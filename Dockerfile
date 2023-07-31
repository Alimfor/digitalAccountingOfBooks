FROM tomcat:10.1.9

WORKDIR /usr/local/tomcat/webapps

COPY /target/FProj.war /usr/local/tomcat/webapps

CMD ["catalina.sh", "run"]