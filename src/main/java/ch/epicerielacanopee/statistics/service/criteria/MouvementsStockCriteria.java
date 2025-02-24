package ch.epicerielacanopee.statistics.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ch.epicerielacanopee.statistics.domain.MouvementsStock} entity. This class is used
 * in {@link ch.epicerielacanopee.statistics.web.rest.MouvementsStockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mouvements-stocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MouvementsStockCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private IntegerFilter epicerioId;

    private InstantFilter createdDate;

    private InstantFilter lastUpdatedDate;

    private InstantFilter importedDate;

    private InstantFilter date;

    private StringFilter utilisateur;

    private StringFilter type;

    private FloatFilter epicerioMouvement;

    private FloatFilter mouvement;

    private FloatFilter solde;

    private StringFilter vente;

    private StringFilter codeProduit;

    private StringFilter produit;

    private StringFilter responsableProduit;

    private StringFilter fournisseurProduit;

    private StringFilter codeFournisseur;

    private FloatFilter reduction;

    private FloatFilter ponderation;

    private FloatFilter venteChf;

    private FloatFilter valeurChf;

    private BooleanFilter active;

    private Boolean distinct;

    public MouvementsStockCriteria() {}

    public MouvementsStockCriteria(MouvementsStockCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.epicerioId = other.optionalEpicerioId().map(IntegerFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastUpdatedDate = other.optionalLastUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.importedDate = other.optionalImportedDate().map(InstantFilter::copy).orElse(null);
        this.date = other.optionalDate().map(InstantFilter::copy).orElse(null);
        this.utilisateur = other.optionalUtilisateur().map(StringFilter::copy).orElse(null);
        this.type = other.optionalType().map(StringFilter::copy).orElse(null);
        this.epicerioMouvement = other.optionalEpicerioMouvement().map(FloatFilter::copy).orElse(null);
        this.mouvement = other.optionalMouvement().map(FloatFilter::copy).orElse(null);
        this.solde = other.optionalSolde().map(FloatFilter::copy).orElse(null);
        this.vente = other.optionalVente().map(StringFilter::copy).orElse(null);
        this.codeProduit = other.optionalCodeProduit().map(StringFilter::copy).orElse(null);
        this.produit = other.optionalProduit().map(StringFilter::copy).orElse(null);
        this.responsableProduit = other.optionalResponsableProduit().map(StringFilter::copy).orElse(null);
        this.fournisseurProduit = other.optionalFournisseurProduit().map(StringFilter::copy).orElse(null);
        this.codeFournisseur = other.optionalCodeFournisseur().map(StringFilter::copy).orElse(null);
        this.reduction = other.optionalReduction().map(FloatFilter::copy).orElse(null);
        this.ponderation = other.optionalPonderation().map(FloatFilter::copy).orElse(null);
        this.venteChf = other.optionalVenteChf().map(FloatFilter::copy).orElse(null);
        this.valeurChf = other.optionalValeurChf().map(FloatFilter::copy).orElse(null);
        this.active = other.optionalActive().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MouvementsStockCriteria copy() {
        return new MouvementsStockCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public Optional<InstantFilter> optionalDate() {
        return Optional.ofNullable(date);
    }

    public InstantFilter date() {
        if (date == null) {
            setDate(new InstantFilter());
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getUtilisateur() {
        return utilisateur;
    }

    public Optional<StringFilter> optionalUtilisateur() {
        return Optional.ofNullable(utilisateur);
    }

    public StringFilter utilisateur() {
        if (utilisateur == null) {
            setUtilisateur(new StringFilter());
        }
        return utilisateur;
    }

    public void setUtilisateur(StringFilter utilisateur) {
        this.utilisateur = utilisateur;
    }

    public StringFilter getType() {
        return type;
    }

    public Optional<StringFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public StringFilter type() {
        if (type == null) {
            setType(new StringFilter());
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public FloatFilter getEpicerioMouvement() {
        return epicerioMouvement;
    }

    public Optional<FloatFilter> optionalEpicerioMouvement() {
        return Optional.ofNullable(epicerioMouvement);
    }

    public FloatFilter epicerioMouvement() {
        if (epicerioMouvement == null) {
            setEpicerioMouvement(new FloatFilter());
        }
        return epicerioMouvement;
    }

    public void setEpicerioMouvement(FloatFilter epicerioMouvement) {
        this.epicerioMouvement = epicerioMouvement;
    }

    public FloatFilter getMouvement() {
        return mouvement;
    }

    public Optional<FloatFilter> optionalMouvement() {
        return Optional.ofNullable(mouvement);
    }

    public FloatFilter mouvement() {
        if (mouvement == null) {
            setMouvement(new FloatFilter());
        }
        return mouvement;
    }

    public void setMouvement(FloatFilter mouvement) {
        this.mouvement = mouvement;
    }

    public FloatFilter getSolde() {
        return solde;
    }

    public Optional<FloatFilter> optionalSolde() {
        return Optional.ofNullable(solde);
    }

    public FloatFilter solde() {
        if (solde == null) {
            setSolde(new FloatFilter());
        }
        return solde;
    }

    public void setSolde(FloatFilter solde) {
        this.solde = solde;
    }

    public StringFilter getVente() {
        return vente;
    }

    public Optional<StringFilter> optionalVente() {
        return Optional.ofNullable(vente);
    }

    public StringFilter vente() {
        if (vente == null) {
            setVente(new StringFilter());
        }
        return vente;
    }

    public void setVente(StringFilter vente) {
        this.vente = vente;
    }

    public StringFilter getCodeProduit() {
        return codeProduit;
    }

    public Optional<StringFilter> optionalCodeProduit() {
        return Optional.ofNullable(codeProduit);
    }

    public StringFilter codeProduit() {
        if (codeProduit == null) {
            setCodeProduit(new StringFilter());
        }
        return codeProduit;
    }

    public void setCodeProduit(StringFilter codeProduit) {
        this.codeProduit = codeProduit;
    }

    public StringFilter getProduit() {
        return produit;
    }

    public Optional<StringFilter> optionalProduit() {
        return Optional.ofNullable(produit);
    }

    public StringFilter produit() {
        if (produit == null) {
            setProduit(new StringFilter());
        }
        return produit;
    }

    public void setProduit(StringFilter produit) {
        this.produit = produit;
    }

    public StringFilter getResponsableProduit() {
        return responsableProduit;
    }

    public Optional<StringFilter> optionalResponsableProduit() {
        return Optional.ofNullable(responsableProduit);
    }

    public StringFilter responsableProduit() {
        if (responsableProduit == null) {
            setResponsableProduit(new StringFilter());
        }
        return responsableProduit;
    }

    public void setResponsableProduit(StringFilter responsableProduit) {
        this.responsableProduit = responsableProduit;
    }

    public StringFilter getFournisseurProduit() {
        return fournisseurProduit;
    }

    public Optional<StringFilter> optionalFournisseurProduit() {
        return Optional.ofNullable(fournisseurProduit);
    }

    public StringFilter fournisseurProduit() {
        if (fournisseurProduit == null) {
            setFournisseurProduit(new StringFilter());
        }
        return fournisseurProduit;
    }

    public void setFournisseurProduit(StringFilter fournisseurProduit) {
        this.fournisseurProduit = fournisseurProduit;
    }

    public StringFilter getCodeFournisseur() {
        return codeFournisseur;
    }

    public Optional<StringFilter> optionalCodeFournisseur() {
        return Optional.ofNullable(codeFournisseur);
    }

    public StringFilter codeFournisseur() {
        if (codeFournisseur == null) {
            setCodeFournisseur(new StringFilter());
        }
        return codeFournisseur;
    }

    public void setCodeFournisseur(StringFilter codeFournisseur) {
        this.codeFournisseur = codeFournisseur;
    }

    public FloatFilter getReduction() {
        return reduction;
    }

    public Optional<FloatFilter> optionalReduction() {
        return Optional.ofNullable(reduction);
    }

    public FloatFilter reduction() {
        if (reduction == null) {
            setReduction(new FloatFilter());
        }
        return reduction;
    }

    public void setReduction(FloatFilter reduction) {
        this.reduction = reduction;
    }

    public FloatFilter getPonderation() {
        return ponderation;
    }

    public Optional<FloatFilter> optionalPonderation() {
        return Optional.ofNullable(ponderation);
    }

    public FloatFilter ponderation() {
        if (ponderation == null) {
            setPonderation(new FloatFilter());
        }
        return ponderation;
    }

    public void setPonderation(FloatFilter ponderation) {
        this.ponderation = ponderation;
    }

    public FloatFilter getVenteChf() {
        return venteChf;
    }

    public Optional<FloatFilter> optionalVenteChf() {
        return Optional.ofNullable(venteChf);
    }

    public FloatFilter venteChf() {
        if (venteChf == null) {
            setVenteChf(new FloatFilter());
        }
        return venteChf;
    }

    public void setVenteChf(FloatFilter venteChf) {
        this.venteChf = venteChf;
    }

    public FloatFilter getValeurChf() {
        return valeurChf;
    }

    public Optional<FloatFilter> optionalValeurChf() {
        return Optional.ofNullable(valeurChf);
    }

    public FloatFilter valeurChf() {
        if (valeurChf == null) {
            setValeurChf(new FloatFilter());
        }
        return valeurChf;
    }

    public void setValeurChf(FloatFilter valeurChf) {
        this.valeurChf = valeurChf;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public Optional<BooleanFilter> optionalActive() {
        return Optional.ofNullable(active);
    }

    public BooleanFilter active() {
        if (active == null) {
            setActive(new BooleanFilter());
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
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
        final MouvementsStockCriteria that = (MouvementsStockCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(epicerioId, that.epicerioId) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
            Objects.equals(importedDate, that.importedDate) &&
            Objects.equals(date, that.date) &&
            Objects.equals(utilisateur, that.utilisateur) &&
            Objects.equals(type, that.type) &&
            Objects.equals(epicerioMouvement, that.epicerioMouvement) &&
            Objects.equals(mouvement, that.mouvement) &&
            Objects.equals(solde, that.solde) &&
            Objects.equals(vente, that.vente) &&
            Objects.equals(codeProduit, that.codeProduit) &&
            Objects.equals(produit, that.produit) &&
            Objects.equals(responsableProduit, that.responsableProduit) &&
            Objects.equals(fournisseurProduit, that.fournisseurProduit) &&
            Objects.equals(codeFournisseur, that.codeFournisseur) &&
            Objects.equals(reduction, that.reduction) &&
            Objects.equals(ponderation, that.ponderation) &&
            Objects.equals(venteChf, that.venteChf) &&
            Objects.equals(valeurChf, that.valeurChf) &&
            Objects.equals(active, that.active) &&
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
            date,
            utilisateur,
            type,
            epicerioMouvement,
            mouvement,
            solde,
            vente,
            codeProduit,
            produit,
            responsableProduit,
            fournisseurProduit,
            codeFournisseur,
            reduction,
            ponderation,
            venteChf,
            valeurChf,
            active,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementsStockCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEpicerioId().map(f -> "epicerioId=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastUpdatedDate().map(f -> "lastUpdatedDate=" + f + ", ").orElse("") +
            optionalImportedDate().map(f -> "importedDate=" + f + ", ").orElse("") +
            optionalDate().map(f -> "date=" + f + ", ").orElse("") +
            optionalUtilisateur().map(f -> "utilisateur=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalEpicerioMouvement().map(f -> "epicerioMouvement=" + f + ", ").orElse("") +
            optionalMouvement().map(f -> "mouvement=" + f + ", ").orElse("") +
            optionalSolde().map(f -> "solde=" + f + ", ").orElse("") +
            optionalVente().map(f -> "vente=" + f + ", ").orElse("") +
            optionalCodeProduit().map(f -> "codeProduit=" + f + ", ").orElse("") +
            optionalProduit().map(f -> "produit=" + f + ", ").orElse("") +
            optionalResponsableProduit().map(f -> "responsableProduit=" + f + ", ").orElse("") +
            optionalFournisseurProduit().map(f -> "fournisseurProduit=" + f + ", ").orElse("") +
            optionalCodeFournisseur().map(f -> "codeFournisseur=" + f + ", ").orElse("") +
            optionalReduction().map(f -> "reduction=" + f + ", ").orElse("") +
            optionalPonderation().map(f -> "ponderation=" + f + ", ").orElse("") +
            optionalVenteChf().map(f -> "venteChf=" + f + ", ").orElse("") +
            optionalValeurChf().map(f -> "valeurChf=" + f + ", ").orElse("") +
            optionalActive().map(f -> "active=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
