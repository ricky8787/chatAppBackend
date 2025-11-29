package com.example.chatapp.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.chatapp.entity.User;
import com.example.chatapp.service.JwtService;
import com.example.chatapp.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
/**
 * JWT 認證過濾器 (Authentication Filter)
 * 這是一個在 Spring Security 過濾器鏈中運行的組件。
 * 它繼承 OncePerRequestFilter，確保每個請求只會執行一次此過濾器。
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    

    @Override
    /**
     * 過濾器的核心邏輯。
     * @param request  傳入的 HTTP 請求
     * @param response 傳出的 HTTP 回應
     * @param filterChain 過濾器鏈，用於將請求傳遞給下一個過濾器
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 從請求的 Header 中取得 "Authorization" 的值
        String authHeader = request.getHeader("Authorization");

        // 2. 檢查 Header 是否存在，以及是否以 "Bearer " 開頭
        //    如果不存在或格式不對，表示這個請求可能是不需要認證的公開 API (如登入、註冊)
        //    或者是一個無效的請求。無論如何，我們直接交給下一個過濾器處理。
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 取得 JWT 字串
        //    "Bearer " 字串長度為 7，所以我們從第 7 個字元開始擷取。
        String token = authHeader.substring(7);
        // 4. 從 JWT 中解析出使用者名稱 (username)
        String username = jwtService.extractUsername(token);

        // 5. 檢查使用者名稱是否存在，並且當前的 Security Context 中還沒有 Authentication 物件
        //    (SecurityContextHolder.getContext().getAuthentication() == null)
        //    這個檢查是為了避免在同一個請求中重複進行認證。
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 6. 如果通過檢查，就從資料庫中讀取使用者完整資訊
            User user = userService.findByUsername(username);
            
            // 7. 建立一個 UsernamePasswordAuthenticationToken 物件。
            //    這個物件代表一個已經被認證的使用者。
            //    - principal: 通常是使用者物件本身 (user)
            //    - credentials: 密碼，因為已經認證過了，所以設為 null
            //    - authorities: 使用者的權限列表，這裡暫時給一個空的 ArrayList
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            // 8. 將這個認證物件設定到 Spring Security 的安全上下文 (SecurityContext) 中
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 9. 不論認證成功與否，都將請求繼續往下一個過濾器傳遞
        filterChain.doFilter(request, response);
    }
}