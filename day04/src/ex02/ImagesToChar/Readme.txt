

GNUmakefile:
        make build
        java -jar target/images-to-chars-printer.jar
        or
        make run
        or
        make run white=--white=<String> black=--black=<String>;

        example:
            make run white=--white=WHITE black=--black=BLACK
            java -jar target/images-to-chars-printer.jar --white=WHITE --black=BLACK



Maven:
        mvn clean package 
            java -jar target/images-to-chars-printer-1.0.jar --white=<String> --blak=<BLACK>


        example:
            java -jar target/images-to-chars-printer-1.0.jar --white=WHITE --black=BLACK
