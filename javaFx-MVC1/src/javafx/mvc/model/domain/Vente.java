
package javafx.mvc.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnyftr
 */
public class Vente {
    private int code;
    private LocalDate dateVente;
    private Double valeur;
    private boolean payer;
    private User user;
    private ItemProduit itemproduit;
    private List<ItemProduit>itemProduit = new ArrayList();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public boolean isPayer() {
        return payer;
    }

    public void setPayer(boolean payer) {
        this.payer = payer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemProduit getItemproduit() {
        return itemproduit;
    }

    public void setItemproduit(ItemProduit itemproduit) {
        this.itemproduit = itemproduit;
    }

    public List<ItemProduit> getItemProduit() {
        return itemProduit;
    }

    public void setItemProduit(List<ItemProduit> itemProduit) {
        this.itemProduit = itemProduit;
    }

   
}
