
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")
CLASSPATH=-cp lib/HikariCP-4.0.3.jar:lib/slf4j-api-2.0.16.jar:lib/postgresql-42.7.3.jar:target/classes
CLANG_CONFIG=../.clang-format

build:
	javac -d out $(SOURCES)
 
run: build
	java -cp out/ game.app.Main

test:
	mvn clean compile
	java $(CLASSPATH) edu.school21.chat.app.Main


style:
	clang-format -style=file:$(CLANG_CONFIG) -n $(SOURCES)

fix_style:
	clang-format -style=file:$(CLANG_CONFIG) -i $(SOURCES)

a:
	echo $(CLANG_CONFIG)

open:
	vim $(shell find . -name $(file_name))

add: fix_style
	git add $(shell find . -name "*.java")

clean:
	rm -rf $(CLASSES) out/
