# MRCP4j

The MRCPv2 protocol is designed to allow client devices to control media
processing resources, such as speech recognition engines. MRCP4J provides a 
Java API that encapsulates the MRCPv2 protocol and can be used to implement 
MRCP clients and/or servers.

## Requirements

- JAVA 8
- Gradle 7.3.1

## Include from from Maven

Configure maven to use Central from your Project Object Model (POM) file.You may do so by
adding the following to your pom.xml:

    <repositories>
      <repository>
        <id>central</id>
        <name>Maven Central</name>
        <layout>default</layout>
        <url>https://repo1.maven.org/maven2</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
      </repository>
    </repositories>

Add mrcp4j as a dependecy to your pom.xml

    <dependency>
      <groupId>org.jvoicexml</groupId>
      <artifactId>org.mrcp4j</artifactId>
      <version>0.3</version>
      <type>module</type>
    </dependency>
    
## Include from Gradle

Add the Maven Central repository to your build.gradle

    repositories {
      mavenCentral()
    }

Add mrcp4j as a an implementation dependency to your build.gradle

    implementation 'org.jvoicexml:org.mrcp4j:0.3'
