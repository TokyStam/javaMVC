
package javafx.mvc.model.domain;

/**
 *
 * @author johnyftr
 */
public class ItemProduit {
    private Produit produit;
    private int quantite;
    private Vente cVente;
    private Double valeur;

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Vente getcVente() {
        return cVente;
    }

    public void setcVente(Vente cVente) {
        this.cVente = cVente;
    }
    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }
  
}
