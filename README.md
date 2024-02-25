# Java EE practice project
Project is based on *Maven*, thus import project to IntelliJ IDEA by:
* File -> Open... -> pick `pom.xml` file.

## Application Server configuration

### WildFly

1. Download ZIP of WildFly 26 "Jakarta EE 8 Full & Web Distribution" from: http://wildfly.org/downloads/
2. Unzip
3. In IntelliJ IDEA: register "JBoss Server" -> local:
    * Press "Fix", choose "exploded war"
4. Run the server, project should start successfully.
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Java EE application for practice lessons</display-name>

  <context-param>
    <param-name>javax.faces.PROJECT_STAGE</param-name>
    <param-value>Development</param-value>
  </context-param>

  <servlet>
    <servlet-name>Faces Servlet</servlet-name>
    <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <servlet-mapping>
    <servlet-name>Faces Servlet</servlet-name>
    <url-pattern>*.xhtml</url-pattern>
  </servlet-mapping>

  <welcome-file-list>
    <welcome-file>index.xhtml</welcome-file>
  </welcome-file-list>

  <!-- Add the H2 console servlet -->
  <servlet>
    <servlet-name>H2ConsoleServlet</servlet-name>
    <servlet-class>org.h2.server.web.WebServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>H2ConsoleServlet</servlet-name>
    <url-pattern>/h2/*</url-pattern>
  </servlet-mapping>

  <!-- Add the H2 console filter -->


</web-app>