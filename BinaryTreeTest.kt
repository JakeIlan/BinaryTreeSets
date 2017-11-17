package lesson3

import org.junit.Assert.*
import org.junit.Test



class BinaryTreeTest {
    @Test
    fun add() {
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    //test for headSet and tailSet
    @Test
    fun sets() {
        val tree = BinaryTree<Int>()
        tree.add(5)
        tree.add(3)
        tree.add(4)
        tree.add(2)
        tree.add(10)
        tree.add(12)
        tree.add(11)
        val hSet = tree.headSet(5)
        val tSet = tree.tailSet(5)
        assertEquals(3, hSet.size)
        assertTrue(hSet.contains(2))
        assertTrue(hSet.contains(3))
        assertTrue(hSet.contains(4))

        assertEquals(4, tSet.size)
        assertTrue(tSet.contains(10))
        assertTrue(tSet.contains(11))
        assertTrue(tSet.contains(12))
        assertTrue(tSet.contains(5))
    }



    @Test
    fun sets1() {
        val tree = BinaryTree<Int>()
        tree.add(10)
        tree.add(11)
        tree.add(14)
        tree.add(18)
        tree.add(15)
        tree.add(20)
        tree.add(13)
        tree.add(9)
        tree.add(7)
        tree.add(8)
        tree.add(12)
        val set = tree.headSet(14)
        assertTrue(set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))
        assertEquals(true, set.contains(11))
        assertEquals(true, set.contains(12))
        assertEquals(true, set.contains(13))
        assertEquals(false, set.contains(14))
        assertEquals(false, set.contains(15))
        assertEquals(false, set.contains(18))
        assertEquals(false, set.contains(20))
    }


    @Test
    fun addKotlin() {
        val tree = KtBinaryTree<Int>()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }
}
