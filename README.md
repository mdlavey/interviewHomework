# interviewHomework
Assumptions:

* Running on a Unix based system
* has Maven 3.3.9 installed or a compatible version
* has Java 1.8.0_131 installed or a compatible version
* JAVA_HOME is set
* port 8080 not in use.

Issues encountered:
* Started using spring-boot-starter-parent version 1.5.2.RELEASE which had a defect related to SockJS where messages would be dropped causing the communication to be slow.  Once I updated to version 1.5.4.RELEASE the issue was fixed and I could definitely tell a difference in the communication speed.