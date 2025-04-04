package t2308e.assignment.entity;

import t2308e.assignment.enums.Category;
import t2308e.assignment.enums.Status;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class NFT {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Timestamp createdAt;
    private String creator;
    private Category category;
    private String ownerWalletAddress;
    private Status status;

    public NFT(int id, String name, String description, String imageUrl, BigDecimal price, Timestamp createdAt, String creator, Category category, String ownerWalletAddress, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.createdAt = createdAt;
        this.creator = creator;
        this.category = category;
        this.ownerWalletAddress = ownerWalletAddress;
        this.status = status;
    }

    public NFT() {

    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getOwnerWalletAddress() { return ownerWalletAddress; }
    public void setOwnerWalletAddress(String ownerWalletAddress) { this.ownerWalletAddress = ownerWalletAddress; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    // Các phương thức hỗ trợ
    public String getCategoryDescription() {
        return category.getDescription();
    }

    public String getStatusDescription() {
        return status.getDescription();
    }
}

