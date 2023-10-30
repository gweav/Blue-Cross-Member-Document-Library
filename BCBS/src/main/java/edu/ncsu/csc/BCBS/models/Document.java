package edu.ncsu.csc.BCBS.models;

import java.time.LocalDate;

/**
 * A class representing a policy document
 *
 * @author Gabe Weaver, Godsend Cheung
 */
public class Document {

    /**
     * The document's ID
     */
    private String id;

    /**
     * The document's name
     */
    private String name;

    /**
     * The policy ID that the document belongs to.
     */
    private String policyId;

    /**
     * The id of the user this document is related to (Subscriber/Dependent).
     */
    private String userId;

    /**
     * The date this document was created
     */
    private LocalDate issueDate;

    /**
     * The type of this document (EOB, Bill, Coverage, etc.)
     */
    private DocType type;

    /**
     * Empty constructor for Jackson mapping
     */
    public Document() {
    }

    /**
     * Constructor for the document object
     *
     * @param id the document's ID
     */
    public Document(String id, String name, String policyId, String userId, LocalDate issueDate, DocType type) {
        setId(id);
        setName(name);
        setPolicyId(policyId);
        setUserId(userId);
        setIssueDate(issueDate);
        setType(type);
    }

    /**
     * Returns the document's ID
     *
     * @return the document's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the document's ID
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the document's name
     *
     * @return the document's name
     */
    public String getName() { return this.name; }

    /**
     * Sets the document's name
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the policy id.
     * 
     * @return the policy id of this document.
     */
    public String getPolicyId() {
        return this.policyId;
    }

    /**
     * Sets the policy id.
     * 
     * @param policyId the id to set.
     */
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    /**
     * Retrieves the User ID this document belongs to.
     * 
     * @return the user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the User ID this document belongs to.
     * 
     * @param userId String representation of user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the date this document was issued/created
     *
     * @return the date this document was issued/created
     */
    public LocalDate getIssueDate() {
        return this.issueDate;
    }

    /**
     * Sets the date this document was issued/created
     *
     * @param issueDate the date to set
     */
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Returns the document's type
     *
     * @return the document's type
     */
    public DocType getType() {
        return this.type;
    }

    /**
     * Sets the document's type
     *
     * @param type the type to set
     */
    public void setType(DocType type) {
        this.type = type;
    }
}
