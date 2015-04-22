package com.github.kumaraman21.intellijbehave.utility;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class CharTree<T> {
    private final int key;
    private final Map<Integer, CharTree<T>> children = new HashMap<Integer, CharTree<T>>();
    private T value;

    private CharTree(int key) {
        this(key, null);
    }

    public CharTree(int key, @Nullable T value) {
        super();
        this.key = key;
        this.value = value;
    }

    public T lookupValue(CharSequence seq) {
        return lookupValue(seq, 0);
    }

    private T lookupValue(CharSequence seq, int offset) {
        return lookup(seq, offset).value;
    }

    public Entry<T> lookup(CharSequence seq, int offset) {
        CharTree<T> ct = this;
        T found = this.value;

        int i = offset;
        for (int n = seq.length(); i < n; i++) {
            ct = ct.get(seq.charAt(i));
            if (ct == null) {
                break;
            }
            if (ct.value != null) {
                found = ct.value;
            }
        }
        return new Entry<T>(found, i - offset);
    }

    void print(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append(" |  ");
        }
        System.out.print(builder.toString());
        System.out.print("[");
        System.out.print((char) key);
        System.out.print("] ");
        System.out.println(value == null ? "n/a" : value);

        List<Integer> keys = new ArrayList<Integer>(children.keySet());
        Collections.sort(keys);
        for (Integer key : keys) {
            children.get(key).print(level + 1);
        }
    }

    public void push(CharSequence seq, T value) {
        push(seq, 0, value);
    }

    private void push(CharSequence seq, int pos, T value) {
        if (pos < seq.length()) {
            int c = seq.charAt(pos);
            CharTree<T> child = getOrCreate(c);
            child.push(seq, pos + 1, value);
        } else {
            this.value = value;
        }
    }

    private CharTree<T> get(int c) {
        return children.get(c);
    }

    private CharTree<T> getOrCreate(int c) {
        CharTree<T> cn = children.get(c);
        if (cn == null) {
            cn = new CharTree<T>(c);
            children.put(c, cn);
        }
        return cn;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public static class Entry<T> {
        public final T value;
        public final int length;

        public Entry(T value, int length) {
            this.value = value;
            this.length = length;
        }

        public boolean hasValue() {
            return value != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Entry entry = (Entry) o;

            if (length != entry.length) {
                return false;
            }
            return !(value != null ? !value.equals(entry.value) : entry.value != null);

        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + length;
            return result;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "value=" + value +
                    ", length=" + length +
                    '}';
        }
    }
}
