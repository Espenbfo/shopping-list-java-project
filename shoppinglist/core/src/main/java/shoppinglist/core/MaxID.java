package shoppinglist.core;

/**
 * This class is for storing the max ID of a shoppinglist.
 */
public class MaxID {
    /**
     * the highest id as of yet.
     */
    private int id;

    /**
     * Initialize a MaxID with the default value.
     */
    public MaxID() {
        id = 0;
    }

    /**
     * Initialize a MaxID with this.id = id.
     *
     * @param id the new id
     */
    public MaxID(final int id) {
        this.id = id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
}
