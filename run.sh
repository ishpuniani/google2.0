# Commands to run program and store results

java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.SearchIndex

# Run command for previous students project: https://github.com/CS7IS3-A-Y-201718-IR2/text-search-engine-ir2
# java -cp target/app-1.0-SNAPSHOT.jar com.mycompany.app.Application -dataSource Parsed-docs -queryFile DataSet/topics -output output/ -assets DataSet -indexDir index/

./trec_eval-9.0.7/trec_eval DataSet/qrels.assignment2.part1 results.txt > trec_results.txt