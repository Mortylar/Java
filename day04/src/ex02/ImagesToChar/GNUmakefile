APP_SOURCE=$(wildcard src/main/java/edu.school21.printer/app/*.java)
LOGIC_SOURCE=$(wildcard src/main/java/edu.school21.printer/logic/*.java)

CLASS_FOLDER=target
CLASS_SOURCE=$(wildcard target/edu/school21/printer/*/*.class)

MANIFEST_TXT=src/main/manifest.txt
MANIFEST_MF=MANIFEST.MF
RESOURCE=src/main/resources/


EXE=edu/school21/printer/app/Program
JAR=Program.jar


all: clean style build


build: clean
	javac  -d $(CLASS_FOLDER) $(APP_SOURCE) $(LOGIC_SOURCE)
	cp $(MANIFEST_TXT) $(CLASS_FOLDER)/$(MANIFEST_MF)
	cp $(RESOURCE)* $(CLASS_FOLDER)
	cd target && jar cvfm $(JAR) *

run: build
	java -jar $(CLASS_FOLDER)/$(JAR) $(white) $(black)

style:
	clang-format -style=file:../../.clang-format -n $(APP_SOURCE) $(LOGIC_SOURCE)

fix_style:
	clang-format -style=file:../../.clang-format -i $(APP_SOURCE) $(LOGIC_SOURCE)

clean:
	-rm -rf target


