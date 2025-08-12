package org.amazonclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="history")
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "prod_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User buyer;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime date;
}