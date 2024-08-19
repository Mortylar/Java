

GNUmakefile:
        make run
        or
        make run white=--white-pixels=<ch> black=--black-pixels=<ch> filename=--file=<string>;

        example:
            make run white=--white-pixels=. black=--black-pixels=O filename=--file=it.bmp



Maven:
        mvn compile
        mvn exec:java -Dexec.mainClass="edu.school21.printer.app.Program" -Dexec.args="--white-pixels=<ch> --black-pixels=<ch> --file=<string>"


        example:
            mvn exec:java -Dexec.mainClass="edu.school21.printer.app.Program" -Dexec.args="--white-pixels=. --black-pixels=0 --file=it.bmp"
