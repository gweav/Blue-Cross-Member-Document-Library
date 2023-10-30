package edu.ncsu.csc.BCBS.models;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Blue Cross Blue Shield policy that a subscriber owns and zero or more dependents are linked
 * to. Policies span 12 months and are typically issued on a calendar year-end basis. Policies are associated with users
 * for up to three years.
 *
 * @author Gabe Weaver, Godsend Cheung
 */
public class Policy {

    /**
     * The policy's ID
     */
    private String id;

    /**
     * The year that this policy was activated
     */
    private Year year;

    /**
     * Indicates whether this policy is active. If this plan is not within the current year, this should be false
     */
    private boolean isActive;

    /**
     * The ID of the user who owns this policy
     */
    private String subscriberId;

    /**
     * Dependents associated with this policy (IDs)
     */
    private List<String> dependents;

    /**
     * Documents associated with this policy within its 12-months jurisdiction
     */
    private List<Document> documents;

    /**
     * Empty constructor for Jackson mapping
     */
    public Policy() {
    }

    /**
     * Constructor for the policy object
     *
     * @param id           the policy's ID
     * @param year         the year that the policy was activated
     * @param isActive     the status of the policy
     * @param subscriberId the ID of the user who owns the policy
     * @param dependents   users that are dependents of the policy's owner
     * @param documents    documents associated with the policy
     */
    public Policy(String id, Year year, boolean isActive, String subscriberId, List<String> dependents, List<Document> documents) {
        setId(id);
        setYear(year);
        setActive(isActive);
        setSubscriberId(subscriberId);
        setDependents(dependents);
        setDocuments(documents);
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public Year getYear() {
        return year;
    }

    private void setYear(Year year) {
        this.year = year;
    }

    public boolean isActive() {
        return isActive;
    }

    private void setActive(boolean active) {
        isActive = active;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    private void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public List<String> getDependents() {
        return dependents;
    }

    private void setDependents(List<String> dependents) {
        if (dependents == null) {
            this.dependents = new ArrayList<>();
        } else {
            this.dependents = dependents;
        }
    }

    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the policies documents. If the argument is null, create a new empty set of documents.
     * @param documents list of documents associated with the policy.
     */
    private void setDocuments(List<Document> documents) {
        this.documents = (documents == null) ? new ArrayList<>() : documents;
    }

    /**
     * Adds the specified user to the policy's list of dependents. The user to be added cannot be the subscriber.
     *
     * @param dependent the dependent to add
     * @return true if added successfully or false if the user is the subscriber or is already in the policy's list of dependents
     */
    public boolean addDependent(User dependent) {
        if (dependent != null && !dependents.contains(dependent.getId()) && !dependent.getId().equals(subscriberId)) {
            return dependents.add(dependent.getId());
        }
        return false;
    }

    /**
     * Removes the specified user from the policy's list of dependents
     *
     * @param dependent the dependent to remove
     * @return true if this list policy's dependent list contained the specified user
     */
    public boolean removeDependent(User dependent) {
        return dependents.remove(dependent.getId());
    }

    /**
     * Adds a document under this policy if it is within its calendar year.
     * 
     * @param doc document to be added.
     * @return true if the document is added.
     */
    public boolean addDocument(Document doc) {
        // Is the document valid for the policy?
        if (doc == null || doc.getIssueDate().getYear() != year.getValue() || !id.equals(doc.getPolicyId())) return false;

        // Does the document already exist?
        for (Document d : documents) {
            if (d.getId().equals(doc.getId())) return false;
        }

        // Is the document the owner's?
        if (doc.getUserId().equals(subscriberId)) return documents.add(doc);

        // Is the document associated with any dependent?
        for (String dependent : dependents) {
            if (dependent.equals(doc.getUserId())) {
                return documents.add(doc); // Match found.
            }
        }

        // All of the above was false -> unrelated document.
        return false;
    }
}
