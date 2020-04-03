rm -rf Indexed-Docs # Remove index folder if it exists

mvn clean
mvn package

java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.IndexDocuments
