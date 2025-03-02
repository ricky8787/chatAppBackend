package com.example.chatapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Message {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "room_id")
	    private Room room;

	    @ManyToOne
	    @JoinColumn(name = "sender_id")
	    private User sender;

	    @Column(columnDefinition = "TEXT")
	    private String content;

	    @Enumerated(EnumType.STRING)
	    private MessageType type;

	    @Column(name = "timestamp", updatable = false)
	    private LocalDateTime timestamp = LocalDateTime.now();

	    public enum MessageType {
	        CHAT, JOIN, LEAVE
	    }
}