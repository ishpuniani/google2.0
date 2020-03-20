mvn clean
mvn package
# Commands to run program and store results

java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.Application -dataSource Parsed-docs -queryFile DataSet/topics -output output/ -assets src/main/resources -indexDir index/