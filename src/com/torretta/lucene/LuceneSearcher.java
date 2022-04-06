package com.torretta.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
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

public class LuceneSearcher {

    String indexDir = "C:\\Users\\Zack\\OneDrive\\Desktop\\Indexed\\Index";
    String dataDir = "C:\\Users\\Zack\\OneDrive\\Desktop\\Indexed\\Data";
    Indexer indexer;
    Searcher searcher;

    public static void main(String[] args) {
        LuceneSearcher tester;
        try {
            Scanner myObj = new Scanner(System.in);
            System.out.println("Please enter song title");
            String song = myObj.nextLine();
            System.out.println("Song is: " + song);
            tester = new LuceneSearcher();
            tester.search(song);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void search(String searchQuery) throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        String p = "";
        String s = "https://genius.com/";
        TopDocs docs = searcher.search(searchQuery, indexDir, 10);
//        System.out.println(docs.totalHits + " documents found");
        
        
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
          //just breaks first iteration to display first one for now. 
           Document doc = searcher.getDocument(scoreDoc);
           String walk = doc.get(IndexSearch.FILE_NAME);

            String toLowerCase = walk.toLowerCase();

            p = toLowerCase.replaceAll(" ", "-");
            //formatting title as URL. gets rid of parenthesis, apostrophes, etc.
            p = p.replaceAll("---genius-lyrics.txt","");
            p = p.replaceAll("[()]","");
            p = p.replaceAll("-–-","-");
            p = p.replaceAll("'", "");
            System.out.print(s + p);
            System.out.println();
            System.out.println("Score: " + Float.toString(scoreDoc.score));
            break;
        }
        searcher.close();
    }
}
