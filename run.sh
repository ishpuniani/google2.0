# Commands to run program and store results

java -Xmx6g -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.SearchIndex

./trec_eval-9.0.7/trec_eval Dataset/qrels.assignment2.part1 results.txt > trec_results.txt

cat trec_results.txt