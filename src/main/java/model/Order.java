package model;

/**
 * @author Stefana
 * Clasa Order este folosita pentru a modela o comanda, cu atributele definite in tabela orders din baza de date warehouse.
 */
public class Order {
    private int idOrder;
    private int quantity;
    private String nameClient;
    private String nameProduct;
    private Float total;

    /**
     * @param quantity cate produse au fost adaugate in comanda
     * @param idClient idClient numele clientului care a facut comanda
     * @param product  produsul comandat
     */
    public Order(int quantity, String idClient, String product) {
        this.quantity = quantity;
        this.nameProduct = product;
        this.nameClient = idClient;
    }

    public Order() {

    }
    /**
     * @param idOrder    numarul de identificare al comenzii
     * @param nameClient numele clientului care a facut coanda
     * @param product    numele produsului comandat
     * @param quantity   cantiatea de produse comandata
     * @param price      pretul produsului comandat
     */

    public Order(int idOrder, String nameClient, String product, int quantity, Float price) {
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.nameProduct = product;
        this.nameClient = nameClient;
        this.total = price;
    }


    public Order(String nameClient, String product, int quantity, Float price) {
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.nameProduct = product;
        this.nameClient = nameClient;
        this.total = price;
    }

    public Order(int idOrder, Float total) {
        this.idOrder = idOrder;
        this.total = total;
    }

    /**
     * @return pretul produsului din comanda
     */
    public Float getTotal() {
        return total;
    }

    /**
     * @param total pretul produsului comandat in comanda
     */
    public void setTotal(Float total) {
        this.total = total;
    }

    /**
     * @return numarul de identificare al comenzii
     */
    public int getIdOrder() {
        return idOrder;
    }

    /**
     * @param idOrder numarul de identificare al comenzii
     */
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * @return cantitatea de produse comandata
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity cantitatea de produse comandata
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return numele clientului care a facut comanda
     */
    public String getClient() {
        return nameClient;
    }

    /**
     * @param idClient numele clientului care a facut comanda
     */

    public void setClient(String idClient) {
        this.nameClient = idClient;
    }

    /**
     * @return numee produsului comandat
     */
    public String getProduct() {
        return nameProduct;
    }

    /**
     * @param product numele produsului comandat
     */
    public void setIdProduct(String product) {
        this.nameProduct = product;
    }
}
