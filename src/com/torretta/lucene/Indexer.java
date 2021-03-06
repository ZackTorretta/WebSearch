/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.torretta.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Zack
 */
public class Indexer {

    private final IndexWriter writer;

    
    public Indexer(String indexDirectoryPath) throws IOException {
        //this directory will contain the indexes 
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));

        //create the indexer 
        writer = new IndexWriter(indexDirectory,
                new StandardAnalyzer(Version.LUCENE_36), true,
                IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();
        //index file contents 
        Field contentField = new Field(IndexSearch.CONTENTS, new FileReader(file), TermVector.WITH_POSITIONS_OFFSETS);
        //index file name 
        Field fileNameField = new Field(IndexSearch.FILE_NAME,
                file.getName(),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.WITH_POSITIONS_OFFSETS);
        //index file path 
        Field filePathField = new Field(IndexSearch.FILE_PATH,
                file.getCanonicalPath(),
                Field.Store.YES, Field.Index.NOT_ANALYZED, TermVector.WITH_POSITIONS_OFFSETS);
        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);
        return document;
    }

    private void indexFile(File file) throws IOException {

        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
        //get all files in the data directory 
        File[] files = new File(dataDirPath).listFiles();
        for (File file : files) {
            if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file)) {
                indexFile(file);
            }
        }
        return writer.numDocs();
    }
}
