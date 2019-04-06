package com.github.kumaraman21.intellijbehave.settings;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AnythingHappenedDocumentListener implements DocumentListener {

    private DocumentChangedListener documentChangedListener;

    public AnythingHappenedDocumentListener(DocumentChangedListener documentChangedListener) {
        this.documentChangedListener = documentChangedListener;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChangedListener.documentChanged(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChangedListener.documentChanged(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChangedListener.documentChanged(e);
    }
}
