

GNUmakefile:
        make build
        java -jar target/Program.jar
        or
        make run
        or
        make run white=--white-pixels=<ch> black=--black-pixels=<ch>;

        example:
            make run white=--white-pixels=. black=--black-pixels=O



Maven:
        mvn clean package
            java -jar target/Program-2.0.jar 


        example:
            java -jar target/Program-2.0.jar --white-pixels=. --black-pixels=O
