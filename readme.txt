Readme for MRCP4J v${pom.version}
======================

Overview
--------

  MRCP4J provides a Java API that encapsulates the MRCPv2 protocol and can be used to implement MRCPv2 clients and/or servers.


Prerequisites
-------------

  MRCP4J requires JDK 5.0 or higher which can be downloaded here: http://java.sun.com/j2se/1.5.0/download.jsp


Dependencies
------------

  MRCP4J has the following dependencies:

+--------------------+-------------+--------------------------------------------------+
| Project            | Version     |  Download URL                                    |
+--------------------+-------------+--------------------------------------------------+
| Commons Logging    | 1.1         | http://jakarta.apache.org/commons/logging/       |
+--------------------+-------------+--------------------------------------------------+
| MINA               | 0.8.0       | http://directory.apache.org/subprojects/network/ |
+--------------------+-------------+--------------------------------------------------+
| SLF4J              | 1.0-beta9   | http://www.slf4j.org/                            |
+--------------------+-------------+--------------------------------------------------+


  Distribution jars for the above listed dependencies need to be included in your classpath in order to use MRCP4J.  They are available for download from the provided URLs and are also included in the MRCP4J binary distribution.


Installation
------------

  To install MRCP4J extract the mrcp4j-<version>.jar from the binary distribution archive and add it to your application's classpath along with the above listed dependencies.



Change Log for MRCP4J
=====================

MRCP4J v0.2
-----------
* Fix potential deadlock in event queue timeout scenario.
* Remove redundant signal for event queue completion which was causing MrcpSession.postEvent() to hang.
* Use commons-logging for debug output instead of System.out.println() statements.
* Use commons-logging for MRCP session logging instead of MINA session log.
* Update MINA dependency from version 0.7.2 to version 0.8.0.
* Add value factories for some of the more commonly used MRCP headers.
* Preserve the order of MRCP headers to be the same as the order they are added in.
* Calculate and set message-length during encoding of MRCP messages.
* Terminate message content with CRLF and include in calculation of content-length header value.


MRCP4J v0.1
-----------
No prior release to compare with.



Further Information
===================

  For more information please see the MRCP4J Project Home at http://mrcp4j.sourceforge.net.





+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
+ Copyright (C) 2005-2006 SpeechForge. All Rights Reserved. +
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++