import java.util.NoSuchElementException;

/** Class ArrayStack implements the interface StackInt as
 *  an adapter to the List. This implementation is functionally
 *  equivalent to that given in java.util.Stack except that the
 *  underlying Array is not publicly exposed.
 *  @param <E> The type of the elements in the stack
 */
public class ArrayStack<E> implements StackInt<E> {

    /** The List containing the data */
    public int capacity = 25;
    private int numberOfElements = 0;
    private E[] arrayStack;

    /**
     * Construct an empty stack using an Array as the
     * container and with a fixed capacity.
     */
    public ArrayStack() {
        arrayStack = (E[]) new Object[capacity];
    }


    /**
     * Push an object onto the stack.
     * @post The object is at the top of the stack.
     * @param obj The object to be pushed
     * @return The object pushed
     */
    @Override
    public E push(E obj) {
        if (numberOfElements+1 <= capacity) {
            arrayStack[numberOfElements] = obj;
            this.numberOfElements = numberOfElements + 1;
        }
        else {
            this.reallocate();
            arrayStack[numberOfElements] = obj;
            this.numberOfElements = numberOfElements + 1;
        }
        return obj;
    }

    /**
     * Peek at the top object on the stack.
     * @return The top object on the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return arrayStack[numberOfElements-1];
    }

    /**
     * Pop the top object off the stack.
     * @post The object at the top of the stack is removed.
     * @return The top object, which is removed
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E poppedElement = arrayStack[numberOfElements-1];
        numberOfElements = numberOfElements - 1;
        arrayStack[numberOfElements] = null;
        return poppedElement;
    }

    /**
     * See whether the stack is empty.
     * @return true if the stack is empty
     */
    @Override
    public boolean isEmpty() {
        if (numberOfElements == 0){
            return true;
        }
        else {
            return false;
        }
    }

    /*<exercise chapter="4" section="3" type="programming" number="2">*/
    public int size() {
        return numberOfElements;
    }

    private void reallocate(){
        this.capacity = 2*capacity;
        E[] newArrayStack = (E[]) new Object[capacity];
        System.arraycopy(arrayStack, 0, newArrayStack, 0, arrayStack.length );
        arrayStack = newArrayStack.clone();
    }

}