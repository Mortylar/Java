

J_SOURCE=$(wildcard *.java)
PACKAGE_SOURCE=$(wildcard statistic/*.java cursor/*.java)
TARGET=Program

build:
	javac $(J_SOURCE) $(PACKAGE_SOURCE)

run: build
	java $(TARGET)

clean:
	-rm *.class
	-rm cursor/*.class statistic/*.class
