
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")
CLANG_CONFIG=.clang-format

build:
	javac -d out $(SOURCES)
 
run: build
	java -cp out/ game.app.Main


test:
	mvn clean package
	java -jar target/Game.jar --enemiesCount=10 --wallsCount=10 --size=10 --profile=production


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
