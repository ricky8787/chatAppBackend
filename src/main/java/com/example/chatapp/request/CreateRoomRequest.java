package com.example.chatapp.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CreateGroupRequest", description = "建立聊天群組的請求物件")
public class CreateRoomRequest {


	@Schema(description = "群組名稱，若未填可由後端給預設名稱", example = "前端工程師聊天室")
	private String roomName;

	@Schema(description = "初始成員 ID 列表（可以不包含建立者，後端可自動補上）", example = "[\"U002\", \"U003\", \"U004\"]")
	private List<Long> memberIds;

	@Schema(description = "群組頭像圖片 URL", example = "https://example.com/images/group-avatar.png")
	private String avatarUrl;

	@Schema(description = "是否為公開群組（true 表示任何人可搜尋加入，false 為私密）", example = "false")
	private Boolean isPublic;
}