public class LinkedDS<T> implements PrimQ<T>, Reorder
{
    protected Node<T> firstNode;
    protected int numOfEntries;

    public LinkedDS()
    {
        firstNode = null;
        numOfEntries = 0;
    }

    public LinkedDS(LinkedDS<T> oldList)
    {
        firstNode = null;
        numOfEntries = 0;
        if (!oldList.empty())
        {
            numOfEntries = oldList.size();
            Node temp1 = oldList.firstNode;
            Node newNode = new Node(temp1.getData());
            firstNode = newNode;
            Node temp2 = firstNode;
            temp1 = temp1.getNextNode();
            while (temp1 != null)
            {
                newNode = new Node(temp1.getData());
                temp2.setNextNode(newNode);
                temp1 = temp1.getNextNode();
                temp2 = temp2.getNextNode();
            }
        }
    }

    public boolean addItem(T newEntry)
    {
        Node newNode = new Node(newEntry);
        if (empty())
        {
            firstNode = newNode;
        }
        else
        {
            Node temp = firstNode;
            while (temp.getNextNode() != null)
            {
                temp = temp.getNextNode();
            }
            temp.setNextNode(newNode);
        }
        numOfEntries++;
        return true;
    }

    public T removeItem()
    {
        T data = null;
        if (!empty())
        {
            data = firstNode.getData();
            firstNode = firstNode.getNextNode();
            numOfEntries--;
        }
        return data;
    }

    public boolean empty()
    {
        return numOfEntries == 0;
    }

    public int size()
    {
        return numOfEntries;
    }

    public void clear()
    {
        firstNode = null;
        numOfEntries = 0;
    }

    public void reverse()
    {
        if (empty())
        {
            return;
        }
        Node curr, prev, temp;
        curr = firstNode;
        prev = null;
        while (curr != null)
        {
            temp = curr.getNextNode();
            curr.setNextNode(prev);
            prev = curr;
            curr = temp;
        }
        firstNode = prev;
    }

    public void shiftRight()
    {
        if (size() < 2)
        {
            return;
        }
        Node temp = firstNode;
        while (temp.getNextNode().getNextNode() != null)
        {
            temp = temp.getNextNode();
        }
        temp.getNextNode().setNextNode(firstNode);
        firstNode = temp.getNextNode();
        temp.setNextNode(null);
    }

    public void shiftLeft()
    {
        if (size() < 2)
        {
            return;
        }
        addItem(removeItem());
    }

    public void leftShift(int num)
    {
        if (num <= 0)
        {
            return;
        }
        else if (num >= size())
        {
            clear();
            return;
        }
        for (int i = 0; i < num; i++)
        {
            removeItem();
        }
    }

    public void rightShift(int num)
    {
        if (num <= 0)
        {
            return;
        }
        else if (num >= size())
        {
            clear();
            return;
        }
        numOfEntries = size() - num;
        Node temp = firstNode;
        for (int i = 0; i < numOfEntries-1; i++)
        {
            temp = temp.getNextNode();
        }
        temp.setNextNode(null);
    }

    public void leftRotate(int num)
    {
        if (num < 0)
        {
            rightRotate(-num);
            return;
        }
        else if ((num % size()) == 0)
        {
            return;
        }
        else
        {
            num = num % size();
        }
        Node temp = firstNode;
        Node temp2 = firstNode;
        for (int i = 0; i < num - 1; i++)
        {
            temp = temp.getNextNode();
        }
        while (temp2.getNextNode() != null)
        {
            temp2 = temp2.getNextNode();
        }
        temp2.setNextNode(firstNode);
        firstNode = temp.getNextNode();
        temp.setNextNode(null);
    }

    public void rightRotate(int num)
    {
        if (num < 0)
        {
            leftRotate(-num);
            return;
        }
        else if ((num % size()) == 0)
        {
            return;
        }
        else
        {
            num = num % size();
        }
        Node temp = firstNode;
        Node temp2 = firstNode;
        for (int i = 0; i < numOfEntries - num - 1; i++)
        {
            temp = temp.getNextNode();
        }
        while (temp2.getNextNode() != null)
        {
            temp2 = temp2.getNextNode();
        }
        temp2.setNextNode(firstNode);
        firstNode = temp.getNextNode();
        temp.setNextNode(null);
    }

    public String toString()
    {
        String result = "";
        Node temp = firstNode;
        while (temp != null)
        {
            result += temp.getData().toString();
            result += " ";
            temp = temp.getNextNode();
        }
        return result;
    }

    class Node<T>
    {
        private T data;
        private Node next;

        Node(T data)
        {
            this(data, null);
        }

        Node(T data, Node next)
        {
            this.data = data;
            this.next = next;
        }

        public T getData()
        {
            return data;
        }

        public Node getNextNode()
        {
            return next;
        }

        public void setData(T data)
        {
            this.data = data;
        }

        public void setNextNode(Node next)
        {
            this.next = next;
        }
    }
}
