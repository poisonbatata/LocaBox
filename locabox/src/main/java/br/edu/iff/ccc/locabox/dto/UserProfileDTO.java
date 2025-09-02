package br.edu.iff.ccc.locabox.dto;

public class UserProfileDTO {
    private Long id;
    private String name;       // vem de UserSystem.nome
    private String email;
    private String status;
    private String role;

    // Campos de apresentação (opcionais — não existem na entidade)
    private String avatarUrl;      // fallback no template se null
    private Integer joinedYear;    // placeholder até ter data real
    private Double avgRating;      // placeholder até ligar reviews
    private Integer totalReviews;  // placeholder até ligar reviews
    private String phone;          // opcional
    private String address;        // opcional
    private String description;    // opcional

    public UserProfileDTO() {}

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public Integer getJoinedYear() { return joinedYear; }
    public void setJoinedYear(Integer joinedYear) { this.joinedYear = joinedYear; }

    public Double getAvgRating() { return avgRating; }
    public void setAvgRating(Double avgRating) { this.avgRating = avgRating; }

    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
