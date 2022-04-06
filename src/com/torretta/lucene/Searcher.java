/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torretta.lucene;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.index.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.QueryTermVector;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Similarity;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;

public class Searcher {

    IndexSearcher indexSearcher;
    IndexSearcher search;
    QueryParser parser;
    Query query;
    TermEnum guess;
    IndexReader indexReader;
//    Searcher searcher;
    TopScoreDocCollector collect;

    public Searcher(String indexDirectoryPath) throws IOException {
//        int numHits = 10;
        Directory iDir = FSDirectory.open(new File(indexDirectoryPath));
        indexSearcher = new IndexSearcher(iDir);
//        IndexSearcher searcher = new IndexSearcher(indexReader);
        parser = new QueryParser(Version.LUCENE_36,
                IndexSearch.CONTENTS,
                new StandardAnalyzer(Version.LUCENE_36));
    }

    public TopDocs search(String searchQuery, String indexDirectoryPath, int x) throws IOException, ParseException {

        query = parser.parse(searchQuery);

        return indexSearcher.search(query, IndexSearch.MAX_SEARCH);
    }

    public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    public void close() throws IOException {
        indexSearcher.close();
    }

    Document getDocument(int scoreDoc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
