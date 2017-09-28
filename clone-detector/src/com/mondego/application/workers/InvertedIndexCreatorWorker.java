package com.mondego.application.workers;

import java.util.HashSet;
import java.util.Set;

import com.mondego.application.handlers.SearchActionHandler;
import com.mondego.framework.models.Bag;
import com.mondego.framework.models.DocumentForInvertedIndex;
import com.mondego.framework.models.TermInfo;
import com.mondego.framework.models.TokenFrequency;
import com.mondego.framework.workers.Worker;
import com.mondego.indexbased.DocumentMaker;

public class InvertedIndexCreatorWorker extends Worker<Bag> {
    private DocumentMaker documentMaker;
    public InvertedIndexCreatorWorker(Bag t) {
        super(t);
        this.documentMaker = new DocumentMaker();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void process() {
        DocumentForInvertedIndex documentForII = this.documentMaker.prepareDocumentForII(this.dataObject);
        SearchActionHandler.documentsForII.put(documentForII.id, documentForII);
        Set<Long> docs = null;
        int prefixLength = documentForII.prefixSize;
        int pos = 0;
        TermInfo termInfo = null;
        for (TokenFrequency tf : this.dataObject) {
            if (prefixLength > 0) {
                String term = tf.getToken().getValue();
                if (SearchActionHandler.invertedIndex.containsKey(term)){
                    docs= SearchActionHandler.invertedIndex.get(term);
                }else{
                    docs = new HashSet<Long>();
                    SearchActionHandler.invertedIndex.put(term, docs);
                }
                docs.add(documentForII.id);
                termInfo = new TermInfo();
                termInfo.frequency=tf.getFrequency();
                termInfo.position = pos;
                pos = pos + tf.getFrequency();
                documentForII.termInfoMap.put(term, termInfo);
                prefixLength -= tf.getFrequency();
            }
            documentForII.tokenFrequencies.add(tf);
        }
    }

    public DocumentMaker getIndexer() {
        return documentMaker;
    }

}