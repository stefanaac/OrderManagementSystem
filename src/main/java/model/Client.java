package model;

/**
 * @author Stefana
 * Clasa Client este folosita pentru reprezentarea unui client asa cum am definit tabela client din baza de date warehouse.
 */
public class Client {
    /**
     * id-ul este generat random la crearea unui client nou
     */
    private int id;
    private String name;
    private String address;
    private String email;


    public Client() {
    }

    /**
     * @param id      numar intreg unic generat random la crearea unui client nou
     * @param name    numele clientului
     * @param address adresa clientului
     * @param email   adresa de email a clientului
     */
    public Client(int id, String name, String address, String email) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;

    }

    public Client(String name) {

        this.name = name;

    }

    public Client(String name, String address, String email) {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
    }


    /**
     * @return id-ul clientului
     */
    public int getId() {
        return id;
    }

    /**
     * @param id numarul de identificare al clientului
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return numele clientului
     */
    public String getName() {
        return name;
    }

    /**
     * @param name numele clientului
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return adresa clientului
     */

    public String getAddress() {
        return address;
    }

    /**
     * @param address adresa clientului
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return adresa de email a clientului
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email adresa de email a clientuli
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return un string care contine toate informatiile legate de un client
     */
    @Override
    public String toString() {
        return "Client[id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
    }


}
