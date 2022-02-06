package model;

/**
 * @author Stefana
 * Clasa Product este folosita pentru a modela un produs si are atributele declarate in tabela product din baza de date warehouse
 */

public class Product {
    private int idProduct;
    private String productName;
    private int quantity;
    private float price;

    public Product() {

    }

    /**
     * @param id          numarul de identificare al produsului
     * @param productName numele produsului
     * @param quantity    cantitatea de produse existenta in stoc
     * @param price       pretul produsului
     */
    public Product(Integer id, String productName, int quantity, float price) {
        this.idProduct = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * @param productName numele produsului
     * @param quantity    catitatea produsuliui existenta in stoc
     * @param price       pretul produsului
     */
    public Product(String productName, int quantity, float price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * @return numarul de identificare al produsului
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * @param idProduct numarul de identificare al produsului
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * @return numele produsului
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName numele produsului
     */

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return numarul de bucati existente in stoc
     */

    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity numarul de bucati existente in stoc
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return pretul produsului
     */

    public float getPrice() {
        return price;
    }

    /**
     * @param price pretul produsului
     */
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
