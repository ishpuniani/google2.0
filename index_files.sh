rm -rf Indexed-Docs

mvn clean
mvn package

java -Xmx6g -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.IndexDocuments
