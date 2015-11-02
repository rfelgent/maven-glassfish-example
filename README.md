Example Project for demonstrating a java ee 7 webapp development within a maven structure

Technical Features
===
- in-memory GF server (usage of maven plugin "maven-embedded-glassfish-plugin")
- in-memory h2 database (with automatic database creation and data initialisation)
- custom GF login realm/module
- JSF based login combined with Container-Auth (no "j_security_check", but "login.xml")
- JPA 2.1 (used by custom login module for user authorization)
- JSF 2.2

Start
===
Perform the those preparation steps:

- mvn clean install
- cd ./webapp/ && mvn embedded-glassfish:run
- go to http://localhost:8181/demo (valid credentials are "max:123456")
