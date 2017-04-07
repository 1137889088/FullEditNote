package com.chen.app.domain;

/**
 * Created by chen on 2017/4/7.
 */
public class NoteInfoBean {
    private String noteId;
    private String noteTitle;
    private String noteDate;

    public NoteInfoBean(String noteId, String noteTitle, String noteDate) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDate = noteDate;
    }

    public NoteInfoBean(String noteTitle, String noteDate) {
        this.noteTitle = noteTitle;
        this.noteDate = noteDate;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
