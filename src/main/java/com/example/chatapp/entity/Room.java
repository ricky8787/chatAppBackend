package com.example.chatapp.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "rooms")
@NoArgsConstructor
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 或你目前 DB 的策略
	@Schema(description = "群組 ID", example = "101")
	private Long id;

	@Schema(description = "群組名稱", example = "前端工程師聊天室")
	private String roomName;

	@Schema(description = "群組頭像 URL", example = "https://example.com/images/group-avatar.png")
	private String avatarUrl;

	@Schema(description = "是否公開群組", example = "false")
	private Boolean isPublic;

	@Schema(description = "建立時間（UTC）", example = "2025-11-22T13:00:00Z")
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
	// 避免 toString/序列化時一直循環
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<RoomMember> members = new ArrayList<>();

	@PrePersist
	protected void onCreate() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
	}

}
