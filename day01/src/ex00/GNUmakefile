

J_SOURCE=$(wildcard *.java)
PACKAGE_SOURCE=$(wildcard user/*.java transaction/*.java)
TARGET=Program

build:
	javac $(J_SOURCE) $(PACKAGE_SOURCE)

run: build
	java $(TARGET)

clean:
	-rm *.class
	-rm user/*.class transaction/*.class
