import java.util.Random;

public class MultiDS<T> implements PrimQ<T>, Reorder
{
    private final T[] array;
    private int size;
    private int capacity;
    private int head;
    private int tail;
    private boolean order;

    public MultiDS(int init_capacity)
    {
        T[] temp = (T[]) new Object[init_capacity];
        array = temp;
        size = 0;
        capacity = init_capacity;
        head = 0;
        tail = 0;
        order = true;
    }

    public boolean addItem(T item)
    {
        if (full())
        {
            return false;
        }
        array[tail] = item;
        tail = move(tail);
        size ++;
        return true;
    }

    public T removeItem()
    {
        if (empty())
        {
            return null;
        }
        T temp = array[head];
        head = move(head);
        size --;
        return temp;
    }

    public T top()
    {
        if (empty())
        {
            return null;
        }
        return array[head];
    }

    public boolean full()
    {
        return size==capacity;
    }

    public boolean empty()
    {
        return size==0;
    }

    public int size()
    {
        return size;
    }

    public void clear()
    {
        while (!empty())
        {
            removeItem();
        }
    }

    public void reverse()
    {
        if (order)
        {
            order = false;
        }
        else
        {
            order = true;
        }
        head = move(head);
        tail = move(tail);
        int temp = head;
        head = tail;
        tail = temp;
    }

    public void shiftRight()
    {
        reverse();
        shiftLeft();
        reverse();
    }

    public void shiftLeft()
    {
        T temp = array[head];
        head = move(head);
        array[tail] = temp;
        tail = move(tail);
    }

    public void shuffle()
    {
        Random rand = new Random();
        for (int i = 0; i < size(); i ++)
        {
            int r = rand.nextInt(size());
            T temp = array[(head+i)%capacity];
            array[(head+i)%capacity] = array[(head+r)%capacity];
            array[(head+r)%capacity] = temp;
        }
        //return;
    }

    private int move(int pointer)
    {
        if (order)
        {
            return (pointer + 1) % capacity;
        }
        else
        {
            return (pointer + capacity - 1) % capacity;
        }
    }

    public String toString()
    {
        String result = "";
        int i = 0;
        while (Math.abs(i) < size())
        {
            result += array[(head+i+capacity)%capacity].toString();
            result += " ";
            if (order)
            {
                i ++;
            }
            else
            {
                i --;
            }
        }
        result += "\n";
        return result;
    }
}
