
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")
TARGET=target/Chat.jar
CLANG_CONFIG=../.clang-format

build:
	mvn clean package
 
run: build
	java -jar $(TARGET)


style:
	clang-format -style=file:$(CLANG_CONFIG) -n $(SOURCES)

fix_style:
	clang-format -style=file:$(CLANG_CONFIG) -i $(SOURCES)

open:
	vim $(shell find . -name $(file_name))

add: fix_style
	git add $(shell find . -name "*.java")

clean:
	rm -rf $(CLASSES) out/
