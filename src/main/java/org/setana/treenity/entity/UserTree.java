package org.setana.treenity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class UserTree extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_tree_id")
    private Long id;

    private Boolean bookmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id")
    private Tree tree;

    public UserTree(User user, Tree tree) {
        this.user = user;
        this.tree = tree;
        this.bookmark = true;
    }
}
