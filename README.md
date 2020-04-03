# google2.0
The Irrelevants' (Group 10) submission for Information Retrieval Assignment 2

# Index Files
run `./index_files.sh` to index files.

The indexing takes around 10-15 minutes

The current Index on this aws instance has been done with a Custom Analyzer, which can be viewed in the `MyAnalyzer.java` file

These index files will be placed into the `Indexed-Docs` folder

# Search Index
run `./run.sh` to search the indexed files

Choose the Analyzer you used for the indexing and whatever Similarity you want

The search results will be placed into the `results.txt` file

This will also run the trec_eval command and store the results in the `trec_results.txt` file

The results in `trec_results.txt` will also be printed to the console

# Best Analyzer & Similarity Combo
The best combination so far is using the *Custom Analyzer* with the *BM25 Similarity*