package com.eduardopontes.romaneioapp.model.order;

import com.eduardopontes.romaneioapp.model.product.Product;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -4677476748478514011L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence")
    @SequenceGenerator(name = "order_item_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @NotNull
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(precision = 10, scale = 3)
    @Min(0)
    private double amount;

    @OneToOne
    @NotNull
    @JoinColumn(name = "primitive_type_id", nullable = false)
    private ProductPrimitiveType productPrimitiveType;
}
