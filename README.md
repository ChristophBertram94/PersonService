# PersonService
CMG REST-Aufgabe

Fragen:

1. Authentifikation
Ich habe der Anwendung bereits eine einfache Authentifikation in Form eines Logins hinzugefügt. Hinweis: Das Passwort wird nicht verschlüsselt!

Normalerweise würde ich das Passwort natürlich hashen, angesicht des Zeitrahmens habe ich darauf aber verzichtet.

2. Performance Messungen:
Messen könnte man die Performance indem man die Zeit misst die einzelnen CRUD Funktionen brauchen um durchgeführt zu werden.

3. Problem
Die Anwendung sollte im optimal Fall so erstellt werden, dass der Nutzer unter allen Umständen darüber informiert wird ob die angelegte Person persistiert wurde oder nicht.

Falls dies nicht möglich ist, sollte der Nutzer darauf hingewiesen werden im Zweifel zu versuchen, die Person erneut anzulegen. Existiert diese bereits, wurde die Person erfolgreich persistiert.

4. ERM Siehe ERM_PersonService


Technologien:

Java 1.8

Maven 3.6.1

3.8.1.Final

Wildfly 17.0.1.Final

Requirements: Apache-Maven-3.6.1 Java 1.8 Wildfly 17.0.1.Final - Java EE Full & Web Distribution https://wildfly.org/downloads/

JAVA_HOME und MAVEN_HOME müssen als Umgebungsvariablen gesetzt sein

Setup:

PersonService.war wildfly-17.0.1.Final StartPersonService.bat

In das selbe Verzeichnis legen. Die StartPersonService.bat kopiert das PersonService.war in den JBoss und startet automatisch den Server. Sobald der Server gestartet wurde kann die Applikation über

http://localhost:8080/PersonService/Login.html

aufgerufen werden.

Anlegen eines neuen Benutzers:

Ich habe eine einfache Authentification hinzugefügt. Da zu beginn noch kein Account vorhanden ist muss zu erst ein neuer Benutzer registriert werden.

Eine erfolgreiche Registrierung leitet den Benutzer automatisch auf die Loginseite weiter. Dort kann sich mit den zuvor registrierten Daten eingeloggten werden.

Auf der folgenden Seite könnten die Benutzerdaten angepasst werden.

Anmerkung: 

Der Username agiert hierbei als Benutzer ID und kann daher nach der Registrierung nicht mehr verändert werden. Falls die Felder Name oder E-Mail verändert werden und der neue Wert nicht den festgelegten Kritieren entspricht werden diese nicht verändert. Die alten Werte werden beibehalten

Speicherung der Daten: Die Benutzerdaten werden lediglich Temporär gespeichert. Solange wie der Server läuft kann auf alle angelegten Nutzer zugegriffen werden. Startet der Server neu, sind alle Daten verloren!

Tests: 

Die automatischen Tests belaufen sich auf die Erstellung eines neuen Benutzers sowie auf das Testen der CRUD Funktionen. Da die CRUD Funktionen aufeinander beruhen, müssen diese in einer chronologischen Reihenfolge abgearbeitet werden und können nicht einzeln getestet werden.

Frontend: 

Bei den Frontend Seiten habe ich ein vorhandenes HTML Template verwendet und lediglich für meine Anforderungen angepasst bzw. erweitert.
