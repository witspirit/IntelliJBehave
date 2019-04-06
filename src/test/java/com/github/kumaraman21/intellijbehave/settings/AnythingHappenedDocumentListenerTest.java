package com.github.kumaraman21.intellijbehave.settings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.event.DocumentEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AnythingHappenedDocumentListenerTest {

    DocumentChangedListener listener;
    AnythingHappenedDocumentListener underTest;

    DocumentEvent documentEvent;

    @Before
    public void setup() {
        documentEvent = mock(DocumentEvent.class);
        listener = mock(DocumentChangedListener.class);
        underTest = new AnythingHappenedDocumentListener(listener);
    }

    @Test
    public void whenInsertUpdate_thenListenerCalledWithTheEvent() {
        underTest.insertUpdate(documentEvent);

        verify(listener).documentChanged(documentEvent);
    }

    @Test
    public void whenRemoveUpdate_thenListenerCalledWithTheEvent() {
        underTest.removeUpdate(documentEvent);

        verify(listener).documentChanged(documentEvent);
    }

    @Test
    public void whenChangedUpdate_thenListenerCalledWithTheEvent() {
        underTest.changedUpdate(documentEvent);

        verify(listener).documentChanged(documentEvent);
    }
}