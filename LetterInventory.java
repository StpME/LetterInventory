// Name: Mitchell Stapelman
// Section: DG
// Represents a letter inventory that keeps track of the counts of each letter in a given string.

public class LetterInventory {

    private int size;
	private int[] elementData;
    private static final int DEFAULT_SIZE = 26;
    

    // Constructs a LetterInventory object with a given String.
    // Ignores case and only processes alphabetic characters.
    public LetterInventory(String data) {
        String casedData = data.toLowerCase();
        this.elementData = new int[DEFAULT_SIZE];
        for (int i = 0; i < casedData.length(); i++) {
            if(Character.isLetter(casedData.charAt(i))) {
                elementData[casedData.charAt(i) - 'a']++;
                size++;
            }
        } 
    }

    // Returns the count of the given letter from the inventory, ignoring case.
    // Throws an IllegalArgumentException if passed a nonalphabetic character.
    public int get(char letter) {
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException();
        }
        return elementData[Character.toLowerCase(letter) - 'a'];
    }

    // Sets the count of the given letter and value, ignoring case.
    // Throws an IllegalArgumentException if passed a nonalphabetic character or if value is negative.
    public void set(char letter, int value) {
        if (!Character.isLetter(letter) || value < 0) {
            throw new IllegalArgumentException();
        }
        size += value - elementData[Character.toLowerCase(letter) - 'a'];
		elementData[Character.toLowerCase(letter) - 'a'] = value;
    }

    // Returns sum of all letter counts in the inventory.
    public int size() {
        return this.size;
    }

    // Returns true if inventory is empty or if all letter counts are 0.
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Constructs and returns a new LetterInventory object, representing the sum of counts for this object and the other.
    public LetterInventory add(LetterInventory other) {
        LetterInventory sum = new LetterInventory("");
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            sum.elementData[i] = this.elementData[i] + other.elementData[i];
            
        }
        sum.size = this.size + other.size;
        return sum;
    }

    // Constructs and returns a new LetterInventory object, representing the difference of counts between this object and other.
    // Returns null if difference is negative.
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory result = new LetterInventory("");
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            result.elementData[i] = this.elementData[i] - other.elementData[i];
            if (result.elementData[i] < 0) {
                return null;
            }
            result.size += result.elementData[i];
        }
        return result;
    }

    // Returns a lowercase and sorted string representation of LetterInventory, surrounded by square brackets.
    // An example inventory with 2 a's, 1 b and 4 h's would be represented as [aabhhhh].
    public String toString() {
        String inventory = "[";
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < elementData[i]; j++) {
                inventory += (char) ('a' + i);
            }
        }
        return inventory + "]";
    }

    public static void main(String[] args) {
        LetterInventory inventory = new LetterInventory("washington state");
        System.out.println(inventory.get('a'));
        System.out.println(inventory.toString());
    }

    // Returns true if the other object stores the same character counts as this inventory.
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LetterInventory)) {
            return false;
        }
        LetterInventory other = (LetterInventory) o;
        LetterInventory diff = this.subtract(other);
        return diff != null && diff.isEmpty();
    }

    // Returns a hash code value for this letter inventory.
    public int hashCode() {
        int result = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            result += c * get(c);
        }
        return result;
    }

    // Returns the cosine similarity between this inventory and the other inventory.
    public double similarity(LetterInventory other) {
        long product = 0;
        double thisNorm = 0;
        double otherNorm = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            int a = this.get(c);
            int b = other.get(c);
            product += a * (long) b;
            thisNorm += a * a;
            otherNorm += b * b;
        }
        if (thisNorm <= 0 || otherNorm <= 0) {
            return 0;
        }
        return product / (Math.sqrt(thisNorm) * Math.sqrt(otherNorm));
    }
}
