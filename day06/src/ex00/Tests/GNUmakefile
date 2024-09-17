
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")
COVERAGE_FILES=$(shell find . -name "index.html")
TARGET=target/Chat.jar
CLANG_CONFIG=../.clang-format

build:
	mvn clean package
 
run: build
	java -jar $(TARGET)

test:
	mvn test

coverage: test 
	open $(COVERAGE_FILES)


style:
	clang-format -style=file:$(CLANG_CONFIG) -n $(SOURCES)

fix_style:
	clang-format -style=file:$(CLANG_CONFIG) -i $(SOURCES)

open:
	vim $(shell find . -name $(file_name))

open_all_sources:
	vim $(shell find . -name "*.java")

add: fix_style
	git add $(shell find . -name "*.java")

clean:
	mvn clean
