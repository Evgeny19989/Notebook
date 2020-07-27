package com.example.notebook;

import java.util.Comparator;

public class ToDoListComparator {

    private static NameComparator nameComparator;
    private static DateComparator dateComparator;

    public static Comparator<Note> getDateComparator() {
        if (dateComparator == null) {
            dateComparator = new DateComparator();
        }
        return dateComparator;
    }

    public static Comparator<Note> getNameComparator() {
        if (nameComparator == null) {
            nameComparator = new NameComparator();
        }
        return nameComparator;
    }

    private static class NameComparator implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    }

    private static class DateComparator implements Comparator<Note> {
        @Override
        public int compare(Note o1, Note o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
}