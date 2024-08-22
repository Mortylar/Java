APP_SOURCE=$(wildcard src/main/java/edu.school21.printer/app/*.java)
LOGIC_SOURCE=$(wildcard src/main/java/edu.school21.printer/logic/*.java)

LIB_FLAG=lib/jcommander-2.1.jar:lib/JColor-5.5.2-SNAPSHOT.jar

JCOLOR=JColor-5.5.2-SNAPSHOT.jar
JCOMMANDER=jcommander-2.1.jar

CLASS_FOLDER=target
CLASS_SOURCE=$(wildcard target/edu/school21/printer/*/*.class)

MANIFEST_TXT=src/main/manifest.txt
MANIFEST_MF=MANIFEST.MF
RESOURCE=src/main/resources/


EXE=edu/school21/printer/app/Program
JAR=Program.jar


all: clean style build

a:
	echo $(LIB_SOURCE)

compile: clean
	javac  -d $(CLASS_FOLDER) -cp $(LIB_FLAG) $(APP_SOURCE) $(LOGIC_SOURCE)
	cp $(MANIFEST_TXT) $(CLASS_FOLDER)/$(MANIFEST_MF)
	cp $(RESOURCE)* $(CLASS_FOLDER)
	#cp lib/* $(CLASS_FOLDER)
	#cd target && jar cvfm $(JAR) *

build: compile unpack
	cd target && jar cvfm $(JAR) *
	rm -rf lib/JColor lib/JCommander

run: build
	java -jar $(CLASS_FOLDER)/$(JAR) $(white) $(black)

style:
	clang-format -style=file:../../.clang-format -n $(APP_SOURCE) $(LOGIC_SOURCE)

fix_style:
	clang-format -style=file:../../.clang-format -i $(APP_SOURCE) $(LOGIC_SOURCE)

clean:
	-rm -rf target


unpack:
	-mkdir lib/JColor
	-mkdir lib/JCommander
	cp lib/$(JCOLOR) lib/JColor/
	cp lib/$(JCOMMANDER) lib/JCommander/
	cd lib/JColor && jar -xvf $(JCOLOR)
	cd lib/JCommander && jar -xvf $(JCOMMANDER)
	-mkdir target/
	-mkdir target/com/
	cp -r lib/JColor/com/diogonunes target/com/
	cp -r lib/JCommander/com/beust target/com/

