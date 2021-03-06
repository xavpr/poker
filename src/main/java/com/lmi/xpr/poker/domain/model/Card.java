package com.lmi.xpr.poker.domain.model;

import com.lmi.xpr.poker.data.entity.Faces;
import com.lmi.xpr.poker.data.entity.Suits;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
    private Long idCard;
    private Suits suit;
    private Faces face;
    private boolean alreadyDealt;
    private Long deckId;
    private Integer position;

    public Card(Suits suit, Faces face, Integer position) {
        this.suit = suit;
        this.face = face;
        this.position = position;
    }
}
