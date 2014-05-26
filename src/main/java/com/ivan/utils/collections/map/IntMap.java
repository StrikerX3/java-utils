package com.ivan.utils.collections.map;

import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.ivan.utils.lang.Objects;
import com.ivan.utils.math.MathEx;
import com.ivan.utils.math.bits.BitMath;

/**
 * <code>int</code>-based hash map which uses integer primitives as keys. This
 * class does not implement {@link Map}, but provides a method to convert an
 * instance into one, and a constructor and a method to convert a
 * <code>Map</code> into an <code>IntMap</code>. This implementation outperforms
 * a <code>Map&lt;Integer, ?&gt;</code> in all operations by a significant
 * margin, especially on large collections.
 * <p>
 * Although this class does not implement <code>Map</code>, it does provide
 * methods for all operations that interface requires, including all optional
 * operations. This collection is not thread-safe.
 * <p>
 * The collections returned by {@link #keySet()}, {@link #values()},
 * {@link #entrySet()} and {@link #asMap()} are backed by this map, that is,
 * changes made to the collections returned by these methods are reflected in
 * this map and vice-versa. The iterators returned by these collections are
 * <i>fail-fast</i>.
 *
 * @param <V> the type of the values stored by this map
 */
public class IntMap<V> {
    Entry<V>[] table;

    private int capacityMask;

    int size;

    volatile int modCount;

    private float loadFactor;

    private int threshold;

    private Collection<V> values;

    /**
     * The minimum capacity - MUST be a power of two.
     */
    private static final int MINIMUM_CAPACITY = 4;

    /**
     * The default initial capacity - MUST be a power of two.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
    * The maximum capacity, used if a higher value is implicitly specified
    * by either of the constructors with arguments.
    * MUST be a power of two <= 1<<30.
    */
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The load factor used when none specified in constructor.
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Constructs an empty <code>IntMap</code> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public IntMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs an empty <code>IntMap</code> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param initialCapacity the initial capacity.
     */
    public IntMap(final int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty <code>IntMap</code> with the specified initial
     * capacity and load factor.
     *
     * @param initialCapacity the initial capacity, clamped to a valid number
     * @param loadFactor the load factor
     * @throws IllegalArgumentException if the load factor is nonpositive
     */
    public IntMap(final int capacity, final float loadFactor) {
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        final int cap = MathEx.clamp(BitMath.nextPowerOfTwo(capacity), MINIMUM_CAPACITY, MAXIMUM_CAPACITY);
        capacityMask = cap - 1;
        this.loadFactor = loadFactor;
        threshold = (int) (cap * loadFactor);
        table = createTable(cap);
    }

    /**
     * Constructs a new <code>IntMap</code> with the same mappings as the
     * specified <code>Map</code>. The <code>IntMap</code> is created with
     * default load factor (0.75) and an initial capacity sufficient to hold the
     * mappings in the specified <code>Map</code>.
     *
     * @param m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public IntMap(final Map<Integer, V> map) {
        this(Math.max((int) (map.size() / DEFAULT_LOAD_FACTOR) + 1,
                DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        putAll(map);
    }

    /**
     * Hashes the given key, returning the index of the bucket where it is to
     * be placed.
     * 
     * @param key the key to be hashed
     * @return the index of the bucket where the key should be allocated
     */
    private static int hash(final int key, final int mask) {
        return key & mask;
    }

    /**
     * Rehashes the contents of this map into a new array with a different
     * capacity. This method is called automatically when the number of keys in
     * this map reaches its threshold or {@link #trimToSize()} is invoked.
     *
     * @param newCapacity the new capacity, MUST be a power of two
     */
    private void rehash(final int newCapacity) {
        if (newCapacity == table.length) {
            return;
        }
        final int newCapacityMask = newCapacity - 1;
        final Entry<V>[] newTable = createTable(newCapacity);
        for (final Entry<V> entry : table) {
            Entry<V> e = entry;
            while (e != null) {
                final int hash = hash(e.key, newCapacityMask);
                final Entry<V> nx = e.next;
                e.next = newTable[hash];
                newTable[hash] = e;
                e = nx;
            }
        }
        table = newTable;

        capacityMask = newCapacityMask;
        threshold = (int) (newCapacity * loadFactor);
    }

    /**
     * Creates a new table with the specified capacity.
     * 
     * @param newCapacity the new table capacity
     * @return the table
     */
    private Entry<V>[] createTable(final int newCapacity) {
        return (Entry<V>[]) Array.newInstance(Entry.class, newCapacity);
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Returns <code>true</code> if this map contains no key-value mappings.
     *
     * @return <code>true</code> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <code>true</code> if this map contains a mapping for the
     * specified key.
     *
     * @param key The key whose presence in this map is to be tested
     * @return <code>true</code> if this map contains a mapping for the
     * specified key.
     */
    public boolean containsKey(final int key) {
        final int hash = hash(key, capacityMask);
        for (Entry<V> e = table[hash]; e != null; e = e.next) {
            if (e.key == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns <code>true</code> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return <code>true</code> if this map maps one or more keys to the
     *         specified value
     */
    public boolean containsValue(final V value) {
        for (final Entry<V> entry : table) {
            for (Entry<V> e = entry; e != null; e = e.next) {
                if (Objects.equals(e.value, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null}
     * if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key {@code k} to
     * a value {@code v} such that {@code key==k}, then this method returns
     * {@code v}; otherwise it returns {@code null}.
     * (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i> indicate
     * that the map contains no mapping for the key; it's also possible that the
     * map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to distinguish
     * these two cases.
     *
     * @see #put(int, Object)
     */
    public V get(final int key) {
        final int hash = hash(key, capacityMask);
        for (Entry<V> e = table[hash]; e != null; e = e.next) {
            if (e.key == key) {
                return e.value;
            }
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is
     * replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <code>key</code>, or
     *         <code>null</code> if there was no mapping for <code>key</code>.
     *         (A <code>null</code> return can also indicate that the map
     *         previously associated <code>null</code> with <code>key</code>.)
     */
    public V put(final int key, final V value) {
        final int hash = hash(key, capacityMask);
        Entry<V> e = table[hash];
        while (e != null) {
            if (e.key == key) {
                final V old = e.value;
                e.value = value;
                return old;
            }
            e = e.next;
        }
        table[hash] = new Entry<V>(key, value, table[hash]);
        size++;
        modCount++;

        if (size >= threshold) {
            rehash(table.length << 1);
        }
        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <code>key</code>, or
     *         <code>null</code> if there was no mapping for <code>key</code>.
     *         (A <code>null</code> return can also indicate that the map
     *         previously associated <code>null</code> with <code>key</code>.)
     */
    public V remove(final int key) {
        final int hash = hash(key, capacityMask);
        Entry<V> e = table[hash];
        if (e == null) {
            return null;
        }

        if (e.key == key) {
            final V old = e.value;
            table[hash] = e.next;
            size--;
            modCount++;
            return old;
        }

        Entry<V> prev = e;
        e = e.next;
        while (e != null) {
            if (e.key == key) {
                final V old = e.value;
                prev.next = e.next;
                size--;
                modCount++;
                return old;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    /**
     * Copies all of the mappings from the specified <code>IntMap</code> to this
     * map. These mappings will replace any mappings that this map had for any
     * of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     */
    public void putAll(final IntMap<? extends V> m) {
        final int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0) {
            return;
        }

        /*
         * Expand the map if the map if the number of mappings to be added
         * is greater than or equal to threshold.  This is conservative; the
         * obvious condition is (m.size() + size) >= threshold, but this
         * condition could result in a map with twice the appropriate capacity,
         * if the keys to be added overlap with the keys already in this map.
         * By using the conservative calculation, we subject ourself
         * to at most one extra resize.
         */
        if (numKeysToBeAdded > threshold) {
            int targetCapacity = (int) (numKeysToBeAdded / loadFactor + 1);
            if (targetCapacity > MAXIMUM_CAPACITY) {
                targetCapacity = MAXIMUM_CAPACITY;
            }
            int newCapacity = table.length;
            while (newCapacity < targetCapacity) {
                newCapacity <<= 1;
            }
            if (newCapacity > table.length) {
                rehash(newCapacity);
            }
        }

        for (final Entry<? extends V> entry : m.table) {
            for (Entry<? extends V> e = entry; e != null; e = e.next) {
                put(e.key, e.value);
            }
        }
    }

    /**
     * Copies all of the mappings from the specified map to this map. These
     * mappings will replace any mappings that this map had for any of the keys
     * currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     */
    public void putAll(final Map<? extends Integer, ? extends V> m) {
        final int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0) {
            return;
        }

        /*
         * Expand the map if the map if the number of mappings to be added
         * is greater than or equal to threshold.  This is conservative; the
         * obvious condition is (m.size() + size) >= threshold, but this
         * condition could result in a map with twice the appropriate capacity,
         * if the keys to be added overlap with the keys already in this map.
         * By using the conservative calculation, we subject ourself
         * to at most one extra resize.
         */
        if (numKeysToBeAdded > threshold) {
            int targetCapacity = (int) (numKeysToBeAdded / loadFactor + 1);
            if (targetCapacity > MAXIMUM_CAPACITY) {
                targetCapacity = MAXIMUM_CAPACITY;
            }
            int newCapacity = table.length;
            while (newCapacity < targetCapacity) {
                newCapacity <<= 1;
            }
            if (newCapacity > table.length) {
                rehash(newCapacity);
            }
        }

        for (final Map.Entry<? extends Integer, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        size = 0;
        modCount++;
        table = createTable(DEFAULT_INITIAL_CAPACITY);
        capacityMask = DEFAULT_INITIAL_CAPACITY - 1;
    }

    /**
     * Returns an array of the keys contained in this map.
     * 
     * @return an <code>int[]</code> with the keys contained in this map 
     */
    public int[] keys() {
        final int[] keys = new int[size];
        int i = 0;
        for (final Entry<V> entry : table) {
            for (Entry<V> e = entry; e != null; e = e.next) {
                keys[i++] = e.key;
            }
        }
        return keys;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map. The set is
     * backed by the map, so changes to the map are reflected in the set, and
     * vice-versa. If the map is modified while an iteration over the set is in
     * progress (except through the iterator's own <code>remove</code>
     * operation), the results of the iteration are undefined. The set supports
     * element removal, which removes the corresponding mapping from the map,
     * via the <code>Iterator.remove</code>, <code>Set.remove</code>,
     * <code>removeAll</code>, <code>retainAll</code>, and <code>clear</code>
     * operations. It does not support the <code>add</code> or
     * <code>addAll</code> operations.
     * 
     * @return a <code>Set</code> view of the keys contained in this map 
     */
    public Set<Integer> keySet() {
        return new KeySet();
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are reflected
     * in the collection, and vice-versa.  If the map is modified while an
     * iteration over the collection is in progress (except through the
     * iterator's own <code>remove</code> operation), the results of the
     * iteration are undefined. The collection supports element removal, which
     * removes the corresponding mapping from the map, via the
     * <code>Iterator.remove</code>, <code>Collection.remove</code>,
     * <code>removeAll</code>, <code>retainAll</code> and <code>clear</code>
     * operations. It does not support the <code>add</code> or
     * <code>addAll</code> operations.
     */
    public Collection<V> values() {
        final Collection<V> vs = values;
        return vs != null ? vs : (values = new Values());
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map. The set
     * is backed by the map, so changes to the map are reflected in the set, and
     * vice-versa. If the map is modified while an iteration over the set is in
     * progress (except through the iterator's own <code>remove</code>
     * operation, or through the <code>setValue</code> operation on a map entry
     * returned by the iterator) the results of the iteration are undefined. The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the <code>Iterator.remove</code>,
     * <code>Set.remove</code>, <code>removeAll</code>, <code>retainAll</code>
     * and <code>clear</code> operations. It does not support the
     * <code>add</code> or <code>addAll</code> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Entry<V>> entrySet() {
        return new EntrySet();
    }

    /**
     * Returns a {@link Map} view of this map. The map is backed by this map, so
     * changes to the map are reflected in that map, and vice-versa. All
     * operations provided by <code>Map</code> are supported. In other words,
     * this method returns a Map that conforms to the Java Collections Framework
     * and can be used for interoperability.
     *
     * @return a <code>Map</code> view of this map
     */
    public Map<Integer, V> asMap() {
        return new MapView();
    }

    /**
     * Reduces the internal table to the minimum capacity required to hold all
     * mappings contained in this mapping respecting the load factor.
     */
    public void trimToSize() {
        final int newCapacity = Math.max(MINIMUM_CAPACITY, BitMath.nextPowerOfTwo((int) (size / loadFactor) + 1));
        rehash(newCapacity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        String sep = "";
        for (final Entry<V> entry : table) {
            for (Entry<V> e = entry; e != null; e = e.next) {
                sb.append(sep).append(e.key).append('=').append(e.value);
                sep = ", ";
            }
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Represents a mapping from a key to a value.
     *
     * @param <T> the type of the value
     */
    public static class Entry<T> {
        int key;

        T value;

        Entry<T> next;

        public Entry(final int key, final T value, final Entry<T> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public int getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * <code>Set</code> view of the keys contained in this map, returned by
     * {@link IntMap#keySet()}.
     */
    final class KeySet extends AbstractSet<Integer> {
        @Override
        public Iterator<Integer> iterator() {
            return newKeyIterator();
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Integer)) {
                return false;
            }
            return containsKey((Integer) o);
        }

        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Integer)) {
                return false;
            }
            return IntMap.this.remove((Integer) o) != null;
        }

        @Override
        public void clear() {
            IntMap.this.clear();
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * <code>Collection</code> view of the values contained in this map,
     * returned by {@link IntMap#values()}.
     */
    final class Values extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return newValueIterator();
        }

        @Override
        public int size() {
            return size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(final Object o) {
            return IntMap.this.containsValue((V) o);
        }

        @Override
        public void clear() {
            IntMap.this.clear();
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * <code>Set</code> view of the mappings contained in this map, returned by
     * {@link IntMap#entrySet()}.
     */
    final class EntrySet extends AbstractSet<Entry<V>> {
        @Override
        public Iterator<Entry<V>> iterator() {
            return newEntryIterator();
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Entry<V> e = (Entry<V>) o;
            final Entry<V> candidate = getEntry(e.key);
            return candidate != null && candidate.equals(e);
        }

        @Override
        public boolean remove(final Object o) {
            return removeEntry(o);
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void clear() {
            IntMap.this.clear();
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * <code>Set</code> view of <code>Map.Entry</code> objects that correspond
     * to the mappings contained in this map, used by {@link MapView}.
     */
    final class MapEntrySet extends AbstractSet<Map.Entry<Integer, V>> {
        @Override
        public Iterator<Map.Entry<Integer, V>> iterator() {
            return newMapEntryIterator();
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry<Integer, V> e = (Map.Entry<Integer, V>) o;
            final Entry<V> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }

        @Override
        public boolean remove(final Object o) {
            return removeMapEntry(o);
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void clear() {
            IntMap.this.clear();
        }
    }

    /**
     * Returns the entry associated with the specified key in the IntMap.
     * Returns null if the IntMap contains no mapping for the key.
     */
    Entry<V> getEntry(final int key) {
        final int hash = hash(key, capacityMask);
        for (Entry<V> e = table[hash]; e != null; e = e.next) {
            if (e.key == key) {
                return e;
            }
        }
        return null;
    }

    /**
     * Removes the entry associated with the specified key in the IntMap,
     * returning <code>true</code> if the mapping existed.
     */
    @SuppressWarnings("unchecked")
    boolean removeEntry(final Object o) {
        if (!(o instanceof Entry)) {
            return false;
        }
        final Entry<V> e = (Entry<V>) o;
        final boolean containedKey = containsKey(e.key);
        remove(e.key);
        return containedKey;
    }

    /**
     * Removes the entry associated with the specified key in the IntMap,
     * returning <code>true</code> if the mapping existed.
     */
    @SuppressWarnings("unchecked")
    boolean removeMapEntry(final Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        final Map.Entry<Integer, V> e = (Map.Entry<Integer, V>) o;
        final boolean containedKey = containsKey(e.getKey());
        remove(e.getKey());
        return containedKey;
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * <code>Map</code> view of this map, returned by {@link IntMap#asMap()}.
     */
    final class MapView extends AbstractMap<Integer, V> {
        @Override
        public int size() {
            return size;
        }

        @Override
        public V put(final Integer key, final V value) {
            return IntMap.this.put(key, value);
        }

        @Override
        public V get(final Object key) {
            if (!(key instanceof Integer)) {
                return null;
            }
            return IntMap.this.get((Integer) key);
        }

        @Override
        public V remove(final Object key) {
            if (!(key instanceof Integer)) {
                return null;
            }
            return IntMap.this.remove((Integer) key);
        }

        @Override
        public void putAll(final Map<? extends Integer, ? extends V> m) {
            IntMap.this.putAll(m);
        }

        @Override
        public Set<Integer> keySet() {
            return IntMap.this.keySet();
        }

        @Override
        public Collection<V> values() {
            return IntMap.this.values();
        }

        @Override
        public Set<Map.Entry<Integer, V>> entrySet() {
            return new MapEntrySet();
        }

        @Override
        public boolean containsKey(final Object key) {
            if (!(key instanceof Integer)) {
                return false;
            }
            return IntMap.this.containsKey((Integer) key);
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean containsValue(final Object value) {
            return IntMap.this.containsValue((V) value);
        }

        @Override
        public void clear() {
            IntMap.this.clear();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // iterator that enables operations on collections returned by the keySet(),
    // values() and entrySet() methods to be backed by this map

    /**
     * Iterator returned by the collection views, enabling them to be backed by
     * this map.
     *
     * @param <E> the type of the value
     */
    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<V> next; // next entry to return

        int expectedModCount; // For fast-fail

        int index; // current slot

        Entry<V> current; // current entry

        HashIterator() {
            expectedModCount = modCount;
            if (size > 0) { // advance to first entry
                final Entry<V>[] t = table;
                while (index < t.length && (next = t[index++]) == null) {
                }
            }
        }

        @Override
        public final boolean hasNext() {
            return next != null;
        }

        final Entry<V> nextEntry() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            final Entry<V> e = next;
            if (e == null) {
                throw new NoSuchElementException();
            }

            if ((next = e.next) == null) {
                final Entry<V>[] t = table;
                while (index < t.length && (next = t[index++]) == null) {
                }
            }
            current = e;
            return e;
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException();
            }
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            final int k = current.key;
            current = null;
            IntMap.this.remove(k);
            expectedModCount = modCount;
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * {@link HashIterator} for the key set view.
     * 
     * @see IntMap#keySet()
     */
    final class KeyIterator extends HashIterator<Integer> {
        @Override
        public Integer next() {
            return nextEntry().key;
        }
    }

    /**
     * {@link HashIterator} for the value collection view.
     * 
     * @see IntMap#values()
     */
    final class ValueIterator extends HashIterator<V> {
        @Override
        public V next() {
            return nextEntry().value;
        }
    }

    /**
     * {@link HashIterator} for the entry set view.
     * 
     * @see IntMap#entrySet()
     */
    final class EntryIterator extends HashIterator<Entry<V>> {
        @Override
        public Entry<V> next() {
            return nextEntry();
        }
    }

    /**
     * {@link HashIterator} for the map view.
     * 
     * @see IntMap#asMap()
     */
    final class MapEntryIterator extends HashIterator<Map.Entry<Integer, V>> {
        @Override
        public Map.Entry<Integer, V> next() {
            return new MapEntry(nextEntry());
        }
    }

    /**
     * Represents an entry in the {@link Map} view returned by
     * {@link IntMap#asMap()}.
     */
    private final class MapEntry implements Map.Entry<Integer, V> {
        private final Entry<V> entry;

        public MapEntry(final Entry<V> entry) {
            this.entry = entry;
        }

        @Override
        public Integer getKey() {
            return entry.key;
        }

        @Override
        public V getValue() {
            return entry.value;
        }

        @Override
        public V setValue(final V value) {
            final V old = entry.value;
            entry.value = value;
            return old;
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new {@link KeyIterator}.
     * 
     * @return a new <code>KeyIterator</code>, backed by this map
     */
    Iterator<Integer> newKeyIterator() {
        return new KeyIterator();
    }

    /**
     * Creates a new {@link ValueIterator}.
     * 
     * @return a new <code>ValueIterator</code>, backed by this map
     */
    Iterator<V> newValueIterator() {
        return new ValueIterator();
    }

    /**
     * Creates a new {@link EntryIterator}.
     * 
     * @return a new <code>EntryIterator</code>, backed by this map
     */
    Iterator<Entry<V>> newEntryIterator() {
        return new EntryIterator();
    }

    /**
     * Creates a new {@link MapEntryIterator}.
     * 
     * @return a new <code>MapEntryIterator</code>, backed by this map
     */
    Iterator<Map.Entry<Integer, V>> newMapEntryIterator() {
        return new MapEntryIterator();
    }
}
