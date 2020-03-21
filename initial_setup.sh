mvn clean
mvn package

# Parse Documents in Dataset and place into Parsed-docs folder
java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.Parsers.LaTimesParser
java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.Parsers.FbisParser
java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.Parsers.Fr94Parser
java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.Parsers.FtParser

# Index documents
java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.IndexFiles -docs Parsed-docs/
