package com.torretta.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;

public class LuceneTester {

    String indexDir = "C:\\Users\\Zack\\OneDrive\\Desktop\\Indexed\\Index";
    String dataDir = "C:\\Users\\Zack\\OneDrive\\Desktop\\Indexed\\Data";
    Indexer indexer;
    Searcher searcher;

    public static void main(String[] args) {
        LuceneTester tester;
        try {

            tester = new LuceneTester();
            tester.createIndex();

    }   catch (IOException ex) {
            Logger.getLogger(LuceneTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createIndex() throws IOException {
        indexer = new Indexer(indexDir);
        int numIndexed;

        numIndexed = indexer.createIndex(dataDir, new filter());

        indexer.close();
        System.out.println(numIndexed + " File indexed");

    }
   
}

