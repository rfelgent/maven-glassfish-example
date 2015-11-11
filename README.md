Example Project for demonstrating a java EE 7 webapp development within a maven structure

Technical Features
===
- in-memory GF server (usage of maven plugin "maven-embedded-glassfish-plugin")
- in-memory h2 database (with automatic database creation and data initialisation)
- custom GF login realm/module
- JSF based login combined with Container-Auth (no "j_security_check", but "login.xhtml")
- JPA 2.1 (used by custom login module for user authorization)
- JSF 2.2
- bean validation

Features
===
- login
- register (newly registered user can login, too!)

Getting started
===

- mvn clean install
- cd ./webapp/ && mvn embedded-glassfish:run
- open "http://localhost:8181/demo" via a browser
- login via "max:123456" (normal user) or "admin:admin" (admin user)
