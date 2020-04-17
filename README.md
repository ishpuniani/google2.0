# google2.0

The Irrelevants' (Group 10) submission for Information Retrieval Assignment 2

# Index Files

There is an existing Index on this aws instance that has been done with a Custom Analyzer, which can be viewed in the `MyAnalyzer.java` file, and the BM25 similarity

## To re-index the files

run `./index_files.sh` to index files.

These index files will be placed into the `Indexed-Docs` folder

# Search Index

run `./run.sh` to search the indexed files

Choose the Analyzer and Similarity you used for the indexing

The search results will be placed into the `results.txt` file

This will also run the trec_eval command with the half Qrel file and store the results in the `trec_results.txt` file

The results in `trec_results.txt` will also be printed to the console

# Best Analyzer & Similarity Combo

The best combination so far is using the _Custom Analyzer_ with the _BM25 Similarity_ which gave us a MAP of 0.3240 with the half qrel file
