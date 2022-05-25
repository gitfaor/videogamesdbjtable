package videogamesdbtable;

/**
 *
 * @author Fabio Origoni
 * Description: this class allows you to hide the id of the record.
 */
public class IdNameItem {

    private int id;
    private String name;

    public IdNameItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Override of the toString class.
     *
     * @return name of the videogame.
     */
    @Override
    public String toString() {
        return this.name;
    }
    

}
