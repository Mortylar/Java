
package user;

public abstract class HomoSapiens {

    private String name_;

    public String getName() { return name_; }
    protected void setName(String name) { name_ = name; }

    public abstract void print();
}
