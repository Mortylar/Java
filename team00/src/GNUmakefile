
SOURCES=$(shell find . -name "*.java")
CLASSES=$(shell find . -name "*.class")
CLANG_CONFIG=.clang-format

build:
	javac -d out $(SOURCES)
 
run: build
	java -cp out/ Main

style:
	clang-format -style=file:$(CLANG_CONFIG) -n $(SOURCES)

fix_style:
	clang-format -style=file:$(CLANG_CONFIG) -i $(SOURCES)


clean:
	rm $(CLASSES)
