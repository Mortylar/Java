

GNUmakefile:
        make build
        java -jar target/Program.jar
        or
        make run
        or
        make run white=--white=<String> black=--black=<String>;

        example:
            make run white=--white=WHITE black=--black=BLACK
            java -jar target/Program.jar --white=WHITE --black=BLACK



Maven:
        mvn clean package 
            java -jar target/Program-2.0.jar --white=<String> --blak=<BLACK>


        example:
            java -jar target/Program-2.0.jar --white=WHITE --black=BLACK
