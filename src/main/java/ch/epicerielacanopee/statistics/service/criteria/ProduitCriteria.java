package ch.epicerielacanopee.statistics.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ch.epicerielacanopee.statistics.domain.Produit} entity. This class is used
 * in {@link ch.epicerielacanopee.statistics.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private IntegerFilter epicerioId;

    private InstantFilter createdDate;

    private InstantFilter lastUpdatedDate;

    private InstantFilter importedDate;

    private StringFilter nom;

    private StringFilter code;

    private StringFilter disponible;

    private FloatFilter prixFournisseur;

    private StringFilter htTtc;

    private FloatFilter tauxTva;

    private FloatFilter margeProfit;

    private FloatFilter prixVente;

    private StringFilter vendu;

    private FloatFilter quantiteParPiece;

    private StringFilter unite;

    private StringFilter prixParUnite;

    private StringFilter fournisseur;

    private StringFilter refFournisseur;

    private FloatFilter stock;

    private FloatFilter commandesClients;

    private LocalDateFilter derniereVerificationDate;

    private LocalDateFilter derniereLivraisonDate;

    private StringFilter achatFournisseur;

    private LocalDateFilter dernierAchatDate;

    private FloatFilter dernierAchatQuantite;

    private FloatFilter statsLivraison;

    private FloatFilter statsPerte;

    private FloatFilter statsVente;

    private FloatFilter statsVenteSpeciale;

    private Boolean distinct;

    public ProduitCriteria() {}

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.epicerioId = other.optionalEpicerioId().map(IntegerFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastUpdatedDate = other.optionalLastUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.importedDate = other.optionalImportedDate().map(InstantFilter::copy).orElse(null);
        this.nom = other.optionalNom().map(StringFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.disponible = other.optionalDisponible().map(StringFilter::copy).orElse(null);
        this.prixFournisseur = other.optionalPrixFournisseur().map(FloatFilter::copy).orElse(null);
        this.htTtc = other.optionalHtTtc().map(StringFilter::copy).orElse(null);
        this.tauxTva = other.optionalTauxTva().map(FloatFilter::copy).orElse(null);
        this.margeProfit = other.optionalMargeProfit().map(FloatFilter::copy).orElse(null);
        this.prixVente = other.optionalPrixVente().map(FloatFilter::copy).orElse(null);
        this.vendu = other.optionalVendu().map(StringFilter::copy).orElse(null);
        this.quantiteParPiece = other.optionalQuantiteParPiece().map(FloatFilter::copy).orElse(null);
        this.unite = other.optionalUnite().map(StringFilter::copy).orElse(null);
        this.prixParUnite = other.optionalPrixParUnite().map(StringFilter::copy).orElse(null);
        this.fournisseur = other.optionalFournisseur().map(StringFilter::copy).orElse(null);
        this.refFournisseur = other.optionalRefFournisseur().map(StringFilter::copy).orElse(null);
        this.stock = other.optionalStock().map(FloatFilter::copy).orElse(null);
        this.commandesClients = other.optionalCommandesClients().map(FloatFilter::copy).orElse(null);
        this.derniereVerificationDate = other.optionalDerniereVerificationDate().map(LocalDateFilter::copy).orElse(null);
        this.derniereLivraisonDate = other.optionalDerniereLivraisonDate().map(LocalDateFilter::copy).orElse(null);
        this.achatFournisseur = other.optionalAchatFournisseur().map(StringFilter::copy).orElse(null);
        this.dernierAchatDate = other.optionalDernierAchatDate().map(LocalDateFilter::copy).orElse(null);
        this.dernierAchatQuantite = other.optionalDernierAchatQuantite().map(FloatFilter::copy).orElse(null);
        this.statsLivraison = other.optionalStatsLivraison().map(FloatFilter::copy).orElse(null);
        this.statsPerte = other.optionalStatsPerte().map(FloatFilter::copy).orElse(null);
        this.statsVente = other.optionalStatsVente().map(FloatFilter::copy).orElse(null);
        this.statsVenteSpeciale = other.optionalStatsVenteSpeciale().map(FloatFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public Optional<UUIDFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public UUIDFilter id() {
        if (id == null) {
            setId(new UUIDFilter());
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public IntegerFilter getEpicerioId() {
        return epicerioId;
    }

    public Optional<IntegerFilter> optionalEpicerioId() {
        return Optional.ofNullable(epicerioId);
    }

    public IntegerFilter epicerioId() {
        if (epicerioId == null) {
            setEpicerioId(new IntegerFilter());
        }
        return epicerioId;
    }

    public void setEpicerioId(IntegerFilter epicerioId) {
        this.epicerioId = epicerioId;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Optional<InstantFilter> optionalLastUpdatedDate() {
        return Optional.ofNullable(lastUpdatedDate);
    }

    public InstantFilter lastUpdatedDate() {
        if (lastUpdatedDate == null) {
            setLastUpdatedDate(new InstantFilter());
        }
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(InstantFilter lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public InstantFilter getImportedDate() {
        return importedDate;
    }

    public Optional<InstantFilter> optionalImportedDate() {
        return Optional.ofNullable(importedDate);
    }

    public InstantFilter importedDate() {
        if (importedDate == null) {
            setImportedDate(new InstantFilter());
        }
        return importedDate;
    }

    public void setImportedDate(InstantFilter importedDate) {
        this.importedDate = importedDate;
    }

    public StringFilter getNom() {
        return nom;
    }

    public Optional<StringFilter> optionalNom() {
        return Optional.ofNullable(nom);
    }

    public StringFilter nom() {
        if (nom == null) {
            setNom(new StringFilter());
        }
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getDisponible() {
        return disponible;
    }

    public Optional<StringFilter> optionalDisponible() {
        return Optional.ofNullable(disponible);
    }

    public StringFilter disponible() {
        if (disponible == null) {
            setDisponible(new StringFilter());
        }
        return disponible;
    }

    public void setDisponible(StringFilter disponible) {
        this.disponible = disponible;
    }

    public FloatFilter getPrixFournisseur() {
        return prixFournisseur;
    }

    public Optional<FloatFilter> optionalPrixFournisseur() {
        return Optional.ofNullable(prixFournisseur);
    }

    public FloatFilter prixFournisseur() {
        if (prixFournisseur == null) {
            setPrixFournisseur(new FloatFilter());
        }
        return prixFournisseur;
    }

    public void setPrixFournisseur(FloatFilter prixFournisseur) {
        this.prixFournisseur = prixFournisseur;
    }

    public StringFilter getHtTtc() {
        return htTtc;
    }

    public Optional<StringFilter> optionalHtTtc() {
        return Optional.ofNullable(htTtc);
    }

    public StringFilter htTtc() {
        if (htTtc == null) {
            setHtTtc(new StringFilter());
        }
        return htTtc;
    }

    public void setHtTtc(StringFilter htTtc) {
        this.htTtc = htTtc;
    }

    public FloatFilter getTauxTva() {
        return tauxTva;
    }

    public Optional<FloatFilter> optionalTauxTva() {
        return Optional.ofNullable(tauxTva);
    }

    public FloatFilter tauxTva() {
        if (tauxTva == null) {
            setTauxTva(new FloatFilter());
        }
        return tauxTva;
    }

    public void setTauxTva(FloatFilter tauxTva) {
        this.tauxTva = tauxTva;
    }

    public FloatFilter getMargeProfit() {
        return margeProfit;
    }

    public Optional<FloatFilter> optionalMargeProfit() {
        return Optional.ofNullable(margeProfit);
    }

    public FloatFilter margeProfit() {
        if (margeProfit == null) {
            setMargeProfit(new FloatFilter());
        }
        return margeProfit;
    }

    public void setMargeProfit(FloatFilter margeProfit) {
        this.margeProfit = margeProfit;
    }

    public FloatFilter getPrixVente() {
        return prixVente;
    }

    public Optional<FloatFilter> optionalPrixVente() {
        return Optional.ofNullable(prixVente);
    }

    public FloatFilter prixVente() {
        if (prixVente == null) {
            setPrixVente(new FloatFilter());
        }
        return prixVente;
    }

    public void setPrixVente(FloatFilter prixVente) {
        this.prixVente = prixVente;
    }

    public StringFilter getVendu() {
        return vendu;
    }

    public Optional<StringFilter> optionalVendu() {
        return Optional.ofNullable(vendu);
    }

    public StringFilter vendu() {
        if (vendu == null) {
            setVendu(new StringFilter());
        }
        return vendu;
    }

    public void setVendu(StringFilter vendu) {
        this.vendu = vendu;
    }

    public FloatFilter getQuantiteParPiece() {
        return quantiteParPiece;
    }

    public Optional<FloatFilter> optionalQuantiteParPiece() {
        return Optional.ofNullable(quantiteParPiece);
    }

    public FloatFilter quantiteParPiece() {
        if (quantiteParPiece == null) {
            setQuantiteParPiece(new FloatFilter());
        }
        return quantiteParPiece;
    }

    public void setQuantiteParPiece(FloatFilter quantiteParPiece) {
        this.quantiteParPiece = quantiteParPiece;
    }

    public StringFilter getUnite() {
        return unite;
    }

    public Optional<StringFilter> optionalUnite() {
        return Optional.ofNullable(unite);
    }

    public StringFilter unite() {
        if (unite == null) {
            setUnite(new StringFilter());
        }
        return unite;
    }

    public void setUnite(StringFilter unite) {
        this.unite = unite;
    }

    public StringFilter getPrixParUnite() {
        return prixParUnite;
    }

    public Optional<StringFilter> optionalPrixParUnite() {
        return Optional.ofNullable(prixParUnite);
    }

    public StringFilter prixParUnite() {
        if (prixParUnite == null) {
            setPrixParUnite(new StringFilter());
        }
        return prixParUnite;
    }

    public void setPrixParUnite(StringFilter prixParUnite) {
        this.prixParUnite = prixParUnite;
    }

    public StringFilter getFournisseur() {
        return fournisseur;
    }

    public Optional<StringFilter> optionalFournisseur() {
        return Optional.ofNullable(fournisseur);
    }

    public StringFilter fournisseur() {
        if (fournisseur == null) {
            setFournisseur(new StringFilter());
        }
        return fournisseur;
    }

    public void setFournisseur(StringFilter fournisseur) {
        this.fournisseur = fournisseur;
    }

    public StringFilter getRefFournisseur() {
        return refFournisseur;
    }

    public Optional<StringFilter> optionalRefFournisseur() {
        return Optional.ofNullable(refFournisseur);
    }

    public StringFilter refFournisseur() {
        if (refFournisseur == null) {
            setRefFournisseur(new StringFilter());
        }
        return refFournisseur;
    }

    public void setRefFournisseur(StringFilter refFournisseur) {
        this.refFournisseur = refFournisseur;
    }

    public FloatFilter getStock() {
        return stock;
    }

    public Optional<FloatFilter> optionalStock() {
        return Optional.ofNullable(stock);
    }

    public FloatFilter stock() {
        if (stock == null) {
            setStock(new FloatFilter());
        }
        return stock;
    }

    public void setStock(FloatFilter stock) {
        this.stock = stock;
    }

    public FloatFilter getCommandesClients() {
        return commandesClients;
    }

    public Optional<FloatFilter> optionalCommandesClients() {
        return Optional.ofNullable(commandesClients);
    }

    public FloatFilter commandesClients() {
        if (commandesClients == null) {
            setCommandesClients(new FloatFilter());
        }
        return commandesClients;
    }

    public void setCommandesClients(FloatFilter commandesClients) {
        this.commandesClients = commandesClients;
    }

    public LocalDateFilter getDerniereVerificationDate() {
        return derniereVerificationDate;
    }

    public Optional<LocalDateFilter> optionalDerniereVerificationDate() {
        return Optional.ofNullable(derniereVerificationDate);
    }

    public LocalDateFilter derniereVerificationDate() {
        if (derniereVerificationDate == null) {
            setDerniereVerificationDate(new LocalDateFilter());
        }
        return derniereVerificationDate;
    }

    public void setDerniereVerificationDate(LocalDateFilter derniereVerificationDate) {
        this.derniereVerificationDate = derniereVerificationDate;
    }

    public LocalDateFilter getDerniereLivraisonDate() {
        return derniereLivraisonDate;
    }

    public Optional<LocalDateFilter> optionalDerniereLivraisonDate() {
        return Optional.ofNullable(derniereLivraisonDate);
    }

    public LocalDateFilter derniereLivraisonDate() {
        if (derniereLivraisonDate == null) {
            setDerniereLivraisonDate(new LocalDateFilter());
        }
        return derniereLivraisonDate;
    }

    public void setDerniereLivraisonDate(LocalDateFilter derniereLivraisonDate) {
        this.derniereLivraisonDate = derniereLivraisonDate;
    }

    public StringFilter getAchatFournisseur() {
        return achatFournisseur;
    }

    public Optional<StringFilter> optionalAchatFournisseur() {
        return Optional.ofNullable(achatFournisseur);
    }

    public StringFilter achatFournisseur() {
        if (achatFournisseur == null) {
            setAchatFournisseur(new StringFilter());
        }
        return achatFournisseur;
    }

    public void setAchatFournisseur(StringFilter achatFournisseur) {
        this.achatFournisseur = achatFournisseur;
    }

    public LocalDateFilter getDernierAchatDate() {
        return dernierAchatDate;
    }

    public Optional<LocalDateFilter> optionalDernierAchatDate() {
        return Optional.ofNullable(dernierAchatDate);
    }

    public LocalDateFilter dernierAchatDate() {
        if (dernierAchatDate == null) {
            setDernierAchatDate(new LocalDateFilter());
        }
        return dernierAchatDate;
    }

    public void setDernierAchatDate(LocalDateFilter dernierAchatDate) {
        this.dernierAchatDate = dernierAchatDate;
    }

    public FloatFilter getDernierAchatQuantite() {
        return dernierAchatQuantite;
    }

    public Optional<FloatFilter> optionalDernierAchatQuantite() {
        return Optional.ofNullable(dernierAchatQuantite);
    }

    public FloatFilter dernierAchatQuantite() {
        if (dernierAchatQuantite == null) {
            setDernierAchatQuantite(new FloatFilter());
        }
        return dernierAchatQuantite;
    }

    public void setDernierAchatQuantite(FloatFilter dernierAchatQuantite) {
        this.dernierAchatQuantite = dernierAchatQuantite;
    }

    public FloatFilter getStatsLivraison() {
        return statsLivraison;
    }

    public Optional<FloatFilter> optionalStatsLivraison() {
        return Optional.ofNullable(statsLivraison);
    }

    public FloatFilter statsLivraison() {
        if (statsLivraison == null) {
            setStatsLivraison(new FloatFilter());
        }
        return statsLivraison;
    }

    public void setStatsLivraison(FloatFilter statsLivraison) {
        this.statsLivraison = statsLivraison;
    }

    public FloatFilter getStatsPerte() {
        return statsPerte;
    }

    public Optional<FloatFilter> optionalStatsPerte() {
        return Optional.ofNullable(statsPerte);
    }

    public FloatFilter statsPerte() {
        if (statsPerte == null) {
            setStatsPerte(new FloatFilter());
        }
        return statsPerte;
    }

    public void setStatsPerte(FloatFilter statsPerte) {
        this.statsPerte = statsPerte;
    }

    public FloatFilter getStatsVente() {
        return statsVente;
    }

    public Optional<FloatFilter> optionalStatsVente() {
        return Optional.ofNullable(statsVente);
    }

    public FloatFilter statsVente() {
        if (statsVente == null) {
            setStatsVente(new FloatFilter());
        }
        return statsVente;
    }

    public void setStatsVente(FloatFilter statsVente) {
        this.statsVente = statsVente;
    }

    public FloatFilter getStatsVenteSpeciale() {
        return statsVenteSpeciale;
    }

    public Optional<FloatFilter> optionalStatsVenteSpeciale() {
        return Optional.ofNullable(statsVenteSpeciale);
    }

    public FloatFilter statsVenteSpeciale() {
        if (statsVenteSpeciale == null) {
            setStatsVenteSpeciale(new FloatFilter());
        }
        return statsVenteSpeciale;
    }

    public void setStatsVenteSpeciale(FloatFilter statsVenteSpeciale) {
        this.statsVenteSpeciale = statsVenteSpeciale;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitCriteria that = (ProduitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(epicerioId, that.epicerioId) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(importedDate, that.importedDate) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(code, that.code) &&
            Objects.equals(disponible, that.disponible) &&
            Objects.equals(prixFournisseur, that.prixFournisseur) &&
            Objects.equals(htTtc, that.htTtc) &&
            Objects.equals(tauxTva, that.tauxTva) &&
            Objects.equals(margeProfit, that.margeProfit) &&
            Objects.equals(prixVente, that.prixVente) &&
            Objects.equals(vendu, that.vendu) &&
            Objects.equals(quantiteParPiece, that.quantiteParPiece) &&
            Objects.equals(unite, that.unite) &&
            Objects.equals(prixParUnite, that.prixParUnite) &&
            Objects.equals(fournisseur, that.fournisseur) &&
            Objects.equals(refFournisseur, that.refFournisseur) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(commandesClients, that.commandesClients) &&
            Objects.equals(derniereVerificationDate, that.derniereVerificationDate) &&
            Objects.equals(derniereLivraisonDate, that.derniereLivraisonDate) &&
            Objects.equals(achatFournisseur, that.achatFournisseur) &&
            Objects.equals(dernierAchatDate, that.dernierAchatDate) &&
            Objects.equals(dernierAchatQuantite, that.dernierAchatQuantite) &&
            Objects.equals(statsLivraison, that.statsLivraison) &&
            Objects.equals(statsPerte, that.statsPerte) &&
            Objects.equals(statsVente, that.statsVente) &&
            Objects.equals(statsVenteSpeciale, that.statsVenteSpeciale) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            epicerioId,
            createdDate,
            lastUpdatedDate,
            importedDate,
            nom,
            code,
            disponible,
            prixFournisseur,
            htTtc,
            tauxTva,
            margeProfit,
            prixVente,
            vendu,
            quantiteParPiece,
            unite,
            prixParUnite,
            fournisseur,
            refFournisseur,
            stock,
            commandesClients,
            derniereVerificationDate,
            derniereLivraisonDate,
            achatFournisseur,
            dernierAchatDate,
            dernierAchatQuantite,
            statsLivraison,
            statsPerte,
            statsVente,
            statsVenteSpeciale,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEpicerioId().map(f -> "epicerioId=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastUpdatedDate().map(f -> "lastUpdatedDate=" + f + ", ").orElse("") +
            optionalImportedDate().map(f -> "importedDate=" + f + ", ").orElse("") +
            optionalNom().map(f -> "nom=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalDisponible().map(f -> "disponible=" + f + ", ").orElse("") +
            optionalPrixFournisseur().map(f -> "prixFournisseur=" + f + ", ").orElse("") +
            optionalHtTtc().map(f -> "htTtc=" + f + ", ").orElse("") +
            optionalTauxTva().map(f -> "tauxTva=" + f + ", ").orElse("") +
            optionalMargeProfit().map(f -> "margeProfit=" + f + ", ").orElse("") +
            optionalPrixVente().map(f -> "prixVente=" + f + ", ").orElse("") +
            optionalVendu().map(f -> "vendu=" + f + ", ").orElse("") +
            optionalQuantiteParPiece().map(f -> "quantiteParPiece=" + f + ", ").orElse("") +
            optionalUnite().map(f -> "unite=" + f + ", ").orElse("") +
            optionalPrixParUnite().map(f -> "prixParUnite=" + f + ", ").orElse("") +
            optionalFournisseur().map(f -> "fournisseur=" + f + ", ").orElse("") +
            optionalRefFournisseur().map(f -> "refFournisseur=" + f + ", ").orElse("") +
            optionalStock().map(f -> "stock=" + f + ", ").orElse("") +
            optionalCommandesClients().map(f -> "commandesClients=" + f + ", ").orElse("") +
            optionalDerniereVerificationDate().map(f -> "derniereVerificationDate=" + f + ", ").orElse("") +
            optionalDerniereLivraisonDate().map(f -> "derniereLivraisonDate=" + f + ", ").orElse("") +
            optionalAchatFournisseur().map(f -> "achatFournisseur=" + f + ", ").orElse("") +
            optionalDernierAchatDate().map(f -> "dernierAchatDate=" + f + ", ").orElse("") +
            optionalDernierAchatQuantite().map(f -> "dernierAchatQuantite=" + f + ", ").orElse("") +
            optionalStatsLivraison().map(f -> "statsLivraison=" + f + ", ").orElse("") +
            optionalStatsPerte().map(f -> "statsPerte=" + f + ", ").orElse("") +
            optionalStatsVente().map(f -> "statsVente=" + f + ", ").orElse("") +
            optionalStatsVenteSpeciale().map(f -> "statsVenteSpeciale=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
