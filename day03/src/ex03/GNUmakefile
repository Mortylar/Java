J_SOURCE=$(wildcard *.java)
PACKAGE_SOURCE=$(wildcard file/*.java)
TARGET=Program

build:
	javac $(J_SOURCE) $(PACKAGE_SOURCE)

run: build
	java $(TARGET)

style:
	clang-format -style=file:../.clang-format -n $(J_SOURCE) $(PACKAGE_SOURCE)

fix_style:
	clang-format -style=file:../.clang-format -i $(J_SOURCE) $(PACKAGE_SOURCE)

clean:
	-rm *.class */*.class *.jpg *.png *.pdf
