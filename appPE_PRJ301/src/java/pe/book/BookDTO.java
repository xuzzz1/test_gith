/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.book;


/**
 *
 * @author DUNGHUYNH
 */
public class BookDTO {
    private int id;
    private String title;
    private String authorName;  // Lưu tên tác giả thay vì ID
    private String categoryName; // Lưu tên thể loại thay vì ID
    private int quantity;
    private String status;
    private String imageUrl; // Lưu đường dẫn ảnh
    private String description; // Thêm thuộc tính mô tả sách

    // Constructor không tham số
    public BookDTO() {
    }

    // Constructor có tham số đầy đủ
    public BookDTO(int id, String title, String authorName, String categoryName, int quantity, String status, String imageUrl, String description) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.status = status;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    
}
