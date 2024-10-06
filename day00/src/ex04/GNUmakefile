

J_SOURCE=$(wildcard *.java)
PACKAGE_SOURCE=$(wildcard statistic/*.java cursor/*.java)
TARGET=Program
CLANG_CONFIG=../.clang-format

build:
	javac $(J_SOURCE) $(PACKAGE_SOURCE)

run: build
	java $(TARGET)

style:
	clang-format -style=file:$(CLANG_CONFIG) -n $(shell find . -name "*.java")

fix_style:
	clang-format -style=file:$(CLANG_CONFIG) -i $(shell find . -name "*.java")

add:fix_style
	git add $(shell find . -name "*.java") GNUmakefile

clean:
	-rm *.class
	-rm cursor/*.class statistic/*.class
