
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")
CLANG_CONFIG=.clang-format

build:
	javac -d out $(SOURCES)
 
run: build
	java -cp out/ game.app.Main

style:
	clang-format -style=file:$(CLANG_CONFIG) -n $(SOURCES)

fix_style:
	clang-format -style=file:$(CLANG_CONFIG) -i $(SOURCES)

a:
	echo $(CLANG_CONFIG)

clean:
	rm -rf $(CLASSES) out/
