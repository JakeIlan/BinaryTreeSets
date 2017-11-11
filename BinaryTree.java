package lesson3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {
        }

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }


//    Начинаем с корня
//    Сравниваем элемент с toElement
//    Если он (элемент) больше, то ничего не делаем и переходим к левому ребенку
//    Если он меньше, то добавляем к ответу его и его левое поддерево и переходим к правому ребенку
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        if (toElement == null) throw new NullPointerException();
        else {
            BinaryTree<T> subTree = new BinaryTree<>();
            Node<T> temp = this.root;
            T tempVal = temp.value;
            if (tempVal == null) throw new NoSuchElementException();
            else {
                while (true) {
                    if (tempVal.compareTo(toElement) < 0) {
                        //если сравниваемый элемент меньше, чем toElement, то мы добавляем этот элемент
                        // и его левое поддерево в результат и переходим к его правому потомку
                        subTree.add(tempVal);
                        if (temp.left != null) {
                            subTree.add((temp.left).value);
                            addChildren(temp.left, subTree);
                        }
                        if (temp.right != null) {
                            temp = temp.right;
                            tempVal = temp.value;
                        } else break;
                    } else if (tempVal.compareTo(toElement) > 0) {
                        //если сравниваемый элемент больше, чем toElement, то мы переходим к его левому потомку,
                        // ничего не добавляя в результат
                        if (temp.left != null) {
                            temp = temp.left;
                            tempVal = temp.value;
                        } else break;
                    } else if (tempVal.compareTo(toElement) == 0) {
                        //если мы нашли toElement, то добавляем его левое поддерево и выходим из цикла
                        if (temp.left != null) {
                            subTree.add((temp.left).value);
                            addChildren(temp.left, subTree);
                        }
                        break;
                    }
                }
            }
            return subTree;
        }
    }

    //вспомогательный метод, для добавления всех потомков ячейки в отдельное дерево
    private void addChildren (Node<T> start, BinaryTree<T> tree) {
        if (start.left != null) {
            tree.add((start.left).value);
            addChildren(start.left, tree);
        }
        if (start.right != null) {
            tree.add((start.right).value);
            addChildren(start.right, tree);
        }
    }


//    Алгоритм аналогичен алгоритму нахождения headSet, если сравниваемый элемент больше, чем fromElement,
//    то добавляем его правое поддерево и переходим к левому потомку, иначе, не добавляя переходим к правому
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (fromElement == null) throw new NullPointerException();
        else {
            BinaryTree<T> subTree = new BinaryTree<>();
            Node<T> temp = this.root;
            T tempVal = temp.value;
            if (tempVal == null) throw new NoSuchElementException();
            else {
                while (true) {
                    if (tempVal.compareTo(fromElement) > 0) {
                        //если сравниваемый элемент больше, чем fromElement, то мы добавляем этот элемент
                        // и его правое поддерево в результат и переходим к его левому потомку
                        subTree.add(tempVal);
                        subTree.add((temp.right).value);
                        addChildren(temp.right, subTree);
                        temp = temp.left;
                        tempVal = temp.value;
                    } else if (tempVal.compareTo(fromElement) < 0) {
                        //если сравниваемый элемент меньше, чем fromElement, то мы переходим к его правому потомку,
                        // ничего не добавляя в результат
                        temp = temp.right;
                        tempVal = temp.value;
                    } else if (tempVal.compareTo(fromElement) == 0) {
                        //если мы нашли fromElement, то добавляем его правое поддерево и выходим из цикла
                        subTree.add((temp.right).value);
                        addChildren(temp.right, subTree);
                        break;
                    }
                }
            }
            return subTree;
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
