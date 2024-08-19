APP_SOURCE=$(wildcard src/java/edu.school21.printer/app/*.java)
LOGIC_SOURCE=$(wildcard src/java/edu.school21.printer/logic/*.java)

CLASS_FOLDER=target
APP_CLASS_SOURCE=$(CLASS_FOLDER )$(wildcard edu/school21/printer/app/*.java)
LOGIC_CLASS_SOURCE=$(CLASS_FOLDER )$(wildcard edu/school21/printer/logic/*.java)

EXE=edu/school21/printer/app/Program

all:
	echo $(LOGIC_SOURCE)

build:
	javac  -d $(CLASS_FOLDER) $(APP_SOURCE) $(LOGIC_SOURCE)

run: build
	java -cp $(CLASS_FOLDER) $(EXE) $(white) $(black) $(filename)

style:
	clang-format -style=file:../../.clang-format -n $(APP_SOURCE) $(LOGIC_SOURCE)

fix_style:
	clang-format -style=file:../../.clang-format -i $(APP_SOURCE) $(LOGIX_SOURCE)

clean:
	-rm -rf target
