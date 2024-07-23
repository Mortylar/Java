

J_SOURCE=$(wildcard *.java)
PACKAGE=statistic
PACKAGE_SOURCE=$(wildcard $(PACKAGE)/*.java)
TARGET=Program

build:
	javac $(J_SOURCE) $(PACKAGE_SOURCE)

run: build
	java $(TARGET)

clean:
	-rm *.class
	-rm $(PACKAGE)/*.class
