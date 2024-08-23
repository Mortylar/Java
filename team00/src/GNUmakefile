
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")

build:
	javac -d out $(SOURCES)
 
run: build
	java -cp out/ Main

clean:
	rm $(CLASSES)
