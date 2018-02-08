/** CS 0445 Spring 2018 (Adapted  from Dr. John Ramirez's assignment code)
 This is a partial implementation of the ReallyLongHex class.  You need to
 complete the implementations of the remaining methods.  Also, for this class
 to work, you must complete the implementation of the LinkedDS class.
 See additional comments below.
*/
public class ReallyLongHex 	extends LinkedDS<Character>
							implements Comparable<ReallyLongHex>
{
	// Instance variables are inherited.  You may not add any new instance variables

	// Default constructor
	private ReallyLongHex()
	{
		super();
	}

	// Note that we are adding the digits here in the END. This results in the
    // MOST significant digit first in the chain.
    // It is assumed that String s is a valid representation of an
	// unsigned integer with no leading zeros.
	public ReallyLongHex(String s)
	{
		super();
		char c;
		// Iterate through the String, getting each character and adding it
        // at the end of the list.
		for (int i = 0; i < s.length(); i++)
		{
			c = s.charAt(i);
			if ((('0' <= c) && (c <= '9')) || (('A' <= c) && (c <= 'F')))
			{
				this.addItem(c);
			}
			else throw new NumberFormatException("Illegal digit " + c);
		}
	}

	// Simple call to super to copy the nodes from the argument ReallyLongHex
	// into a new one.
	public ReallyLongHex(ReallyLongHex rightOp)
	{
		super(rightOp);
	}

	// Method to put digits of number into a String.  We traverse the chain
    // to add the digits to a StringBuilder.
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (numOfEntries > 0)
		{
			sb.append("0x");
			for (Node curr = firstNode; curr != null;
					curr = curr.getNextNode())
			{
				sb.append(curr.getData());
			}
		}
		return sb.toString();
	}

	// You must implement the methods below.  See the descriptions in the
	// assignment sheet

	public ReallyLongHex add(ReallyLongHex rightOp)
	{
		ReallyLongHex result = new ReallyLongHex();
		ReallyLongHex temp1 = new ReallyLongHex(this);
		ReallyLongHex temp2 = new ReallyLongHex(rightOp);
		temp1.reverse();
		temp2.reverse();
		int size = Math.max(temp1.size(), temp2.size());
		int k = 0;
		int t1, t2;
		for (int i = 0; i < size; i++)
		{
			//System.out.println(decToHex(hexToDec(temp1.removeItem())));
			if (temp1.empty())
			{
				t1 = 0;
			}
			else
			{
				t1 = hexToDec(temp1.removeItem());
			}
			if (temp2.empty())
			{
				t2 = 0;
			}
			else
			{
				t2 = hexToDec(temp2.removeItem());
			}
			int r = t1 + t2 + k;
			k = r / 16;
			result.addItem(decToHex(r % 16));
			//hexToDec(temp1.removeItem())
		}
		if (k != 0)
		{
			result.addItem(decToHex(1));
		}
		result.reverse();
		return result;
	}

	public ReallyLongHex subtract(ReallyLongHex rightOp)
	{
		if (this.compareTo(rightOp) < 0)
		{
			throw new ArithmeticException("Invalid Difference -- Negative Number");
		}
		ReallyLongHex result = new ReallyLongHex();
		ReallyLongHex temp1 = new ReallyLongHex(this);
		ReallyLongHex temp2 = new ReallyLongHex(rightOp);
		temp1.reverse();
		temp2.reverse();
		int size = Math.max(temp1.size(), temp2.size());
		int k = 0;
		int t1, t2;
		int zeros = 0;
		for (int i = 0; i < size; i++)
		{
			//System.out.println(decToHex(hexToDec(temp1.removeItem())));
			if (temp1.empty())
			{
				t1 = 0;
			}
			else
			{
				t1 = hexToDec(temp1.removeItem());
			}
			if (temp2.empty())
			{
				t2 = 0;
			}
			else
			{
				t2 = hexToDec(temp2.removeItem());
			}
			int r = t1 - t2 + k;
			if (r < 0)
			{
				k = -1;
				r = 16 + r;
			}
			else
			{
				k = 0;
			}
			if (r == 0)
			{
				zeros++;
			}
			else
			{
				zeros = 0;
			}
			//System.out.println(k);
			result.addItem(decToHex(r));
			//hexToDec(temp1.removeItem())
		}
		result.reverse();
		result.leftShift(zeros);
		return result;
	}

	public int compareTo(ReallyLongHex rOp)
	{
		ReallyLongHex temp1 = new ReallyLongHex(this);
		ReallyLongHex temp2 = new ReallyLongHex(rOp);
		if (temp1.size() < temp2.size())
		{
			return -1;
		}
		else if (temp1.size() > temp2.size())
		{
			return 1;
		}
		int t1, t2;
		for (int i = 0; i < this.size(); i++)
		{
			t1 = hexToDec(temp1.removeItem());
			t2 = hexToDec(temp2.removeItem());
			if (t1 < t2)
			{
				return -1;
			}
			else if (t1 > t2)
			{
				return 1;
			}
		}
		return 0;
	}

	public boolean equals(Object rightOp)
	{
		if (this.compareTo((ReallyLongHex)rightOp) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void mult16ToThe(int num)
	{
		for (int i = 0; i < num; i++)
		{
			this.addItem(decToHex(0));
		}
	}

	public void div16ToThe(int num)
	{
		this.rightShift(num);
	}

	private int hexToDec(char c)
	{
		if (('0' <= c) && (c <= '9'))
		{
			return c - 48;
		}
		else
		{
			return c - 55;
		}
	}

	private char decToHex(int i)
	{
		if ((0 <= i) && (i <= 9))
		{
			return (char)(i + 48);
		}
		else
		{
			return (char)(i + 55);
		}
	}
}
