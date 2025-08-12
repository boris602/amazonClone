
package org.amazonclone.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long prodId;

    @ManyToOne
    @JoinColumn(name = "sold_by_id", nullable = false)
    private User seller;

    @Column(nullable = false)
    private int amount;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "name", nullable = false)
    private String prodName;

    @Column(name = "price", nullable = false)
    private double prodPrice;

    @Column(name = "link", nullable = false)
    private String prodPictureLink;
}


