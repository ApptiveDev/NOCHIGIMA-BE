package apptive.nochigima.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Embedded
    private Discount discount;

    public Product(String name, Long price, String imageUrl, Brand brand, Discount discount) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.discount = discount;
    }

    public void update(String name, Long price, String imageUrl, Brand brand, Discount discount) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.discount = discount;
    }
}
